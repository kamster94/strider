package dbHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import dbConnection.DbAccess;

public class DatabaseHandlerStage 
{
	private static DatabaseHandlerStage myinstance;
	private static DbAccess dbConnection;
	
	private DatabaseHandlerStage() 
	{
		dbConnection = DbAccess.getInstance();
	}
	
	public static DatabaseHandlerStage getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerStage();
		return myinstance;
	}
	
	public List<Integer> getStageIdentifiers(int iduser, int idtravel)
	{
		List<Integer> stageids = new ArrayList<Integer>();
		String sql = "SELECT IDStage FROM DBA.Stage WHERE IDUser = " + iduser + " AND IDTrip = " + idtravel;
		stageids = dbConnection.getIntegersFromDb(sql, Arrays.asList("IDStage"));
		return stageids;
	}
	
	public int getStageType(int iduser, int idtravel, int idstage)
	{
		String sql = "SELECT DBA.fGetStageState(" + iduser + "," + idtravel + "," + idstage +")";
		int stagetype = dbConnection.getIntFromDb(sql);
		return stagetype;
	}
	
	public int getAttractionDetailsId(int idtrip, int idstage)
	{
		int id_attractiondetail = dbConnection.getIntFromDb("SELECT IDAttraction FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		return id_attractiondetail;
	}
	
	public int getHotelDetailsId(int idtrip, int idstage)
	{
		int id_hoteldetail = dbConnection.getIntFromDb("SELECT IDHotel FROM DBA.HotelDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		return id_hoteldetail;
	}
	
	public int getTransportDetailsId(int idtrip, int idstage)
	{
		int id_transportdetail = dbConnection.getIntFromDb("SELECT IDTransport FROM DBA.TransportDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		return id_transportdetail;
	}
}
