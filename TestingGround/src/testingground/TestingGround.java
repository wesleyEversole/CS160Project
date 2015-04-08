/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testingground;

import java.nio.ByteBuffer;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wesley
 */
public class TestingGround {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, DigestException {
       String[] password = {"WesleyEversole", "WesleyEversoles", "1", "test", "WesleyEversole","somehashedvalue"};
       for (int i = 0; i < password.length; i++) {
         String password1 = password[i];
          System.out.println("password = " + password1);
         System.out.println("Salt = " + bToHex(makeSalt(i)));
          //System.out.println("Salt hashed by its self = "+hashPW(makeSalt(i)));
         System.out.println("hashedpassword = " + hashPW(password1, makeSalt(10)));

      }
      // System.out.println("sfmat = "+ sfmat.toString());
        
        Database db = new Database();
        Statement statement = null;
        ResultSet rs = null;
        int id = -1;
        String accName = "";
        String psw = "";
        String email ="my.email@sjsu.edu" ;
        try {
            try (Connection con = db.mySQLdbconnect()) {
                statement = con.createStatement();
                
                rs = statement.executeQuery("SELECT idAccounts, userName, password FROM mydb.accounts ma WHERE ma.email='"+email+"'");
                System.out.println("email="+email);
                while (rs.next()) {
                    id = rs.getInt("idAccounts");
                    System.out.println("id="+id);
                    accName = rs.getString("userName");
                    psw = rs.getString("password");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
                rs = null;
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                }
                statement = null;
            }

        }
        System.out.println("id = " + id);
        System.out.println("accName = " + accName);
        System.out.println("psw = " + psw);

    }

//        for (int foo = 0; foo < 100000; foo++) {
//            SecureRandom sr = new SecureRandom(ByteBuffer.allocate(64).putInt(foo).array());
//            byte[] salt = new byte[64];
//            sr.nextBytes(salt);
//            if (!saltlist.contains(salt)) {
//                saltlist.add(salt);
//            } else {
//
//                Formatter sfmat = new Formatter();
//                for (int i = 0; i < salt.length; i++) {
//                    sfmat.format("%02x", salt[i]);
//
//                }
//                System.out.println("saltlist contains salt(" + sfmat.toString() + ")\n at pos " + saltlist.indexOf(salt));
//            }
//        }
    private static byte[] makeSalt(int id) {
        SecureRandom sr = new SecureRandom(ByteBuffer.allocate(64).putInt(id).array());
        byte[] salt = new byte[64];
        sr.nextBytes(salt);
        return salt;
    }

    private static String bToHex(byte[] input) {
        Formatter fmat = new Formatter();
        for (int i = 0; i < input.length; i++) {
            fmat.format("%02x", input[i]);

        }
        return fmat.toString();
    }

    private static String hashPW(String pw, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        sha512.update(salt);
        sha512.update(pw.getBytes(), 0, pw.length());
        byte[] hashedpassword = sha512.digest();

        for (int i = 0; i < 100000; i++) {
            sha512.update(salt);
            sha512.update(hashedpassword, 0, hashedpassword.length);
            hashedpassword = sha512.digest();
        }

        sha512.reset();
        return bToHex(hashedpassword);
    }
}
