package myproject.shop.repository;

import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.Item;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Repository
public class ItemRepository {

    public Item save(Item item) throws SQLException {
        String sql = "insert into item(stockquantity, price) values(?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, item.getStockQuantity());
        preparedStatement.setInt(2, item.getPrice());
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

        try {
            if (generatedKeys.next()) {
                item.setItemId(generatedKeys.getInt(1));
                return item;
            } else {
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            generatedKeys.close();
            preparedStatement.close();
            connection.close();
        }
    }

    public Item findById(int itemId) throws SQLException {
        String sql = "select * from item where item_id = ?";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, itemId);
        ResultSet resultSet = preparedStatement.executeQuery();

        try {
            if (resultSet.next()) {
                Item item = new Item();
                item.setItemId(itemId);
                item.setStockQuantity(resultSet.getInt(2));
                item.setPrice(resultSet.getInt(3));

                return item;
            } else {
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
    }

    private Connection getConnection() throws SQLException {
        return DBConnectionUtil.getConnection();
    }
}
