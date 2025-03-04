package myproject.shop.repository;

import lombok.RequiredArgsConstructor;
import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.Order;
import myproject.shop.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Repository
public class OrderRepository {

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

    private Connection getConnection() throws SQLException {
        return DBConnectionUtil.getConnection();
    }
}
