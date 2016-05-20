package dbHandlers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.AttractionDetails;
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
	
	/*
	public Stage getStage(int stageid)
	{
		Stage stg;
		
	}
	*/
	
	public AttractionDetails getAttractionDetails(int idtrip, int idstage)
	{
		AttractionDetails attraction;
		int id_attraction = dbConnection.getIntFromDb("SELECT IDAttraction FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		int id_currency = dbConnection.getIntFromDb("SELECT IDCurrency FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		int id_country = dbConnection.getIntFromDb("SELECT IDCountry FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		int id_city = dbConnection.getIntFromDb("SELECT IDCity FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		float price = dbConnection.getFloatFromDb("SELECT AttractionPrice FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip);
		String notes = dbConnection.getSingeStringFromDb("SELECT AttractionNotes FROM DBA.AttractionDetail WHERE IDStage = " + idstage + " AND IDTrip = " + idtrip, "AttractionNotes");
		
		attraction = new AttractionDetails(id_attraction, id_country, id_city, id_currency, price, notes);
		return attraction;
	}
	
}
