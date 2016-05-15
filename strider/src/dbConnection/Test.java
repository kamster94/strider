package dbConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		DbAccess test = new DbAccess("Kamster","sql");
		int create = test.getIntFromDb("CALL DBA.fAddToUsers (@email = 'kam@test.pl',"
				+ " @haslo = 'trudnehaslo', @username = 'testkamster', @idcurrency = 1, @idcountry = 1, @idcity = 1)");
		System.out.println(create);
	}
}
