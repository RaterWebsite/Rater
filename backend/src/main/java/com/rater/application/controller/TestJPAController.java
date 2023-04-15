package com.rater.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rater.application.model.TestJPAEntity;
import com.rater.application.repository.TestJPARepository;

@Controller
@RequestMapping("/api/jpa")
public class TestJPAController {
    
    @Autowired
    private TestJPARepository repository;

    @PostMapping("/addString")
    public @ResponseBody String addNewString (@RequestBody String string) {

        TestJPAEntity entity = new TestJPAEntity();
        entity.setName(string);
        repository.save(entity);
        return "saved: " + string;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<TestJPAEntity> getAll() {
        return repository.findAll();
    }
}
