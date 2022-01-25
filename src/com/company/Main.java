package com.company;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;


public class Main {

    private static final String username = "root";
    private static final String password = "root";
    private static final String db = "test2";
    private static final String url = "jdbc:mysql://localhost:3306/test2";

    public static void main(String[] args) {
        int statusAdd;

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            Statement stmt = connection.createStatement();
            System.out.println("Пользователи у которых день рождения сегодня:");
            ResultSet rs = stmt.executeQuery("SELECT name, b_day FROM `users` where DATE_FORMAT(b_day, '%m-%d') = DATE_FORMAT(NOW(), '%m-%d')");

            while (rs.next()){
                System.out.print(rs.getString("name") + "\t \t");
                System.out.println(rs.getString("b_day"));
            }

            System.out.println("Пользователи у которых день рождения сегодне в ближайшее время:");
            rs = stmt.executeQuery("SELECT name, b_day FROM `users` where DATE_FORMAT(b_day, '%m-%d') >= DATE_FORMAT(NOW(), '%m-%d') and DATE_FORMAT(b_day, '%m-%d') <= DATE_FORMAT((NOW() + INTERVAL +7 DAY), '%m-%d')");

            while (rs.next()){
                System.out.print(rs.getString("name") + "\t \t");
                System.out.println(rs.getString("b_day"));
            }
            stmt.close();


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        System.out.println("Если вы хотите добавить нового пользователя, введи 1. Для завершения программы введите 0");
        Scanner scanner = new Scanner(System.in);
        statusAdd = scanner.nextInt();


            while (statusAdd == 1){
                AddUser.addUser();
                System.out.println("Если вы хотите добавить нового человека, введи 1. Для завершения программы введите 0");
                statusAdd = scanner.nextInt();
            }


    }
}


