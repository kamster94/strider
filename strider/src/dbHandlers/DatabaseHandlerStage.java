package dbHandlers;

import java.sql.Date;
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
}
