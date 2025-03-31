package myproject.shop.repository;

import lombok.RequiredArgsConstructor;
import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.OrderItem;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderItemRepository {

    private final DataSource dataSource;

    public void createOrderItem(OrderItem orderItem) throws SQLException {
        String sql = "insert into order_item(order_id, item_id, order_price, quantity) values(?, ?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, orderItem.getOrderId());
        preparedStatement.setInt(2, orderItem.getItemId());
        preparedStatement.setInt(3, orderItem.getOrderPrice());
        preparedStatement.setInt(4, orderItem.getQuantity());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public List<OrderItem> findOrderItem(int orderId) throws SQLException {
        String sql = "select * from order_item where order_id = ?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<OrderItem> orderItemList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setItemId(resultSet.getInt(2));
                orderItem.setOrderPrice(resultSet.getInt(3));
                orderItem.setQuantity(resultSet.getInt(4));
                orderItemList.add(orderItem);
            }
            return orderItemList;
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
