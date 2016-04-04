package dbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DbAccess extends Thread{
	
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
	
	public void run(){
		try {
			connectToDb();
		} catch (SQLException e) {
			connectionLogger.log(Level.SEVERE, e.toString());
		}
    }
	
	public boolean testConnection(){
		try {
			connectToDb();
			return true;
		} catch (SQLException e) {
			connectionLogger.log(Level.SEVERE, e.toString());
			return false;
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
	
	public boolean pushToDb(String sql){
		try {
			connectToDb();
			PreparedStatement statement = connection.prepareStatement(sql);
			connectionLogger.log(Level.SEVERE, sql);
			statement.executeUpdate(sql);
			return true;
		} catch(SQLException e){
			connectionLogger.log(Level.SEVERE, e.toString());
			return false;
		}     
	}
	
	public boolean checkBoolInDb(String sql, List<String> params){
		try {
			connectToDb();
			PreparedStatement statement = connection.prepareStatement(sql);
			int index = 1;
			for (String param : params){
				statement.setString(index, param);
				index++;
			}
	        ResultSet result = statement.executeQuery();
			if (result.next()){
				if (result.getInt(0) == 0) return false;
				else return true;
			}
			else return false;
		} catch(SQLException e){
			connectionLogger.log(Level.SEVERE, e.toString());
			return false;
		}     
	}
	
	public int getIntFromDb(String sql){
		try {
			connectToDb();
			PreparedStatement statement = connection.prepareStatement(sql);
	        ResultSet result = statement.executeQuery();
	        int value = -1;
	        if (result.next()) {
	    		value = result.getInt(0);
	        }
	        result.close();
	        statement.close();
	        connection.close();
	        return value;
		} catch (SQLException e) {
			connectionLogger.log(Level.SEVERE, e.toString());
			return -1;
		}
	}
}
