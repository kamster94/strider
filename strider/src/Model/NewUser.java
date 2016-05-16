package Model;

public class NewUser {
	
	private String userName;
	private String email;
	private int countryId;
	private String password; 
	private int currencyId;
	private int cityId;
	
	public NewUser(String userName, int cityId, String email, int countryId, String password, int currencyId){
		this.userName = userName;
		this.cityId = cityId;
		this.email = email;
		this.countryId = countryId;
		this.password = password;
		this.currencyId = currencyId;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public int getCountryId() {
		return countryId;
	}

	public String getPassword() {
		return password;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public int getCityId() {
		return cityId;
	}
}
