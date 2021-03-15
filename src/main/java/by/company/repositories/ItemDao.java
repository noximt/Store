package by.company.repositories;

import by.company.domains.Category;
import by.company.domains.Item;
import by.company.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao {
    @Autowired
    private DataSource dataSource;

    public void save(Item item, long userId) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into items values ( default, ?, ?, ?, ?)");
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getDescription());
            preparedStatement.setString(4, String.valueOf(item.getCategory()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item getById(long getById) {
        Item itemById = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from items where id= ?");
            preparedStatement1.setLong(1, getById);
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            long id = resultSet.getLong(1);
            long userId = resultSet.getLong(2);
            String name = resultSet.getString(3);
            String description = resultSet.getString(4);
            Category category = Category.valueOf(resultSet.getString(5));
            itemById = new Item(id, new User(userId), name, description, category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemById;
    }

    public List<Item> getByUserId(long userId) {
        List<Item> items = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items where user_id = ?");
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long itemId = resultSet.getLong(1);
                String name = resultSet.getString(3);
                String description = resultSet.getString(4);
                Category category = Category.valueOf(resultSet.getString(5));
                items.add(new Item(itemId, new User(userId), name, description, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from items");
            while (resultSet.next()) {
                long itemId = resultSet.getLong(1);
                long userId = resultSet.getLong(2);
                String name = resultSet.getString(3);
                String description = resultSet.getString(4);
                Category category = Category.valueOf(resultSet.getString(5));
                items.add(new Item(itemId, new User(userId), name, description, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean contains(long id) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from items");
            while(resultSet.next()){
                if (resultSet.getLong(1) == id){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean contains(String userId) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select user_id from items");
            while(resultSet.next()){
                if (resultSet.getString(1).equals(userId)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void remove(long id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from items where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
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

    public void removeByUserId(long userId) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from items where user_id = ?");
            preparedStatement.setLong(1, userId);
            preparedStatement.execute();
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

    public String updateName(String newName, long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update items set name = ? where id = ?");
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newName;
    }

    public String updateDescription(String newDescription, long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update items set description = ? where id = ?");
            preparedStatement.setString(1, newDescription);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newDescription;
    }

    public Category updateCategory(Category category, long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update items set category = ? where id = ?");
            preparedStatement.setString(1, String.valueOf(category));
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

}
