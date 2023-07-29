package com.service.algorithm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Service;

@Service
public class Serivce {
    private ArrayList<Request> list = new ArrayList<>();

    public List<Request> list() {
        return list;
    }
}