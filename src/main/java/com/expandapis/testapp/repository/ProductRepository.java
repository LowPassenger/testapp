package com.expandapis.testapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ProductRepository {
    @Autowired
    private EntityManager entityManager;

    public void createTable(String tableQuery) {
        try {
            entityManager.createNativeQuery(tableQuery).executeUpdate();
        } catch (DataAccessException exception) {
            log.error("Something wrong while table creation process via native query: {}",
                    tableQuery);
        }
    }

    public void saveProduct(String productQuery) {
        try {
            entityManager.createNativeQuery(productQuery).executeUpdate();
        } catch (DataAccessException exception) {
            log.error("Something wrong while save product(s) process via native query: {}",
                    productQuery);
        }
    }

    public List getAllProducts(String stringQuery) {
        Query query = entityManager.createNativeQuery(stringQuery);
        return query.getResultList();
    }
}
