package Website;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author wesley
 */
public class Database {

    private static String uname, pw;

    public Database() {
        try {
            String buffer;
            File file = new File("C:\\Users\\wesley\\Documents\\GitHub\\CS160Project\\TestingGround\\src\\testingground\\stuff.txt");
            Scanner intake = new Scanner(file);
            while (intake.hasNextLine()) {
                buffer = intake.nextLine();
                String[] split = buffer.split("\\s");
                Database.uname = split[0];
                Database.pw = split[1];
            }
        } catch (Exception e) {
            System.err.println(e.fillInStackTrace());
            System.err.println("Badfile check source\nDatabase.java\nline37");
        }
    }

    protected Connection mySQLdbconnect() throws SQLException {
        Connection con = null;
        try {
            Object newInstance = Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("class.forName failed");
            System.err.println(e.fillInStackTrace());
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost", uname, pw);
        } catch (Exception e) {
            System.err.println("Cannot connect to the db given");
           // System.err.println(Arrays.toString(e.getStackTrace()));
            System.err.println(e.getLocalizedMessage());
            System.err.println(e.getCause());
        }
       
        if(con== null){
            System.out.println("not seting con");
        }
        return con;
    }

}
