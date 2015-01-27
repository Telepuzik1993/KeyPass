public class EmptyStringException extends Exception {
	private String message;
	EmptyStringException(String message) {
		this.message = message;
	}

	public void printMessage() {
		System.out.println(this.message);
	}
}