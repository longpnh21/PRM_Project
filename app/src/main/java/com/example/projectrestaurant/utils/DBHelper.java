package com.example.projectrestaurant.utils;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
    private static String IP = "192.168.1.95";
    private static String PORT = "1433";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";
    private static String DATABASE_NAME = "FoodRestaurant";

    public static Connection getConnection() {
        Connection connection = null;
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://"+IP+":"+PORT+";databasename="+DATABASE_NAME+";User="+USERNAME+";password="+PASSWORD+";";
            connection = DriverManager.getConnection(connectionUrl);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

}