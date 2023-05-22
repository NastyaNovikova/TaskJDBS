package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//execute()- Этот метод можно использовать вместо executeQuery() и executeUpdate() методов.когда точно не знаешь, какой запрос приходится исполнять

//Statement: SQL выражение, которое не содержит параметров
//PreparedStatement : Подготовленное SQL выражение, содержащее входные параметры
//CallableStatement : SQL выражение с возможностью получить возвращаемое значение из хранимых процедур (SQL Stored Procedures).

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() { //Class DriverManager позволяет подключиться к базе данных по указанному URL
        try (Connection con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);//Interface Connection (устанавливаем подключение)- обеспечивает формирование запросов к источнику данных и управление транзакциями.
             Statement statement = con.createStatement()) {//Interface Statement-SQL выражение, которое не содержит параметров
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" + //Метод executeUpdate для выполнения операторов управления данными типа INSERT, UPDATE или DELETE
                    "(Id SERIAL PRIMARY KEY , name VARCHAR(255), lastname VARCHAR(255), age int)");//SERIAL-serial - это синтаксический сахар над целочисленным типом данных, для которого создаётся счётчик sequence и ставится приращение этого sequence как default значение этого поля. sequence нетранзакционен и создаёт гарантированно не повторяющиеся числа в пределах этого sequence.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");//DROP TABLE-удалить не просто данные в таблице, а и саму таблицу
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)")) { //Interface PreparedStatement-помогает от взлома базы данных, который называется SQL Injection.
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
             Statement statement = connection.createStatement()) { //Класс ResultSet- содержит все результаты запроса
            ResultSet resultSet = statement.executeQuery("SELECT name, lastname, age FROM users");//executeQuery-если хотим получить данные из таблицы(SELECT)
            while (resultSet.next()) { // последовательно переключает строки для их чтения с помощью метода next()
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age"));
                userList.add(user); //getString- получение данных VARCHAR.getByte- чисел- getInt()
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");//TRUNCATE TABLE-Очистка содержания таблицы
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
