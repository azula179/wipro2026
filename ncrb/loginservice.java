package ncrb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class loginservice {
	public static boolean login(Scanner snr) {
		try {
			Connection con =DBconnec.getConnection();
            System.out.println("\n----- LOGIN -----");
            System.out.print("Username: ");
            String username = snr.nextLine();
            java.io.Console console = System.console();
            String password;

            if (console != null) {
                char[] pwd = console.readPassword("Password: ");
                password = new String(pwd);
                } 
            else {
                System.out.print("Password: ");
                password = snr.nextLine();
            }
            
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst =con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rt = pst.executeQuery();
            
            if (rt.next()) {
            	
                System.out.println("\nLogin Successful!");
                con.close();
                return true;
                } 
            else {
            	
                System.out.println("\nInvalid Username or Password.");
                con.close();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

	            


