package myproject.shop.repository;

import lombok.RequiredArgsConstructor;
import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.OrderItem;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
