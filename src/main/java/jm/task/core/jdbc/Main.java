package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;
//git push -u origin test
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Nastya", "Nov", (byte) 22);
        userService.saveUser("Ivan", "Fedun", (byte) 43);
        userService.saveUser("Katya", "Didok", (byte) 8);
        userService.saveUser("Tom", "Utochka", (byte) 15);
        List<User> list = userService.getAllUsers();
        for (User i: list){
            System.out.println(i.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
