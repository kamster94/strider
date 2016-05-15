package Model;

public class NewUser {
	
	private String userName;
	private String city;
	private String email;
	private int countryId;
	private String password;
	private String currency; 
	private int currencyId;
	private int cityId;
	
	public NewUser(String userName, String city, int cityId, String email, int countryId, String password, String currency, int currencyId){
		this.userName = userName;
		this.city = city;
		this.cityId = cityId;
		this.email = email;
		this.countryId = countryId;
		this.password = password;
		this.currency = currency;
		this.currencyId = currencyId;
	}

	public String getUserName() {
		return userName;
	}

	public String getCity() {
		return city;
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

	public String getCurrency() {
		return currency;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public int getCityId() {
		return cityId;
	}

	
}
