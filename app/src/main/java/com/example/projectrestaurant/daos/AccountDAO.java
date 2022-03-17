package com.example.projectrestaurant.daos;

import com.example.projectrestaurant.dtos.Account;
import com.example.projectrestaurant.dtos.Role;
import com.example.projectrestaurant.utils.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void Initialize() {
        connection = null;
        preparedStatement = null;
        resultSet = null;
    }

    private void Close() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public List<Account> getAllAccountsList() throws SQLException {
        List<Account> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select username, password, role " +
                        "From Account ";
                preparedStatement = connection.prepareStatement(sql);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    result.add(new Account(username, password, Role.valueOf(role)));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public Account login(String username, String password) throws SQLException {
        Account result = null;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select username, password, role " +
                        "From Account " +
                        "Where username = ? AND password = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    String role = resultSet.getString("role");
                    result = new Account(username, password, role);
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean insertAccount(Account account) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Insert into Account (username, password) " +
                        "Values (?, ?)" ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());

                int rowCount = preparedStatement.executeUpdate();
                if(rowCount > -1) {
                    result = true;
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean updateAccount(Account account) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Update Account " +
                        "Set password = ? " +
                        "Where username = ? " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getPassword());
                preparedStatement.setString(2, account.getUsername());

                int rowCount = preparedStatement.executeUpdate();
                if(rowCount > -1) {
                    result = true;
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean deleteAccount(Account account) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Delete from Account " +
                        "Where username = ? " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getUsername());

                int rowCount = preparedStatement.executeUpdate();
                if(rowCount > -1) {
                    result = true;
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }
}
