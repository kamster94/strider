package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class dbAccess {
	
	public static Logger connectionLogger = Logger.getLogger("connectionLog"); 
	
	private String connectionString;
	private Connection connection;
	
	private void connectToDb(String login, String password) throws SQLException{
		connectionString = "jdbc:sqlanywhere:uid="+login+";pwd="+password+";eng=demo12;database=demo;host=5.134.69.28:15144";
		connection = DriverManager.getConnection(connectionString); 
	}
	
	public List<String> getStringsFromDb(String sql, List<String> columns){
		try {
			connectToDb("DBA","sql");
			PreparedStatement statement = connection.prepareStatement(sql);
	        ResultSet result = statement.executeQuery();
	        List<String> values = new ArrayList<String>();
	        while (result.next()) {
	        	for (String column : columns) {
	    			values.add(result.getString(column));
	    		}
	        }
	        result.close();
	        statement.close();
	        connection.close();
	        return values;
		} catch (SQLException e) {
			connectionLogger.log(Level.SEVERE, e.toString());
			return null;
		}
	}
	
	public List<Float> getFloatsFromDb(String sql, List<String> columns){
		try {
			connectToDb("DBA","sql");
			PreparedStatement statement = connection.prepareStatement(sql);
	        ResultSet result = statement.executeQuery();
	        List<Float> values = new ArrayList<Float>();
	        while (result.next()) {
	        	for (String column : columns) {
	    			values.add(result.getFloat(column));
	    		}
	        }
	        result.close();
	        statement.close();
	        connection.close();
	        return values;
		} catch (SQLException e) {
			connectionLogger.log(Level.SEVERE, e.toString());
			return null;
		}
	}
	
	public boolean pushToDb(String sql, List<Object> params){
		try {
			connectToDb("DBA","sql");
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch(SQLException e){
			connectionLogger.log(Level.SEVERE, e.toString());
			return false;
		}     
	}
}
