/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uphoto
 */
public class Encryption {
    
    public static String encryptPassword(String srt){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            
            md5.update(srt.getBytes());
            
            byte[] digested = md5.digest();
            
            StringBuilder string = new StringBuilder();
            for (byte b : digested) {
                b = (byte) map(Math.abs(b), 0, 127, 35, 122);
                string.append((char)b);
            }
            
            return string.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public static void main(String[] args){
        Scanner input;
        
        while (true) {
            System.out.print("Your String: ");
            input = new Scanner(System.in);
            System.out.println(encryptPassword(input.nextLine()));
        }
    }
    
    private static int map(int i, int a1, int a2, int b1, int b2) {
        if (i<a1) return a1;
        if (i>a2) return a2;
        float temp = i-a1;
        temp /=(float) (a1-a2);
        
        temp *= b1-b2;
        temp += b1;
        return (int)temp;
    }
}
