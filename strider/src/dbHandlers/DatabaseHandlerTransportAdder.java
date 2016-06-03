package dbHandlers;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import Model.TravelFramework;
import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerTransportAdder {
	
	private static DatabaseHandlerTransportAdder myinstance;
	private DbAccess dbConnection;
	//private TransportDetails transport;
	private User user;
	private TravelFramework travel;
	
	private DatabaseHandlerTransportAdder(){
		dbConnection = DbAccess.getInstance();
		user = User.getInstance();
		travel = TravelFramework.getInstance();
	}

	public static DatabaseHandlerTransportAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerTransportAdder();
		return myinstance;
	}
	
	/*
	public void setTransportDetails(TransportDetails td)
	{
		transport = td;
	}
	*/
	/*
	public boolean pushTransportDetails(){
		String sql = "SELECT DBA.fAddTransportDetails (" + user.getId() + ", " + travel.getCurrentTravel().getId() + ", "
				+ transport.getIdCurrency() + ", " + transport.getIdTransportCategory() + ", " + transport.getIdTransport() + ", " + transport.getIdCountryArrival() + ", "
				+ transport.getIdCityArrival() + ", '" + transport.getIdCountryLeaving() + "', " + transport.getIdCityLeaving() + ", '"
				+ Date.valueOf(transport.getArrivalDatetime()) + "', '" + Date.valueOf(transport.getLeavingDatetime()) + "', " + transport.getPrice() + ", '"
				+ transport.getLink() + "', '" + transport.getNotes() +"')";
		int status = dbConnection.getIntFromDb(sql);
		if (status == 1) return true;
		else return false;
	}
	*/
	
	public List<String> getProviders(int category){
		return dbConnection.getStringsFromDb("SELECT TransportName FROM DBA.Transport WHERE IDTransportCategory = " + category, Arrays.asList("TransportName"));	
	}
	
	public List<String> getCategories(){
		return dbConnection.getStringsFromDb("SELECT TransportCategoryName FROM DBA.TransportCategory", Arrays.asList("TransportCategoryName"));	
	}
	
	public int getCategoryId(String name){
		//return dbConnection.getIntFromDb("SELECT IDTransportCategory FROM DBA.Transport WHERE TransportName = '" + name + "'");
		//Fixed: Chappi
		return dbConnection.getIntFromDb("SELECT IDTransportCategory FROM DBA.TransportCategory WHERE TransportCategoryName = '" + name + "'");
	}
	
	public int getTransportId(String name){
		return dbConnection.getIntFromDb("SELECT IDTransport FROM DBA.Transport WHERE TransportName = '" + name + "'");
	}
}
