package by.company.services;

import by.company.domains.Response;
import by.company.domains.User;
import by.company.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Response<User> save(User user) {
        if (userDao.contains(user.getUsername())) {
            return new Response<>(false, "User already exist", user);
        }
        if (user.getUsername() == null && user.getPassword() == null && user.getName() == null && user.getSurname() == null && user.getRole() == null) {
            return new Response<>(false, "This user has null element in his entity", user);
        }
        userDao.save(user);
        return new Response<>(true, "Operation successful", user);
    }

    public Response<User> getById(long userId) {
        if (userId == 0 || userId < 0) {
            return new Response<>(false, "User id is 0 or less than 0");
        }
        if (userDao.contains(userId)) {
            return new Response<>(true, "Operation successful", userDao.getById(userId));
        }
        return new Response<>(false, "No such User");
    }

    public Response<User> getByUsername(String username){
        if (username == null) {
            return new Response<>(false, "Username is null");
        }
        if (userDao.contains(username)) {
            return new Response<>(true, "Operation successful", userDao.getByUsername(username));
        }
        return new Response<>(false, "No such User");
    }

    public Response<User> getAll(){
        if (userDao.getAll().size() == 0){
            return new Response<>(false, " There is no users");
        }
        return new Response(true, "Operation successful", userDao.getAll());
    }

    public Response<User> remove(long id){
        if (id == 0 || id < 0){
            return new Response<>(false, "User id is 0 or less than 0");
        }
        if (userDao.contains(id)){
            userDao.remove(id);
            return new Response<>(true, "Operation successful");
        }
        return new Response<>(false, "No such user");
    }

    public Response<User> remove(String username){
        if (username == null){
            return new Response<>(false, "Username is null");
        }
        if (userDao.contains(username)){
            userDao.remove(username);
            return new Response<>(true, "Operation successful");
        }
        return new Response<>(false, "No such user");
    }

    public Response<User> updatePassword(long  id, String newPassword){
        if (newPassword == null){
            return  new Response<>(false, "Password is null");
        }
        if (id == 0 || id < 0){
            return new Response<>(false, "User id is 0 or less than 0");
        }
        userDao.updatePassword(newPassword, id);
        return new Response<>(true, "Password has been updated");
    }

    public Response<User> updateName(long  id, String newName){
        if (newName == null){
            return  new Response<>(false, "Name is null");
        }
        if (id == 0 || id < 0){
            return new Response<>(false, "User id is 0 or less than 0");
        }
        userDao.updateName(newName, id);
        return new Response<>(true, "Name has been updated");
    }

    public Response<User> updateSurname(long  id, String newSurname){
        if (newSurname == null){
            return  new Response<>(false, "Surname is null");
        }
        if (id == 0 || id < 0){
            return new Response<>(false, "User id is 0 or less than 0");
        }
        userDao.updateSurname(newSurname, id);
        return new Response<>(true, "Surname has been updated");
    }
}
