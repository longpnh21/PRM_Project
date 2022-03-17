package com.example.projectrestaurant.daos;

import com.example.projectrestaurant.dtos.Item;
import com.example.projectrestaurant.dtos.Order;
import com.example.projectrestaurant.utils.DBHelper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
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

    public List<Order> getAllOrderList() throws SQLException {
        List<Order> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, username, orderDate " +
                        "From [dbo].[Order] ";
                preparedStatement = connection.prepareStatement(sql);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    Date orderDate = resultSet.getDate("orderDate");
                    result.add(new Order(uid, username, orderDate));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public List<Order> getOrderListByUsername(String userName) throws SQLException {
        List<Order> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, username, orderDate " +
                        "From [dbo].[Order] " +
                        "Where username = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, userName);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    Date orderDate = resultSet.getDate("orderDate");
                    result.add(new Order(uid, username, orderDate));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public Order getOrderById(int orderId) throws SQLException {
        Order result = null;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, username, orderDate " +
                        "From [dbo].[Order] " +
                        "Where id = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, orderId);

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    Date orderDate = resultSet.getDate("orderDate");
                    result = new Order(uid, username, orderDate);
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public Order getOrderByUsernameAndDate(String username, java.util.Date orderDate) throws SQLException {
        Order result = null;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id " +
                        "From [dbo].[Order] " +
                        "Where username = ? AND orderDate = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setTime(2, new Time(orderDate.getTime()));

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    result = new Order(uid, username, orderDate);
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean insertOrder(Order order) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Insert into [dbo].[Order] (username, orderDate) " +
                        "Values (?, ?) " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, order.getUsername());
                preparedStatement.setTime(2, new java.sql.Time(order.getOrderDate().getTime()));

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
