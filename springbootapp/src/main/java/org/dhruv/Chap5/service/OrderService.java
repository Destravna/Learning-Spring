package org.dhruv.Chap5.service;


import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void placeOrder(String product, int quantity) {
        sleep(150);
        System.out.println("Order placed: " + product + " x" + quantity);
    }

    public double calculateTotal(double price, int quantity) {
        sleep(50);
        return price * quantity;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}