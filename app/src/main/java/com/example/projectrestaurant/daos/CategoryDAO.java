package com.example.projectrestaurant.daos;

import com.example.projectrestaurant.dtos.Category;
import com.example.projectrestaurant.utils.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
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

    public List<Category> getAllCategories() throws SQLException {
        List<Category> result = new ArrayList<>();
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Select id, name " +
                        "From Category ";
                preparedStatement = connection.prepareStatement(sql);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    result.add(new Category(id, name));
                }
            }
        }
        finally {
            Close();
        }
        return result;
    }

    public boolean insertCategory(Category category) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Insert into Category (name) " +
                        "Values (?) " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, category.getName());

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

    public boolean updateCategory(Category category) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Update Category " +
                        "Set Name = ? " +
                        "where Id = ? " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, category.getName());
                preparedStatement.setInt(2, category.getId());

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

    public boolean deleteCategory(Category category) throws SQLException {
        boolean result = false;
        Initialize();
        try {
            connection = DBHelper.getConnection();
            if(connection != null) {
                String sql = "Delete from Category " +
                        "Where Id = ? " ;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, category.getId());

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
