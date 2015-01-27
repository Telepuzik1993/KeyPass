public interface CryptoService {
	
	public abstract Password encrypt(Password password) throws EmptyStringException, CryptoException;

	public abstract Password decrypt(Password password) throws EmptyStringException, CryptoException;
}