/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testingground;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wesley
 */
public class Hasher {
   private byte[] hashedpw;
   private byte[] salt;

    public Hasher(String password,int seed) {
       makeSalt(seed);
       try {
           hashPW(password, salt);
       } catch (NoSuchAlgorithmException ex) {
           Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
        private String bToHex(byte[] input) {
        Formatter fmat = new Formatter();
        for (int i = 0; i < input.length; i++) {
            fmat.format("%02x", input[i]);

        }
        return fmat.toString();
    }

    private void makeSalt(int id) {
        SecureRandom sr = new SecureRandom(ByteBuffer.allocate(64).putInt(id).array());
        salt = new byte[64];
        //should put the info in salt check if not working
        sr.nextBytes(salt);
    }

    private void hashPW(String pw, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        sha512.update(salt);
        sha512.update(pw.getBytes(), 0, pw.length());
        hashedpw = sha512.digest();

        for (int i = 0; i < 100000; i++) {
            sha512.update(salt);
            sha512.update(hashedpw, 0, hashedpw.length);
            hashedpw = sha512.digest();
        }

        sha512.reset();
        
    }
    public byte[] getHashpwbyte(){
        return hashedpw;
    }
    public String getHashedpwString() {
        return bToHex(hashedpw);
    }
    public byte[] getSaltbyte(){
        return salt;
    }
    public String getSaltString() {
        return bToHex(salt);
    }
   
}
