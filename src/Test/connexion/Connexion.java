package Test.connexion;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;

public class Connexion {
	
	 private static Connection cn = null;
	 
	 public static Connection getcon() {
			if (cn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}
				catch(ClassNotFoundException ex) {
				System.out.println("Problème de chargement du Driver!");
				System.exit(1);
				}
			

			try {
			 cn = DriverManager.getConnection("jdbc:mysql://localhost/Restaurant", "root","");

			}
			catch (SQLException e) {
			System.err.println("Error opening SQL connection:"+ e.getMessage());
			}
			}
			return cn;
		}
}
