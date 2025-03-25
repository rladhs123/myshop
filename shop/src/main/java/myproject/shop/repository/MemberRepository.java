package myproject.shop.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.shop.connection.DBConnectionUtil;
import myproject.shop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, name, address) values(?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, member.getMemberId());
        preparedStatement.setString(2, member.getName());
        preparedStatement.setInt(3, member.getAddress());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
        return member;
    }

    public Member findById(int memberId) throws SQLException {
        String sql = "select * from member where member_Id = ?";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, memberId);
        ResultSet resultSet = preparedStatement.executeQuery();

        try {
            if (resultSet.next()) {
                Member member = new Member();
                member.setMemberId(resultSet.getInt("member_id"));
                member.setName(resultSet.getString("name"));
                member.setAddress(resultSet.getInt("address"));

                return member;
            } else {
                throw new NoSuchElementException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }

    }

    public List<Member> findAll() throws SQLException {
        String sql = "select * from member";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Member> memberList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Member member = new Member();
                member.setMemberId(resultSet.getInt(1));
                member.setName(resultSet.getString(2));
                member.setAddress(resultSet.getInt(3));

                memberList.add(member);
            }
            return memberList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
    }

    public void deleteById(int memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, memberId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public void deleteAll() throws SQLException {
        String sql = "delete from member";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
