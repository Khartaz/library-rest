package com.library;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.DriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbConnectionTest {

    @Test
    public void testDbConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/library?serverTimezone=Europe/Warsaw&useSSL=False";
        String user = "library";
        String pass = "12345678";
        try {
            DriverManager.getConnection(jdbcUrl, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}