package dbHandlers;

import java.util.Arrays;
import java.util.List;
import dbConnection.DbAccess;

public class DatabaseHandlerTransportAdder 
{
	private static DatabaseHandlerTransportAdder myinstance;
	
	private DatabaseHandlerTransportAdder() { }

	public static DatabaseHandlerTransportAdder getInstance()
	{
		if(myinstance == null)myinstance = new DatabaseHandlerTransportAdder();
		return myinstance;
	}

	public List<String> getProviders(int category)
	{
		return  DbAccess.getInstance().getStringsFromDb("SELECT TransportName FROM DBA.Transport WHERE IDTransportCategory = " + category, Arrays.asList("TransportName"));	
	}
	
	public List<String> getCategories()
	{
		return  DbAccess.getInstance().getStringsFromDb("SELECT TransportCategoryName FROM DBA.TransportCategory", Arrays.asList("TransportCategoryName"));	
	}
	
	public int getCategoryId(String name)
	{
		return  DbAccess.getInstance().getIntFromDb("SELECT IDTransportCategory FROM DBA.TransportCategory WHERE TransportCategoryName = '" + name + "'");
	}
	
	public int getTransportId(String name)
	{
		return  DbAccess.getInstance().getIntFromDb("SELECT IDTransport FROM DBA.Transport WHERE TransportName = '" + name + "'");
	}
}
