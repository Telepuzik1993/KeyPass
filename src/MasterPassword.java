public class MasterPassword implements BasePassword {
	
	private String password;

	public void init(String password) throws EmptyStringException {
		this.setPassword(password);
	}

	public void setPassword(String password) throws EmptyStringException {
		if (password.length() == 0) {
			throw new EmptyStringException("Password field in MasterPassword object must not be empty string");
		}
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}