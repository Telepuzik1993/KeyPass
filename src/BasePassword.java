public interface BasePassword {

	public abstract void setPassword(String password) throws EmptyStringException;

	public abstract String getPassword();
}
