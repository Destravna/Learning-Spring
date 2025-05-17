package org.dhruv.Chap5.Controller;

import java.util.List;

import org.dhruv.Chap5.annotations.TrackUsage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @TrackUsage
    @GetMapping
    public List<String> getAllProducts() {
        simulateWork(150);
        return List.of("Laptop", "Phone", "Tablet");
    }


    @TrackUsage
    @PostMapping
    public String addProduct(@RequestBody String productName) {
        simulateWork(100);
        return "Product added: " + productName;
    }


    @TrackUsage
    private void simulateWork(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}