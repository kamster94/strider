package dbConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		DbAccess test = new DbAccess("Kamster","sql");
		List<String> lista = new ArrayList<String>(test.getStringsFromDb("SELECT * FROM DBA.City", Arrays.asList("CityName")));
		for (String a : lista){
			System.out.println(a);
		}
	}
}
