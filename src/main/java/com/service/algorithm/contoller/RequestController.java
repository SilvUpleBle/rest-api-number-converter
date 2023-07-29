package com.service.algorithm.contoller;

import java.io.BufferedReader;
import java.io.FileReader;

import com.service.algorithm.resources.NumberConverter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping(value = "request") // http://localhost:8080/
public class RequestController {

    @GetMapping(path = "")
    public String getDefault() {

        return getTextFromFile("src/main/resources/templates/main_page/index.html").formatted("", "");
    }

    @GetMapping(path = "/convert")
    public String getRequest(String language, String type, String value) {
        try {
            if (type.equals("NumberToString")) {
                return getTextFromFile("src/main/resources/templates/main_page/index.html").formatted("checked", "",
                        value,
                        NumberConverter.numberToString(Long.parseLong(value)));
            } else {
                return getTextFromFile("src/main/resources/templates/main_page/index.html").formatted("", "checked",
                        value,
                        NumberConverter.stringToNumber(value));
            }
        } catch (Exception e) {
            if (type.equals("NumberToString")) {
                return getTextFromFile("src/main/resources/templates/main_page/index.html").formatted("checked", "",
                        value, e.getMessage());
            } else {
                return getTextFromFile("src/main/resources/templates/main_page/index.html").formatted("", "checked",
                        value, e.getMessage());
            }

        }
    }

    // TODO перенести в другой класс и сделать статичным
    private String getTextFromFile(String source) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(source));

            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            br.close();
        } catch (Exception e) {
        } finally {

        }

        return sb.toString();
    }
}