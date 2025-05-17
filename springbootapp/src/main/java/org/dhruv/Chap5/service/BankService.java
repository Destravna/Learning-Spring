package org.dhruv.Chap5.service;


import org.dhruv.Chap5.annotations.RequireRole;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @RequireRole("ADMIN")
    public void closeAccount(String accountId) {
        System.out.println("Account " + accountId + " closed.");
    }

    @RequireRole("USER")
    public void viewBalance(String accountId) {
        System.out.println("Viewing balance for " + accountId);
    }
}
