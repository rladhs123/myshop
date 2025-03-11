package myproject.shop.repository;

import lombok.RequiredArgsConstructor;
import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.Order;
import myproject.shop.domain.OrderItem;
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
public class OrderRepository {

    private final DataSource dataSource;

    public Order create(Order order) throws SQLException {
        String sql = "insert into orders(member_Id) values (?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, order.getMemberId());
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            order.setOrderId(generatedKeys.getInt(1));
        } else {
            throw new NoSuchElementException();
        }

        preparedStatement.close();
        connection.close();

        return order;
    }

    public Order findOrder(int memberId) throws SQLException {
        String sql = "select * from orders where member_Id = ?";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, memberId);
        ResultSet resultSet = preparedStatement.executeQuery();

        try {
            if (resultSet.next()) {
                Order order = new Order(memberId);
                order.setOrderId(resultSet.getInt(1));
                return order;
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

    public List<Order> findAll() throws SQLException {
        String sql = "select * from orders";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Order> orderList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt(1));
                order.setMemberId(resultSet.getInt(2));

                orderList.add(order);
            }
            return orderList;
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
