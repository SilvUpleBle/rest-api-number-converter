package com.service.algorithm.contoller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.service.algorithm.exceptions.InvalidValueException;
import com.service.algorithm.exceptions.NumberOutOfRangeException;
import com.service.algorithm.resources.NumberConverter;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
// @RequestMapping(value = "request") // http://localhost:8080/
public class RequestController {

    @RequestMapping("/")
    public ModelAndView getDefault() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("convert");
        modelAndView.addObject("numberToStringChecked", true);
        modelAndView.addObject("input", "");
        modelAndView.addObject("output", "");
        return modelAndView;
    }
    @GetMapping("/convert")
    public ModelAndView getRequest(String type, String value) {
        if(value == null) {
            return getDefault();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("convert");
        modelAndView.setStatus(HttpStatusCode.valueOf(200));
        try {
            modelAndView.addObject("input", value);
            if (type.equals("NumberToString")) {
                modelAndView.addObject("numberToStringChecked", true);
                Long num;
                if (value.length() > 12) {
                    throw new InvalidValueException("Сonverter works with numbers up to and including 12 digits.");
                }
                try {
                    num = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new InvalidValueException("Don`t type letters, only numbers.");
                }

                modelAndView.addObject("output", NumberConverter.numberToString(num));
            } else {
                modelAndView.addObject("stringToNumberChecked", true);
                modelAndView.addObject("output", NumberConverter.stringToNumber(value));
            }
        } catch (Exception e) {
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
            modelAndView.addObject("output", "[Error] " + e.getMessage());
        }
        return modelAndView;
    }
}