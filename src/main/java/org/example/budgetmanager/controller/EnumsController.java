package org.example.budgetmanager.controller;

import org.example.budgetmanager.service.impl.EnumsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/enums")
public class EnumsController {
    private final EnumsServiceImpl enumsService;

    @Autowired
    public EnumsController(EnumsServiceImpl enumsService) {
        this.enumsService = enumsService;
    }

    @GetMapping("/allCategories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(enumsService.getAllCategories().stream()
                .map(Enum::name).toList());
    }

    @GetMapping("/allCurrencies")
    public ResponseEntity<List<String>> getAllCurrencies() {
        return ResponseEntity.ok(enumsService.getAllCurrencies().stream()
                .map(Enum::name).toList());
    }
}
