package com.company;
import javax.xml.transform.Result;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int rout;
        Scanner rou = new Scanner(System.in);
        UserManager.start();
        UserManager.showMenu();
        rout = rou.nextInt();
        while (rout > 0 && rout < 6) {
            switch (rout) {
                case 1:
                    UserManager.addUser();
                    UserManager.showMenu();
                    rout = rou.nextInt();
                    break;
                case 2:
                    UserManager.deleteUser();
                    UserManager.showMenu();
                    rout = rou.nextInt();
                    break;
                case 3:
                    UserManager.editUser();
                    UserManager.showMenu();
                    rout = rou.nextInt();
                    break;
                case 4:
                    UserManager.showAllUsers();
                    UserManager.showMenu();
                    rout = rou.nextInt();
                    break;
                case 5:
                    UserManager.start();
                    UserManager.showMenu();
                    rout = rou.nextInt();
                    break;
            }
        }
    }
}


