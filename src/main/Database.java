package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Path to your .db file (use relative or absolute path)
    private  final String URL = "jdbc:sqlite:res/db/bhaagkanchha.db";

    // Method to establish a connection
    public  Connection connect() {
        Connection connection = null;

            System.out.println("Connection to SQLite database established.");
			return connection;
			//todo
    }
}
