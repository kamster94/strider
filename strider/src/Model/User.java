package Model;

public class User {
	
	private String email;
	private String userName;
	
	private static User self;
	
	private User(){
	}
	
	public static User getInstance(){
		if (self == null) self = new User();
		return self;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
