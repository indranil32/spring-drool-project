package com.springproject.droolEngineProject.service;

import java.util.List;
import java.util.ArrayList;
import com.springproject.droolEngineProject.model.Order;
import com.springproject.droolEngineProject.model.Rule;
import com.springproject.droolEngineProject.repo.DroolRulesRepo;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import com.springproject.droolEngineProject.config.DroolConfig;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import java.io.FileInputStream;
import org.kie.internal.utils.KieHelper;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.KieBase;
import java.io.FileNotFoundException;

@Service
public class OrderService {

    private final KieContainer kieContainer;
    private final DroolRulesRepo rulesRepo;

    public OrderService(KieContainer kieContainer, DroolRulesRepo rulesRepo){
        this.kieContainer = kieContainer;
        this.rulesRepo = rulesRepo;
    }

    public Order getDiscountForOrder(Order order) {
        KieSession session = kieContainer.newKieSession();
        session.insert(order);
        session.fireAllRules();
        session.dispose();
        return order;
    }

    public Order getDiscountForOrderV2(Order order) throws FileNotFoundException{
        List<Rule> ruleAttributes = new ArrayList<>();
        rulesRepo.findAll().forEach(ruleAttributes::add);

        ObjectDataCompiler compiler = new ObjectDataCompiler();
        String generatedDRL = compiler.compile(ruleAttributes, Thread.currentThread().getContextClassLoader().getResourceAsStream(DroolConfig.RULES_TEMPLATE_FILE));
        KieServices kieServices = KieServices.Factory.get();

        KieHelper kieHelper = new KieHelper();

        //multiple such resoures/rules can be added
        byte[] b1 = generatedDRL.getBytes();
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        int numberOfRulesFired = kieSession.fireAllRules();
        kieSession.dispose();

        return order;
    }
}
