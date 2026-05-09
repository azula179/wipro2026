package ncrb;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnec {
	static String url= "jdbc:mysql://localhost:3306/criminal_db";
	static String user="root";
	static String pwd="zodiac";
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, user, pwd);
	}
}
