package com.example.projectrestaurant.daos;

import com.example.projectrestaurant.dtos.Item;
import com.example.projectrestaurant.utils.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
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

    public List<Item> getAllItemsList() throws SQLException {
        List<Item> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, name, price, categoryId, quantity " +
                        "From Item ";
                preparedStatement = connection.prepareStatement(sql);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    String itemName = resultSet.getString("name");
                    float price = resultSet.getFloat("price");
                    int categoryId = resultSet.getInt("categoryId");
                    int quantity = resultSet.getInt("quantity");
                    result.add(new Item(uid, itemName, price, categoryId, quantity));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public List<Item> getAllItemsList(int catId) throws SQLException {
        List<Item> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, name, price, categoryId, quantity " +
                        "From Item " +
                        "Where categoryId = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, catId);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    String itemName = resultSet.getString("name");
                    float price = resultSet.getFloat("price");
                    int categoryId = resultSet.getInt("categoryId");
                    int quantity = resultSet.getInt("quantity");
                    result.add(new Item(uid, itemName, price, categoryId, quantity));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public Item getItemById(int itemId) throws SQLException {
        Item result = null;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, name, price, categoryId, quantity " +
                        "From Item " +
                        "Where id = ? ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, itemId);

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int uid = resultSet.getInt("id");
                    String itemName = resultSet.getString("name");
                    float price = resultSet.getFloat("price");
                    int categoryId = resultSet.getInt("categoryId");
                    int quantity = resultSet.getInt("quantity");
                    result = new Item(uid, itemName, price, categoryId, quantity);
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean insertItem(Item item) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Insert into Item (name, price, categoryId, quantity) " +
                        "Values (?, ?, ?, ?)" ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, item.getName());
                preparedStatement.setFloat(2, item.getPrice());
                preparedStatement.setInt(3, item.getCategoryId());
                preparedStatement.setInt(4, item.getQuantity());

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

    public boolean updateItem(Item item) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Update Item " +
                        "Set name = ?, price = ?, categoryId = ?, quantity = ? " +
                        "Where id = ? " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, item.getName());
                preparedStatement.setFloat(2, item.getPrice());
                preparedStatement.setInt(3, item.getCategoryId());
                preparedStatement.setInt(4, item.getQuantity());
                preparedStatement.setInt(5, item.getId());

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

    public boolean deleteItem(Item item) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Delete from Item " +
                        "Where id = ? " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, item.getId());

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
