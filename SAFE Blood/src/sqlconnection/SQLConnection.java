package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class SQLConnection {
	private static Connection connection = null;
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DATABASE_URL = "jdbc:mysql://localhost/java-sql?autoReconnect=true&useSSL=false";
	private final static String USERNAME = "jhernandez145";
	private final static String PASSWORD = "Ferra599^458";

	public static Connection connectToDatabase() {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			JOptionPane.showMessageDialog(null, "Connection established");
			return connection;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			return null;
		}
	}
}
