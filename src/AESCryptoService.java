import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import org.apache.commons.codec.binary.Base64;

public class AESCryptoService implements CryptoService {
	private final String ALGORITHM = "AES";
	private final String CIPHER_MODE = "AES/ECB/PKCS5Padding";
	private SecretKey secretKey;

	public void init(MasterPassword masterPassword) throws CryptoException {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA");
				String masterPass = masterPassword.getPassword();
				messageDigest.update(masterPass.getBytes());
				byte[] key = messageDigest.digest();
				key = Arrays.copyOf(key, 16);
				this.secretKey = new SecretKeySpec(key, this.ALGORITHM);
			} catch (NoSuchAlgorithmException e) {
				throw new CryptoException("Wrong crypto algorithm");
			}
	}

	public Password encrypt(Password password) throws EmptyStringException, CryptoException {
		try {
			Cipher cipher = Cipher.getInstance(this.CIPHER_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
		
			String login = password.getLogin();
			String encryptedLogin = Base64.encodeBase64String(cipher.doFinal(login.getBytes()));
			password.setLogin(encryptedLogin);

			String pass = password.getPassword();
			String encryptedPass = Base64.encodeBase64String(cipher.doFinal(pass.getBytes()));
			password.setPassword(encryptedPass);

			String description = password.getDescription();
			String encryptedDescription = Base64.encodeBase64String(cipher.doFinal(description.getBytes()));
			password.setDescription(encryptedDescription);
		} catch (InvalidKeyException e) {
			throw new CryptoException("Wrong crypto key");
		} catch (IllegalBlockSizeException e) {
			throw new CryptoException("Wrong block size of crypto algorithm");
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("Wrong crypto algorithm");
		} catch (NoSuchPaddingException e) {
			throw new CryptoException("Wrong padding of crypto algorithm");
		} catch (BadPaddingException e) {
			throw new CryptoException("Bad padding of crypto algorithm");
		}

		return password;
	}

	public Password decrypt(Password password) throws EmptyStringException, CryptoException {
		try {
			Cipher cipher = Cipher.getInstance(this.CIPHER_MODE);
			cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
		
			String login = password.getLogin();
			byte[] decryptedLogin = cipher.doFinal(Base64.decodeBase64(login.getBytes()));
			password.setLogin(new String(decryptedLogin));

			String pass = password.getPassword();
			byte[] decryptedPass = cipher.doFinal(Base64.decodeBase64(pass.getBytes()));
			password.setPassword(new String(decryptedPass));

			String description = password.getDescription();
			byte[] decryptedDescription = cipher.doFinal(Base64.decodeBase64(description.getBytes()));
			password.setDescription(new String(decryptedDescription));
		} catch (InvalidKeyException e) {
			throw new CryptoException("Wrong crypto key");
		} catch (IllegalBlockSizeException e) {
			throw new CryptoException("Wrong block size of crypto algorithm");
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("Wrong crypto algorithm");
		} catch (NoSuchPaddingException e) {
			throw new CryptoException("Wrong padding of crypto algorithm");
		} catch (BadPaddingException e) {
			throw new CryptoException("Wrong master password");
		}

		return password;
	}
}