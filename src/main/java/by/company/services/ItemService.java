package by.company.services;

import by.company.domains.Category;
import by.company.domains.Item;
import by.company.domains.Response;
import by.company.repositories.ItemDao;
import by.company.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService {
    @Autowired
    private ItemDao itemDao;
    private UserDao userDao;

    public Response<Item> save(Item item, long userId) {
        if (!userDao.contains(userId)) {
            return new Response<>(false, "No such user", item);
        }
        itemDao.save(item, userId);
        return new Response<>(true, "Operation successful", item);
    }

    public Response<Item> getById(long Id) {
        if (!itemDao.contains(Id)) {
            return new Response<>(false, "No such item");
        }
        return new Response<>(true, "Operation successful", itemDao.getById(Id));
    }

    public Response<List<Item>> getByUserId(long userId) {
        if (!userDao.contains(userId)) {
            return new Response<>(false, "No such user");
        }
        return new Response<>(true, "Operation successful", itemDao.getByUserId(userId));
    }

//    public Response<Item> getAll() {
//
//    }
//
//    public Response<Item> remove(long id) {
//
//    }
//
//    public Response<Item> removeByUserId(long userId) {
//
//    }
//
//    public Response<Item> updateName(String newName, long id) {
//
//    }
//
//    public Response<Item> updateDescription(String newDescription, long id) {
//
//    }
//
//    public Response<Item> updateCategory(Category category, long id) {
//
//    }
}
