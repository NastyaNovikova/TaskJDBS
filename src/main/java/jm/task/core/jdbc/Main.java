package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;
//git push -u origin test
public class Main {
    public static void main(String[] args) {
        UserService table = new UserServiceImpl();
        table.createUsersTable();
        table.saveUser("Nastya", "Nov", (byte) 22);
        table.saveUser("Ivan", "Fedun", (byte) 43);
        table.saveUser("Katya", "Didok", (byte) 8);
        table.saveUser("Tom", "Utochka", (byte) 15);
        List<User> list = table.getAllUsers();
        for (User i: list){
            System.out.println(i.toString());
        }

        table.cleanUsersTable();
        table.dropUsersTable();
    }
}
