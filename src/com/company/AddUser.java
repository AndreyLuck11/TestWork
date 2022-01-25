package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AddUser {
    private static String name;
    private static String date;
    public static void main(String[] args) {


    }
    public static void addUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя человека: ");
        name = scanner.nextLine();

        System.out.print("Введите дату рождения человека: ");
        date = scanner.nextLine();
        try{
            String url = "jdbc:mysql://localhost:3306/test2";;
            String username = "root";
            String password = "root";


            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();


            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO users (name, b_day) VALUES (?, ?)";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, date);

                int rows = preparedStatement.executeUpdate();

                System.out.printf("Человек добавлен\n");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

}

