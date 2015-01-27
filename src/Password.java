public class Password implements BasePassword {

	private String name;
	private String login;
	private String password;
	private String description;

	public void init(String name, String login, String password, String description) throws EmptyStringException {
		this.setName(name);
		this.setLogin(login);
		this.setPassword(password);
		this.setDescription(description);
	}

	public void setName(String name) throws EmptyStringException {
		if (name.length() == 0) {
			throw new EmptyStringException("Name field in password object must be not empty string");
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	public void setPassword(String password) throws EmptyStringException {
		if (password.length() == 0) {
			throw new EmptyStringException("Password field in password object must be not empty");
		}
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}