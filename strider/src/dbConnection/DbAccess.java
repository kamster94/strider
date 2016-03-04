package dbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DbAccess {
	
	public static Logger connectionLogger = Logger.getLogger("connectionLog"); 
	
	private String connectionString;
	private Connection connection;
	
	private String login;
	private String password;
	
	public DbAccess(String login, String password){
		this.login = login;
		this.password = password;
		try {  
			FileHandler fh;  
	        fh = new FileHandler("connectionLog.log");  
	        connectionLogger.addHandler(fh);
	        connectionLogger.setUseParentHandlers(false);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } 
	}
	
	public void setLogin(String login){
		this.login = login;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	private void connectToDb() throws SQLException{
		connectionString = "jdbc:sqlanywhere:uid="+login+";pwd="+password+";eng=traveladvisordb;database=traveladvisordb;host=5.134.69.28:15144";
		connection = DriverManager.getConnection(connectionString); 
	}
	
	public List<String> getStringsFromDb(String sql, List<String> columns){
		try {
			connectToDb();
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
			connectToDb();
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
			connectToDb();
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch(SQLException e){
			connectionLogger.log(Level.SEVERE, e.toString());
			return false;
		}     
	}
}
