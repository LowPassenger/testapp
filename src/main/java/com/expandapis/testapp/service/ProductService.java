package com.expandapis.testapp.service;

import com.expandapis.testapp.exception.ResourceNotFoundException;
import com.expandapis.testapp.repository.ProductRepository;
import com.expandapis.testapp.util.AppConstants;
import com.expandapis.testapp.util.TableNameHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final TableNameHolder tableNameHolder;

    public ProductService(ProductRepository productRepository, TableNameHolder tableNameHolder) {
        this.productRepository = productRepository;
        this.tableNameHolder = tableNameHolder;
    }

    public boolean productHandler(Map<String, Object> payload) {
        productRepository.createTable(makeTableQuery(payload));
        log.info("Table {} was successfully created", payload.get(AppConstants.JSON_TABLE_KEY));

        List<String> recordsList = makeRecordsQuery(payload);
        if (!recordsList.isEmpty()) {
            recordsList.forEach(productRepository::saveProduct);
            log.info("{} of {}(s) was added to database", recordsList.size(),
                    AppConstants.JSON_RECORD_KEY);
            return true;
        }
        return false;
    }

    public List getAllProducts() {
        String query = "SELECT * FROM " + tableNameHolder.getLastTableName();
        return productRepository.getAllProducts(query);
    }

    private String makeTableQuery(Map<String, Object> payload) {
        String tableName = payload.get(AppConstants.JSON_TABLE_KEY).toString();
        tableNameHolder.setTableName(tableName);
        StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableName).append(" ( id INT PRIMARY KEY AUTO_INCREMENT, ");

        List<Map<String, Object>> records = (List<Map<String, Object>>)payload
                .get(AppConstants.JSON_RECORD_KEY);

        if (records.isEmpty()) {
            throw new ResourceNotFoundException(tableName, AppConstants.JSON_RECORD_KEY,
                    " field has 0 items!");
        }

        Map<String, Object> exampleRecord = records.get(0);
        for (String key : exampleRecord.keySet()) {
            queryBuilder.append(key).append(" VARCHAR(255), ");
        }

        queryBuilder.deleteCharAt(queryBuilder.length() - 2);
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private List<String> makeRecordsQuery(Map<String, Object> payload) {
        String tableName = payload.get("table").toString();
        List<Map<String, Object>> records = (List<Map<String, Object>>)payload.get("records");
        List<String> queries = new ArrayList<>();

        for (Map<String, Object> record : records) {
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ")
                    .append(tableName)
                    .append(" ( ");

            for (String key : record.keySet()) {
                queryBuilder.append(key).append(", ");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 2);
            queryBuilder.append(") VALUES ( ");

            for (Object value : record.values()) {
                queryBuilder.append("'").append(value.toString()).append("', ");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 2);
            queryBuilder.append(")");
            queries.add(queryBuilder.toString());
        }
        return queries;
    }
}
