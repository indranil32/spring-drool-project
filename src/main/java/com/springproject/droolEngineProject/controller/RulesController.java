package com.springproject.droolEngineProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springproject.droolEngineProject.model.Rule;
import com.springproject.droolEngineProject.repo.DroolRulesRepo;

@RestController
public class RulesController {

    @Autowired
    private DroolRulesRepo rulesRepo;

    @PostMapping("/rule")
    public void addRule (@RequestBody Rule rule) {
        rulesRepo.save(rule);
    }

    @GetMapping("/rules")
    public List<Rule> getRules () {
        List<Rule> rules = new ArrayList<Rule>();
        rulesRepo.findAll().forEach(rules::add);
        return rules;
    }
}
