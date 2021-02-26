package by.company.repositories;

import by.company.domains.Role;
import by.company.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private DataSource dataSource;

    public void save(User user){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values ( default, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getById(long getById){
        User userById = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id= ?");
            preparedStatement.setLong(1, getById);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userById = prepareUser(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userById;
    }

    public User getByUsername(String getByUsername){
        User userByUsername = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?");
            preparedStatement.setString(1, getByUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userByUsername = prepareUser(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userByUsername;
    }

    private User prepareUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String username = resultSet.getString(2);
        String password = resultSet.getString(3);
        String name = resultSet.getString(4);
        String surname = resultSet.getString(5);
        Role role = Role.valueOf(resultSet.getString(6));
        return new User(id, username, password, name, surname, role);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                users.add(prepareUser(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public boolean contains(long id) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from users");
            while(resultSet.next()){
                if (resultSet.getLong(1) == id){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean contains(String username) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select username from users");
            while(resultSet.next()){
                if (resultSet.getString(1).equals(username)){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void remove(long id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from users where id = ?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("delete from items where user_id = ?");
            preparedStatement1.setLong(1, id);
            preparedStatement2.setLong(1, id);
            preparedStatement1.execute();
            preparedStatement2.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException trowable) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            trowable.printStackTrace();
        }
    }

    public void remove(String username) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from users where username = ?");
            preparedStatement1.setString(1, username);
            preparedStatement1.execute();
            PreparedStatement preparedStatement2 = connection.prepareStatement("select id from users where username = ?");
            preparedStatement2.setString(1, username);
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            long user_id = resultSet.getLong(1);
            PreparedStatement preparedStatement3 = connection.prepareStatement("delete from items where user_id = ?");
            preparedStatement3.setLong(1 ,user_id);
            preparedStatement3.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException trowable) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            trowable.printStackTrace();
        }
    }

    public String updatePassword(String newPass, long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update users set password = ? where id = ?");
            preparedStatement.setString(1, newPass);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newPass;
    }

    public String updateName(String newName, long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ? where id = ?");
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newName;
    }

    public String updateSurname(String newSurname, long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update users set surname = ? where id = ?");
            preparedStatement.setString(1, newSurname);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newSurname;
    }

}
