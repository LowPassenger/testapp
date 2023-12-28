package com.expandapis.testapp.repository;

import com.expandapis.testapp.util.AppConstants;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable(String tableQuery) {
        try {
            jdbcTemplate.execute(tableQuery);
        } catch (DataAccessException exception) {
            log.error("Something wrong while table creation process via native query: {}",
                    tableQuery);
        }
    }

    public boolean saveProduct(String productQuery) {
        try {
            jdbcTemplate.execute(productQuery);
            return true;
        } catch (DataAccessException exception) {
            log.error("Something wrong while save product(s) process via native query: {}",
                    productQuery);
        }
        return false;
    }

    public List getAllProducts(String stringQuery) {
        try {
            return jdbcTemplate.query(stringQuery, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet resultSet, int rowNum)
                        throws SQLException {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    Map<String, Object> columns = new LinkedHashMap<>();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        columns.put(metaData.getColumnName(i), resultSet.getObject(i));
                    }
                    return columns;
                }
            });
        } catch (DataAccessException exception) {
            log.error("An error occurred while getting all {}(s) from database!",
                    AppConstants.JSON_RECORD_KEY);
            return Collections.emptyList();
        }
    }
}
