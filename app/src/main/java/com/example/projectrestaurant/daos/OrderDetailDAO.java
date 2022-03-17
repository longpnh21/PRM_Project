package com.example.projectrestaurant.daos;

import com.example.projectrestaurant.dtos.Category;
import com.example.projectrestaurant.dtos.OrderDetail;
import com.example.projectrestaurant.utils.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
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

    public List<OrderDetail> getAllOrderDetail(int orderId) throws SQLException {
        List<OrderDetail> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select orderId, itemId, price, quantity " +
                        "From OrderDetail " +
                        "Where orderId = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, orderId);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int itemId = resultSet.getInt("itemId");
                    float price = resultSet.getFloat("price");
                    int quantity = resultSet.getInt("quantity");
                    result.add(new OrderDetail(orderId, itemId, price, quantity));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean insertOrderDetail(List<OrderDetail> orderDetails) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Insert into OrderDetail (orderId, itemId, price, quantity) " +
                        "Values (?, ?, ?, ?) ";
                for(int i = 1; i < orderDetails.size(); i++) {
                    sql += ", (?, ?, ?, ?) ";
                }
                preparedStatement = connection.prepareStatement(sql);
                for(int i = 0; i < orderDetails.size(); i++) {
                    preparedStatement.setInt(i * 4 + 1, orderDetails.get(i).getOrderId());
                    preparedStatement.setInt(i * 4 + 2, orderDetails.get(i).getItemId());
                    preparedStatement.setFloat(i * 4 + 3, orderDetails.get(i).getPrice());
                    preparedStatement.setInt(i * 4 + 4, orderDetails.get(i).getQuantity());
                }

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
