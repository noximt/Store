package by.company.repositories;

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

    public void save(Item item, long id){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into items values ( default, ?, ?, ?)");
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getDescription());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Item getById(long getById){
        Item itemById = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from items where id= ?");
            preparedStatement1.setLong(1, getById);
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            long id = resultSet.getLong(1);
            String name = resultSet.getString(3);
            String description = resultSet.getString(4);
            long user_id = resultSet.getLong(2);
            itemById = new Item(id, name, description);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return itemById;
    }

    public List<Item> getByUser(long id){
        List<Item> items = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from items where user_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long item_id = resultSet.getLong(1);
                String name = resultSet.getString(3);
                String description = resultSet.getString(4);
                items.add(new Item(item_id, name, description));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
                long item_id = resultSet.getLong(1);
                long user_id = resultSet.getLong(2);
                String name = resultSet.getString(3);
                String description = resultSet.getString(4);
                items.add(new Item(item_id, name, description));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }
}
