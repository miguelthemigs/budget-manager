package org.example.budgetmanager.controller;

import org.example.budgetmanager.service.EnumsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
//        List<String> categories = Arrays.stream(Category.values())
//                .map(Enum::name) // Convert enum to its string representation (name)
//                .collect(Collectors.toList());

        return ResponseEntity.ok(enumsService.getAllCategories().stream()
                .map(Enum::name)
                .collect(Collectors.toList()));
    }
}
