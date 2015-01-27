public class StorageException extends Exception {
	private String message;
	StorageException(String message) {
		this.message = message;
	}

	public void printMessage() {
		System.out.println(this.message);
	}
}