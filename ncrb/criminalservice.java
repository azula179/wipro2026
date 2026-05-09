package ncrb;

import java.sql.*;
import java.util.Scanner;

public class criminalservice {
	public static void add(Scanner snr) {
		try {
			Connection con=DBconnec.getConnection();
			System.out.print("enter name:");
			String name = snr.nextLine();
			
			 System.out.print("enter age: ");
	         int age = snr.nextInt();
	         snr.nextLine();
	         
			 System.out.print("enter height (in cm): ");
	         int height = snr.nextInt();
	         snr.nextLine();
	         
	         System.out.print("gang affiliation (Y/N): ");
	         String gang_affil = snr.nextLine();
	            
	         System.out.print("status (arrested/released/wanted): ");
	         String status = snr.nextLine();
	         
	         System.out.print("total registered cases: ");
	         int reg_cases = snr.nextInt();
	         
	         System.out.print("sentence awarded: ");
	         int sentence = snr.nextInt();
	         snr.nextLine();
	         
	         
	         String query = "INSERT INTO offender(name, age, height, gang_affil, status, reg_cases, sentence) VALUES(?,?,?,?,?,?,?)";
	         PreparedStatement pst =con.prepareStatement(query);
	         pst.setString(1, name);
	         pst.setInt(2, age);
	         pst.setInt(3, height);
	         pst.setString(4, gang_affil);
	         pst.setString(5, status);
	         pst.setInt(6,  reg_cases);
	         pst.setInt(7, sentence);
	         
	         int rows = pst.executeUpdate();
	         System.out.println(rows + " record inserted.");
	         con.close();		
		}
		 catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public static void view() {
		try {
			Connection con=DBconnec.getConnection();
			String query="SELECT*FROM offender";
			Statement st=con.createStatement();
			ResultSet rt=st.executeQuery(query);
			
			 System.out.println("\n========= CRIMINAL RECORDS =========");

	         System.out.printf( "%-5s %-15s %-5s %-15s %-20s %-10s %-20s %-20s\n",
	                    "ID",
	                    "NAME",
	                    "AGE",
	                    "HEIGHT",
	                    "GANG AFFILIATION",
	                    "STATUS",
	                    "REGISTERED CASES",
	                    "SENTENCE");
	         while(rt.next()) {
	        	 System.out.printf("%-5s %-15s %-5s %-15s %-20s %-10s %-20s %-20s\n", 
						 rt.getInt("criminal_id"),
	                     rt.getString("name"),
	                     rt.getInt("age"),
	                     rt.getInt("height"),
	                     rt.getString("gang_affil"),
	                     rt.getString("status"),
	                     rt.getInt("reg_cases"),
	                     rt.getInt("sentence"));
	                     		 
			}
	         con.close();
	}
	catch (Exception e) {
		e.printStackTrace();}
	}
	
	public static void search(Scanner snr) {
		try {
            Connection con =DBconnec.getConnection();
            System.out.print("enter criminal id: ");
            int id = snr.nextInt();
            String query ="SELECT * FROM offender WHERE criminal_id=?";
            PreparedStatement pst =con.prepareStatement(query);
            pst.setInt(1, id);

            ResultSet rt =pst.executeQuery();
            if (rt.next()) {
                System.out.println("\nRecord Found:");
                System.out.println(
                        rt.getInt("criminal_id") + " | " +
                        rt.getString("name") + " | " +
                        rt.getInt("age") + " | " +
                        rt.getString("gang_affil") + " | " +
                        rt.getString("status") + " | " +
                        rt.getInt("reg_cases") + " | " +
                        rt.getInt("sentence") + " | " );
            } else {
                System.out.println("No record found.");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Scanner snr) {

        try {
            Connection con =DBconnec.getConnection();
            System.out.print("Enter Criminal ID: ");
            int id = snr.nextInt();
            snr.nextLine();
            System.out.print("Enter New Status: ");
            String status = snr.nextLine();
            String query ="UPDATE offender SET status=? WHERE criminal_id=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, status);
            pst.setInt(2, id);

            int rows =pst.executeUpdate();
            System.out.println(rows + " record updated.");
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(Scanner snr) {

        try {

            Connection con =DBconnec.getConnection();
            System.out.print("Enter Criminal ID: ");
            int id = snr.nextInt();
            String query ="DELETE FROM offender WHERE criminal_id=?";
            PreparedStatement pst =con.prepareStatement(query);
            pst.setInt(1, id);
            
            int rows =pst.executeUpdate();
            System.out.println(rows + " record deleted.");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }	
	
}
