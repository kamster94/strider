package dbHandlers;

import java.sql.Date;

import Model.Transport;
import Model.TravelFramework;
import Model.User;
import dbConnection.DbAccess;

public class DatabaseHandlerTransportAdder {
	
	public DbAccess dbConnection;
	private Transport transport;
	private User user;
	private TravelFramework travel;
	
	public DatabaseHandlerTransportAdder(){
		dbConnection = DbAccess.getInstance();
		user = User.getInstance();
		travel = TravelFramework.getInstance();
	}

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
}
