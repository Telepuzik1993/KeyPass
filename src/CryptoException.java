public class CryptoException extends Exception {
	private String message;
	CryptoException(String message) {
		this.message = message;
	}

	public void printMessage() {
		System.out.println(this.message);
	}
}