package com.example.c1024l1pm.service;

import com.example.c1024l1pm.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jdk.dynalink.StandardOperation.CALL;

public class ProductDAO implements GenneralDAO<Product> {

    private static final String SELECT_ALL_PRODUCTS = "select * from product";
    private static final String INSERT_PRODUCT_SQL = "insert into product(name, price) values(?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "select * from product where id = ?";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?, price = ? where id = ?";
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<Product>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> findAllWithStoreProdure() {
        List<Product> products = new ArrayList<>();
        String query = "{CALL sp_get_products()}";

        try {
            Connection connection = DBConnection.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public void save(Product entity) throws SQLException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void saveWithStoreProdure(Product entity) throws SQLException {
        String query = "{CALL sp_insert_product(?,?)}";
        try (Connection connection = DBConnection.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);) {
            callableStatement.setString(1, entity.getName());
            callableStatement.setDouble(2, entity.getPrice());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                product = new Product(id, name, price);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    @Override
    public boolean update(Product entity) {
        boolean rowUpdated = false;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL);){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowUpdated;
    }

    @Override
    public Product findByIdWithStoreProdure(int id) {
        String query = "{CALL sp_find_product_by_id(?)}";
        Product product = null;
        try (Connection connection = DBConnection.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);) {
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                product = new Product(id, name, price);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean updateWithStoreProdure(Product entity) {
        boolean rowUpdated;
        String query = "{CALL sp_update_product(?,?,?)}";
        try (Connection connection = DBConnection.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);) {
            callableStatement.setInt(1, entity.getId());
            callableStatement.setString(2, entity.getName());
            callableStatement.setDouble(3, entity.getPrice());
            rowUpdated = callableStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
