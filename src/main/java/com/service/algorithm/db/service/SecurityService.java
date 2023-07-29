package com.service.algorithm.db.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}