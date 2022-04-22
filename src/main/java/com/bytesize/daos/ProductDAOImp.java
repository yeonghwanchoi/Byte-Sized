package com.bytesize.daos;

import com.bytesize.entities.Product;
import com.bytesize.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductDAOImp implements ProductDAO{


    @Override
    public Product createProduct(Product Product) {
        try(Connection connection = DatabaseConnection.createConnection()){
            String sql = "insert into products values(default, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Product.getTitle());
            ps.setString(2, Product.getDescription());
            ps.setFloat(3, Product.getPrice());
            ps.setInt(4, Product.getInventory());
            ps.setInt(5, Product.getSellerId());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            Product.setProductId(rs.getInt("productId"));
            return Product;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Product selectProductById(int id) {

        try(Connection connection = DatabaseConnection.createConnection()) {
            String sql = "select * from products where productId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Product product = new Product(
                    rs.getInt("productId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getFloat("price"),
                    rs.getInt("inventory"),
                    rs.getInt("sellerId")
            );

        return product;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> selectAllProducts() {
        try(Connection connection = DatabaseConnection.createConnection()){
            String sql = "select * from products";
            Statement s = connection.createStatement();
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            List<Product> products = new ArrayList<>();
            while(rs.next()){
                Product product = new Product(
                        rs.getInt("productId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getInt("inventory"),
                        rs.getInt("sellerId")
                );
                products.add(product);
            }
            return products;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateProductById(Product Product) {
        try(Connection connection = DatabaseConnection.createConnection()){
            String sql = "update products set title = ?, description = ?, price = ?, inventory = ?, sellerId = ? where productId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Product.getTitle());
            ps.setString(2, Product.getDescription());
            ps.setFloat(3, Product.getPrice());
            ps.setInt(4, Product.getInventory());
            ps.setInt(5, Product.getSellerId());
            ps.setInt(6, Product.getProductId());
            return ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int removeProductById(int id) {
        try(Connection connection = DatabaseConnection.createConnection()){
            String sql = "delete from products where productId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}

