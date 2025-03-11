package myproject.shop.repository;

import lombok.RequiredArgsConstructor;
import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.Item;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final DataSource dataSource;

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

    public List<Item> findAll() throws SQLException {
        String sql = "select * from item";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Item> itemList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getInt(1));
                item.setStockQuantity(resultSet.getInt(2));
                item.setPrice(resultSet.getInt(3));

                itemList.add(item);
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
