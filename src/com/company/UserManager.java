package com.company;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class UserManager {
    private static final String username = "root";
    private static final String password = "root";
    private static final String db = "test2";
    private static final String url = "jdbc:mysql://localhost:3306/test2";
    private static String name;
    private static String date;
    private static String newName;
    private static String newDate;

    static void start() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

        Statement stmt = connection.createStatement();
        System.out.println("Пользователи у которых день рождения сегодня:");
        ResultSet rs = stmt.executeQuery("SELECT name, b_day FROM `users` where DATE_FORMAT(b_day, '%m-%d') = DATE_FORMAT(NOW(), '%m-%d')");

        while (rs.next()) {
            System.out.print(rs.getString("name") + "\t \t");
            System.out.println(rs.getString("b_day"));
        }

        System.out.println("Пользователи у которых день рождения сегодне в ближайшее время:");
        rs = stmt.executeQuery("SELECT name, b_day FROM `users` where DATE_FORMAT(b_day, '%m-%d') >= DATE_FORMAT(NOW(), '%m-%d') and DATE_FORMAT(b_day, '%m-%d') <= DATE_FORMAT((NOW() + INTERVAL +7 DAY), '%m-%d')");

        while (rs.next()) {
            System.out.print(rs.getString("name") + "\t \t");
            System.out.println(rs.getString("b_day"));
        }
        stmt.close();
    } catch (SQLException e) {
        throw new IllegalStateException("Cannot connect the database!", e);
    }
    }

    private static void infoEnter(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя человека: ");
        name = scanner.nextLine();
        System.out.print("Введите дату рождения человека: ");
        date = scanner.nextLine();
    }
    public static void addUser(){
        infoEnter();
        try (Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "INSERT INTO users (name, b_day) VALUES (?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Человек добавлен\n");
        }catch(Exception ex){
            System.out.println("Connection failed... Error");
            System.out.println(ex);
        }
    }

    public static void deleteUser(){
        infoEnter();
        try (Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "DELETE FROM users WHERE name=? AND b_day = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Человек удален из базы данных\n");
        }catch(Exception ex){
            System.out.println("Connection failed... Error");
            System.out.println(ex);
        }
    }

    public static void showAllUsers(){
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement stmt = connection.createStatement();
            System.out.println("Все пользователи в базе данных:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.print(rs.getString("name") + "\t \t");
                System.out.println(rs.getString("b_day"));
            }
        }catch(Exception ex){
            System.out.println("Connection failed... Error");
            System.out.println(ex);
        }
    }

    public static void editUser() {
        infoEnter();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите обновленное имя человека: ");
        newName = scanner.nextLine();
        System.out.print("Введите обновленну дату рождения человека: ");
        newDate = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "UPDATE users SET name=?,b_day=? WHERE name=? AND b_day=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newDate);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, date);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Данные изменены\n");
        } catch (Exception ex) {
            System.out.println("Connection failed... Error");
            System.out.println(ex);
        }
    }

    public static void showMenu(){
        System.out.println("Меню: Введите 1 - Добавить запись в, БД 2 - удалить запись из БД, 3 - Редактировать запись, 4 - показать все записи в БД, 5 - повторно показать именнинников и людей с ближайшим ДР");
    }
}
