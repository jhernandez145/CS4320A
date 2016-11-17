
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class SQLConnection {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/SAFEBLOOD?autoReconnect=true&useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "";
	private String user;
	private String pass;
	private static boolean auth;
	private static int empID;

	public boolean isAuth() {
		return auth;
	}

	public SQLConnection(String user, String pass) {
		this.user = user;
		this.pass = pass;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = SQLConnection.connectToDatabase();
			String SQL = "SELECT * FROM employee WHERE (username = ? and password = ?)";
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);

			preparedStatement.setQueryTimeout(15);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("firstname");
				String usr = resultSet.getString("username");
				String pwd = resultSet.getString("password");
				int empID = resultSet.getInt("employeeID");

				if (user.equals(usr) && pass.equals(pwd)) {
					SQLConnection.auth = true;
					SQLConnection.empID = empID;
				}
			}
			connection = SQLConnection.connectToDatabase();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public static boolean isManager(int employeeID, int mgrID) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		if (empID == employeeID) {
			return true;
		} else {
			try {
				connection = SQLConnection.connectToDatabase();
				String SQL = "SELECT mgrID FROM employee WHERE employeeID = ?";
				preparedStatement = connection.prepareStatement(SQL);
				preparedStatement.setInt(1, employeeID);

				preparedStatement.setQueryTimeout(15);

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					if (resultSet.getInt("mgrID") == empID) {
						return true;
					} else {
						return false;
					}
				}
				connection = SQLConnection.connectToDatabase();
				return false;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return false;
	}

	public static void testConnection() {
		Connection connection = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static Connection connectToDatabase() {
		Connection connection = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		return connection;

	}

	public static void closeConnectionToDatabase(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
				return;
			} else {
				return;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static ResultSet exectuteQuery(String sql) {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = connectToDatabase();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			return resultSet;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static void populateTableRequests() {
		File file = new File("TupleTextFiles\\Requests.txt");
		String sql = "INSERT INTO request (requistID, bloodAmount, bloodType, bloodconfirm, branchID, providerID, requestMonth, requestDay, requestYear)"
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
	}

	public static void populateTableHCProv() {
		File file = new File("TupleTextFiles\\HC_Provider.txt");
		String sql = "INSERT INTO hc_provider (providerID, name, email, address, phoneNumber)" + " VALUES(?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = SQLConnection.connectToDatabase();
			Scanner inputFile = new Scanner(file, "UTF-16");
			while (inputFile.hasNext()) {
				String string = inputFile.nextLine();

				String[] re = string.split(";");

				String providerID = re[0];
				String name = re[1];
				String email = re[2];
				String address = re[3];
				String phoneNumber = re[4];

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, providerID);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, email);
				preparedStatement.setString(4, address);
				preparedStatement.setString(5, phoneNumber);

				preparedStatement.executeUpdate();
			}
			SQLConnection.closeConnectionToDatabase(connection);
			inputFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Exception: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			} else {

			}
		}
	}

	public static void populateTableBranch() {
		File file = new File("TupleTextFiles\\Branch.txt");
		String sql = "INSERT INTO branch (branchID, branchName, address, phoneNumber, email)"
				+ " VALUES(?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = SQLConnection.connectToDatabase();
			Scanner inputFile = new Scanner(file, "UTF-16");
			while (inputFile.hasNext()) {
				String string = inputFile.nextLine();

				String[] re = string.split(";");
				int branchID = Integer.parseInt(re[0]);
				String branchName = re[1];
				String address = re[2];
				String phoneNumber = re[3];
				String email = re[4];

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, branchID);
				preparedStatement.setString(2, branchName);
				preparedStatement.setString(3, address);
				preparedStatement.setString(4, phoneNumber);
				preparedStatement.setString(5, email);

				preparedStatement.executeUpdate();
			}
			SQLConnection.closeConnectionToDatabase(connection);
			inputFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Exception: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			} else {

			}
		}
	}

	public static void populateTableEmployee() {
		File file = new File("TupleTextFiles\\Employee.txt");
		String sql = "INSERT INTO employee (position, salary, phoneNumber, firstName, lastName, email, mgrID, address, branchID)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = SQLConnection.connectToDatabase();

			Scanner inputFile = new Scanner(file, "UTF-16");
			while (inputFile.hasNextLine()) {
				String string = inputFile.nextLine();

				String[] re = string.split(";");
				// int empID = Integer.parseInt(re[0]);
				String position = re[1];
				Double salary = Double.parseDouble(re[2]);
				String phoneNumber = re[3];
				String firstName = re[4];
				String lastName = re[5];
				String email = re[6];
				int mgrID = Integer.parseInt(re[7]);
				String address = re[8];
				int branchID = Integer.parseInt(re[9]);

				preparedStatement = connection.prepareStatement(sql);

				// pstmt.setInt(1, empID);
				preparedStatement.setString(1, position);
				preparedStatement.setDouble(2, salary);
				preparedStatement.setString(3, phoneNumber);
				preparedStatement.setString(4, firstName);
				preparedStatement.setString(5, lastName);
				preparedStatement.setString(6, email);
				preparedStatement.setInt(7, mgrID);
				preparedStatement.setString(8, address);
				preparedStatement.setInt(9, branchID);

				preparedStatement.executeUpdate();

			}

			SQLConnection.closeConnectionToDatabase(connection);
			inputFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Exception: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			} else {

			}
		}
	}

	public static void populateTableDonor() {
		File file = new File("TupleTextFiles\\Donor.txt");
		String sql = "INSERT INTO donor (firstName, lastName, address, bloodType, phoneNumber, email)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = SQLConnection.connectToDatabase();
			Scanner inputFile = new Scanner(file, "UTF-16");
			while (inputFile.hasNextLine()) {
				String string = inputFile.nextLine();

				String[] re = string.split(";");
				String firstName = re[0];
				String lastName = re[1];
				String address = re[2];
				String bloodType = re[3];
				String phoneNumber = re[4];
				String email = re[5];

				preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, address);
				preparedStatement.setString(4, bloodType);
				preparedStatement.setString(5, phoneNumber);
				preparedStatement.setString(6, email);

				preparedStatement.executeUpdate();
			}

			SQLConnection.closeConnectionToDatabase(connection);
			inputFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Exception: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			} else {

			}
		}
	}

	public static void populateTableBlood() {
		File file = new File("TupleTextFiles\\Blood.txt");
		String sql = "INSERT INTO blood (donationMonth, donationDay, donationYear, bloodAmount, donorID)"
				+ " VALUES (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = SQLConnection.connectToDatabase();
			Scanner inputFile = new Scanner(file, "UTF-16");
			while (inputFile.hasNextLine()) {
				String string = inputFile.nextLine();

				String[] re = string.split(";");
				String donationMonth = re[0];
				String donationDay = re[1];
				String donationYear = re[2];
				String bloodAmount = re[3];
				String donorID = re[4];

				preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, donationMonth);
				preparedStatement.setString(2, donationDay);
				preparedStatement.setString(3, donationYear);
				preparedStatement.setString(4, bloodAmount);
				preparedStatement.setString(5, donorID);

				preparedStatement.executeUpdate();
			}

			SQLConnection.closeConnectionToDatabase(connection);
			inputFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Exception: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			} else {

			}
		}
	}

	public static void addTuple(String[] attributes, String sql) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = SQLConnection.connectToDatabase();

			preparedStatement = connection.prepareStatement(sql);

			for (int index = 1; index <= attributes.length; index++) {
				preparedStatement.setString(index, attributes[index - 1]);
			}
			preparedStatement.executeUpdate();
			SQLConnection.closeConnectionToDatabase(connection);

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			}
		}
	}

	/*
	 * so, in order to create an SQL statement, we are going to need the
	 * following information: ~SELECT: What information are we going to need to
	 * return? These are the attributes we are searching for. ~WHERE: This
	 * includes multiple fields. 1. attribute = 'value' 2. table_1 JOINS table_2
	 * on attribute_1 = attribute_2 if we have to span multiple tables to get
	 * our return fields, so we have to search where they are stored in. ~FROM:
	 * This is the from. If using the JOINS statement, then this will be
	 * table_1, else it will be the one table all tuples/attributes are found
	 * in.
	 */

	public static Vector<String> getAllTables() {
		Connection connection = null;
		ResultSet resultSet = null;
		String[] types = { "TABLE" };
		Vector<String> tableNames = new Vector<String>();
		try {
			connection = SQLConnection.connectToDatabase();
			DatabaseMetaData metaData = connection.getMetaData();
			resultSet = metaData.getTables("SAFEBLOOD", null, "%", types);

			while (resultSet.next()) {
				tableNames.add(resultSet.getString(3)); // column 3 holds table
														// names
			}
			resultSet.close();
			SQLConnection.closeConnectionToDatabase(connection);
			return tableNames;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.out.println("SQLException: " + e.getMessage());
				}
			}
		}
		return null;
	}

	public static Map<String, Vector<String>> getAttributeNamesByTable() {
		Vector<String> tableNames = SQLConnection.getAllTables();
		Map<String, Vector<String>> attributesByTable = new ConcurrentHashMap<String, Vector<String>>();
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = SQLConnection.connectToDatabase();
			DatabaseMetaData metadata = connection.getMetaData();
			for (int index = 0; index < tableNames.size(); index++) {
				Vector<String> attributeNames = new Vector<String>();

				resultSet = metadata.getColumns("SAFEBLOOD", null, tableNames.elementAt(index), "%");

				while (resultSet.next()) {
					attributeNames.add(resultSet.getString(4)); // column 4
																// holds column
																// names
				}
				attributesByTable.put(tableNames.get(index), attributeNames);
			}
			SQLConnection.closeConnectionToDatabase(connection);
			resultSet.close();
			return attributesByTable;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.out.println("SQLException: " + e.getMessage());
				}
			}
		}
		return null;
	}

	public static Vector<String> getTablesRequired(Vector<String> attributes) {
		Vector<String> tablesRequired = new Vector<String>();
		Vector<String> allTables = SQLConnection.getAllTables();
		Map<String, Vector<String>> attributesByTable = SQLConnection.getAttributeNamesByTable();

		for (int indexAttribute = 0; indexAttribute < attributes.size(); indexAttribute++) { // for
																								// every
																								// attribute
																								// to
																								// check
			for (int indexTable = 0; indexTable < allTables.size(); indexTable++) { // for
																					// every
																					// table
																					// available
				if (attributesByTable.get(allTables.elementAt(indexTable))
						.contains(attributes.elementAt(indexAttribute))) {
					if (!tablesRequired.contains(allTables.elementAt(indexTable))) { // if
																						// it
																						// is
																						// not
																						// already
																						// added
																						// to
																						// the
																						// tablesRequired
																						// Vector
						tablesRequired.add(allTables.get(indexTable));
					}

				}
			}
		}

		return tablesRequired;
	}

	public static String createJOINS(Vector<String> tablesRequired) {
		if (tablesRequired.size() == 1) {
			return "";
		} else {
			Map<String, String> primaryKeysByTable = SQLConnection.getPrimaryKeysByTable();
			Map<String, String> foreignKeyByDestination = new ConcurrentHashMap<String, String>();
			Map<String, Map<String, String>> foreignKeyByDestByPrimaryTable = new ConcurrentHashMap<String, Map<String, String>>();

			tablesRequired.forEach((destinationTable) -> {
				primaryKeysByTable.forEach((tablePrimary, primaryKey) -> {
					if (!tablePrimary.equals(destinationTable)) {
						if (SQLConnection.attributeIsInTable(primaryKey, destinationTable)) {
							foreignKeyByDestination.put(destinationTable, primaryKey);
							foreignKeyByDestByPrimaryTable.put(tablePrimary, foreignKeyByDestination);
						}

					}
				});
			});

			return (foreignKeyByDestByPrimaryTable).toString();

		}
	}

	// This reduces duplicate values in the map
	public static <k, v> Map<v, k> invertMap(Map<k, v> map) {
		Map<v, k> result = new ConcurrentHashMap<v, k>();
		for (Entry<k, v> entry : map.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}

	public static boolean attributeIsInTable(String attribute, String table) {
		return SQLConnection.getAttributesInTable(table).contains(attribute);
	}

	public static boolean tableIsRequired(String table) {
		return SQLConnection.getAllTables().contains(table);
	}

	public static Vector<String> getPrimaryKeysAllTables() {
		Connection connection = null;
		ResultSet resultSet = null;
		Vector<String> allPrimaryKeys = new Vector<String>();
		Vector<String> allTables = SQLConnection.getAllTables();
		try {
			connection = SQLConnection.connectToDatabase();
			DatabaseMetaData metaData = connection.getMetaData();

			for (String tableName : allTables) {
				resultSet = metaData.getPrimaryKeys(null, null, tableName);

				while (resultSet.next()) {
					allPrimaryKeys.add(resultSet.getString(4));
				}
			}
			SQLConnection.closeConnectionToDatabase(connection);
			resultSet.close();
			return allPrimaryKeys;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.out.println("SQLException: " + e.getMessage());
				}
			}
		}
		return null;
	}

	public static Map<String, String> getPrimaryKeysByTable() {
		Map<String, String> primaryKeysPerTable = new ConcurrentHashMap<String, String>();
		Vector<String> allTables = SQLConnection.getAllTables();
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = SQLConnection.connectToDatabase();
			DatabaseMetaData metaData = connection.getMetaData();

			for (String tableName : allTables) {
				resultSet = metaData.getPrimaryKeys(null, null, tableName);

				while (resultSet.next()) {
					primaryKeysPerTable.put(tableName, resultSet.getString(4));
				}
			}
			SQLConnection.closeConnectionToDatabase(connection);
			resultSet.close();
			return primaryKeysPerTable;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			if (connection != null) {
				SQLConnection.closeConnectionToDatabase(connection);
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.out.println("SQLException: " + e.getMessage());
				}
			}
		}
		return null;

	}

	public static Vector<String> getAttributesInTable(String table) {
		return SQLConnection.getAttributeNamesByTable().get(table);
	}

	public static ResultSet getAllEmployeeResultSet() {
		String sql = "SELECT * FROM employee";
		return SQLConnection.exectuteQuery(sql);
	}

	public static ResultSet getAllBloodResultSet() {
		String sql = "SELECT * FROM blood";
		return SQLConnection.exectuteQuery(sql);
	}

	public static Object[] getMetaData(String table, String SQL) {
		Vector<String> attributes = SQLConnection.getAttributesInTable(table);
		String primaryKey = SQLConnection.getPrimaryKeysByTable().get(table);
		ResultSet resultSet = SQLConnection.exectuteQuery(SQL);

		Object[] metaData = { attributes, primaryKey, resultSet };

		return metaData;
	}

	public static ResultSet getDonationPerDonorReport() {
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT LastName, FirstName, BloodAmount FROM Donor JOIN Blood on Donor.donorID = Blood.donorID";
		try {
			Connection connection = SQLConnection.connectToDatabase();
			st = connection.createStatement();
			rs = st.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet getRequestByHC_Provider() {
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT hc_provider.providerID, requestID, requestMonth, requestDay, requestYear FROM hc_provider, request WHERE hc_provider.providerID=request.providerID";
		try {
			Connection connection = SQLConnection.connectToDatabase();
			st = connection.createStatement();
			rs = st.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet getInventoryReport() {
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT blood.branchID, BloodType, COUNT(*) "
				+ "FROM blood JOIN branch on blood.branchID = branch.branchID "
				+ "JOIN donor on blood.donorID = donor.donorID " + "GROUP BY bloodType";
		try {
			Connection connection = SQLConnection.connectToDatabase();
			st = connection.createStatement();
			rs = st.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet getBloodTypeReport() {
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT bloodType, COUNT(*) FROM Donor GROUP BY bloodType";
		try {
			Connection connection = SQLConnection.connectToDatabase();
			st = connection.createStatement();
			rs = st.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet getDonationByBranches(){
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT blood.branchID, COUNT(*) FROM blood GROUP BY blood.branchID";
		try {
			Connection connection = SQLConnection.connectToDatabase();
			st = connection.createStatement();
			rs = st.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet getDonationPerIndividual(){
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT donor.donorID, donor.firstName, donor.lastName, bloodType, COUNT(*) FROM donor JOIN blood ON donor.donorID = blood.donorID GROUP BY donor.donorID";
		try {
			Connection connection = SQLConnection.connectToDatabase();
			st = connection.createStatement();
			rs = st.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
