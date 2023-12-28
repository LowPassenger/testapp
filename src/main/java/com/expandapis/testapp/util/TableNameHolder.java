package com.expandapis.testapp.util;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TableNameHolder {
    private final Deque<String> tableNames = new ConcurrentLinkedDeque<>();

    public void setTableName(String tableName) {
        tableNames.add(tableName);
    }

    public String getLastTableName() {
        return tableNames.peekLast();
    }
}
