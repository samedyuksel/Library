package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {

    @GetMapping(value = {"/index"})
    public String index() {
        return "index";
    }
}
