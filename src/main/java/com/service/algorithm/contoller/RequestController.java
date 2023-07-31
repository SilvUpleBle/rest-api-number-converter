package com.service.algorithm.contoller;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import com.service.algorithm.database.model.Authority;
import com.service.algorithm.database.model.Log;
import com.service.algorithm.database.model.User;
import com.service.algorithm.database.repository.AuthorityRepo;
import com.service.algorithm.database.repository.LogRepo;
import com.service.algorithm.database.repository.UserRepo;
import com.service.algorithm.exceptions.InvalidValueException;
import com.service.algorithm.resources.NumberConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RequestController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthorityRepo authorityRepo;
    @Autowired
    LogRepo logRepo;

    @RequestMapping("/")
    public ModelAndView getDefault() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("convert");
        modelAndView.addObject("numberToStringChecked", true);
        modelAndView.addObject("input", "");
        modelAndView.addObject("output", "");
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistration(String username, String password, String confirmPassword) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        if (username == null && password == null && confirmPassword == null) {
            return modelAndView;
        }

        if (username.length() < 4 || username.length() > 30) {
            modelAndView.addObject("message", "Username must be more than 4 and less than 30 characters!");
            return modelAndView;
        }
        if (!password.equals(confirmPassword)) {
            modelAndView.addObject("username", username);
            modelAndView.addObject("message", "Passwords don`t match!");
            return modelAndView;
        }
        if (password.length() < 6 || password.length() > 30) {
            modelAndView.addObject("username", username);
            modelAndView.addObject("message", "Password must be more than 6 and less than 30 characters!");
            return modelAndView;
        }
        if (userRepo.findByUsername(username) != null) {
            modelAndView.addObject("message", "User with this username already exists!");
            return modelAndView;
        }

        User user = new User(username, password, true);
        userRepo.save(user);
        Authority authority = new Authority(username, Authority.Role.ROLE_USER);
        authorityRepo.save(authority);

        modelAndView.addObject("message", "User %s successfully saved!".formatted(username));
        return modelAndView;
    }

    @GetMapping("/convert")
    public ModelAndView getRequest(String type, String value, Authentication auth) {
        if(value == null) {
            return getDefault();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("convert");
        modelAndView.setStatus(HttpStatusCode.valueOf(200));
        Log log = new Log();
        log.setUsername(auth.getName());
        log.setConvertationType(type);
        log.setInner_value(value);
        log.setLogType(Log.LogType.INFO);
        try {
            modelAndView.addObject("input", value);
            if (type.equals("NumberToString")) {
                modelAndView.addObject("numberToStringChecked", true);
                Long num;
                if (value.length() > 12) {
                    if (value.length() > 13 && value.charAt(0) != '-') {
                        throw new InvalidValueException("Сonverter works with numbers up to and including 12 digits.");
                    }
                }
                try {
                    num = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new InvalidValueException("Don`t type letters, only numbers.");
                }
                modelAndView.addObject("output", NumberConverter.numberToString(num));
                log.setOuter_value(NumberConverter.numberToString(num));
            } else {
                modelAndView.addObject("stringToNumberChecked", true);
                modelAndView.addObject("output", NumberConverter.stringToNumber(value));
                log.setOuter_value(NumberConverter.stringToNumber(value).toString());
            }
        } catch (Exception e) {
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
            modelAndView.addObject("output", "[Error] " + e.getMessage());
            log.setLogType(Log.LogType.ERROR);
            log.setOuter_value(e.getMessage());
        }
        OffsetDateTime offsetDateTime = OffsetDateTime.now().atZoneSameInstant(ZoneId.of("Europe/Moscow")).toOffsetDateTime();
        log.setCreateTime(Timestamp.valueOf(offsetDateTime.toLocalDateTime()));
        logRepo.save(log);
        return modelAndView;
    }
}