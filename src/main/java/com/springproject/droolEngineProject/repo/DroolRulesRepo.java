package com.springproject.droolEngineProject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springproject.droolEngineProject.model.Rule;


public interface DroolRulesRepo extends JpaRepository<Rule, Integer> {
}
