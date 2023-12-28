package com.expandapis.testapp.controller;

import com.expandapis.testapp.service.ProductService;
import com.expandapis.testapp.util.AppConstants;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("add")
    public ResponseEntity<String> addProducts(@RequestBody Map<String, Object> payload) {
        if (payload == null || payload.isEmpty()
                || !payload.containsKey(AppConstants.JSON_TABLE_KEY)
                || !(payload.get(AppConstants.JSON_TABLE_KEY) instanceof String)
                || !payload.containsKey(AppConstants.JSON_RECORD_KEY)
                || !(payload.get(AppConstants.JSON_RECORD_KEY) instanceof List)) {
            return new ResponseEntity<>("The JSON isn't valid!",
                    HttpStatus.BAD_REQUEST);
        }

        return productService.productHandler(payload) ? new ResponseEntity<>(payload
                .get(AppConstants.JSON_RECORD_KEY) + " was successfully added", HttpStatus.CREATED)
                : new ResponseEntity<>("Something wrong, " + AppConstants.JSON_RECORD_KEY
                + "were not added to the database!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("all")
    public List getAllProducts() {
        return productService.getAllProducts();
    }
}
