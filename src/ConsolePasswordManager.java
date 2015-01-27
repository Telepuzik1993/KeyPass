import java.io.*;
import java.util.*;

public class ConsolePasswordManager {
	private static final Integer MAX_STR_LEN = 30;
	private AESCryptoService cryptor;
	private StorageService storage;

	public void init() {
		MasterPassword masterPassword = getMasterPassword();
		this.cryptor = new AESCryptoService();
		try {
			this.cryptor.init(masterPassword);
		} catch (CryptoException e) {
			e.printMessage();
		}
		this.storage = new FileStorageService();
	}

	public void showNames() {
		List<String> names = this.storage.getNames();

		if (names.size() == 0) {
			System.out.println("You don't have any stored passwords\n");
		} else {
			System.out.println("\nPassword Storage");
			System.out.println("--------|--------");
			for(String name : names) {
				System.out.println(name + "\t|********");
			}
			System.out.println("\n");
		}
	}

	public void add() {
		String name = null;
		while (name == null) {
			name = getName();
			for(String str : this.storage.getNames()) {
				if (str.equals(name)) {
					System.out.println("Password name already exists. Please enter deffirent\n");
					name = null;
				}
			}
		}
		String login = this.getLogin();
		String pass = this.getPassword();
		String description = this.getDescription();
		Password password = new Password();
		try {
			password.init(name, login, pass, description);
			Password cryptedPassword = this.cryptor.encrypt(password);
			this.storage.add(cryptedPassword);
		} catch (EmptyStringException e) {
			e.printMessage();
		} catch (CryptoException e) {
			e.printMessage();
		} catch (StorageException e) {
			e.printMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void get() {
		String name = this.getName();

		try {
			Password encryptedPassword = this.storage.get(name);
			Password password = this.cryptor.decrypt(encryptedPassword);
		
			if (password.getLogin() != null) {
				System.out.println("\nLogin : " + password.getLogin());
			}

			if (password.getPassword() != null) {
				System.out.println("Password : " + password.getPassword());
			}

			if (password.getDescription() != null) {
				System.out.println("Description : " + password.getDescription() + "\n");
			}
		} catch (EmptyStringException e) {
			e.printMessage();
		} catch (StorageException e) {
			e.printMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CryptoException e) {
			e.printMessage();
		}
	}

	public void update() {
		String name = this.getName();
		try {
			Password encryptedPassword = this.storage.get(name);
			Password password = this.cryptor.decrypt(encryptedPassword);
			String login = this.getLogin();
			String pass = this.getPassword();
			String description = this.getDescription();
			password.init(name, login, pass, description);
			Password cryptedPassword = this.cryptor.encrypt(password);
			this.storage.update(cryptedPassword);
		} catch (EmptyStringException e) {
			e.printMessage();
		} catch (StorageException e) {
			e.printMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CryptoException e) {
			e.printMessage();
		}
	}

	public void remove() {
		try {
			String name = this.getName();
			this.storage.remove(name);
		} catch (StorageException e) {
			e.printMessage();
		}
	}

	private String getDescription() {
		System.out.println("Enter description:");
		String description = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			description = br.readLine();
			while ((description.length() == 0) || (description.length() > this.MAX_STR_LEN)) {
				System.out.println("Description must be more than zero " + 
							"characters and less than " + this.MAX_STR_LEN + "characters long\n");
				System.out.println("Enter description");
				description = br.readLine();
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return description;
	}

	private String getPassword() {
		System.out.println("Enter password:");
		String password = new String();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			password = br.readLine();
			while ((password.length() == 0) || (password.length() > this.MAX_STR_LEN)) {
				System.out.println("Password must be more than zero " + 
							"characters and less than " + this.MAX_STR_LEN + "characters long\n");
				System.out.println("Enter password");
				password = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return password;
	}

	private String getLogin() {
		System.out.println("Enter login:");
		String login = new String();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			login = br.readLine();
			while ((login.length() == 0) || (login.length() > this.MAX_STR_LEN)) {
				System.out.println("Login must be more than zero " + 
							"characters and less than " + this.MAX_STR_LEN + "characters long\n");
				System.out.println("Enter login");
				login = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return login;
	}

	private String getName() {
		System.out.println("Enter password name:");
		String name = new String();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			name = br.readLine();
			while ((name.length() == 0) || (name.length() > this.MAX_STR_LEN)) {
				System.out.println("Password name must be more than zero " + 
							"characters and less than " + this.MAX_STR_LEN + "characters long\n");
				System.out.println("Enter password name");
				name = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}

	private MasterPassword getMasterPassword() {
		System.out.println("Enter master password:");
		String masterPass = new String();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			masterPass = br.readLine();
			while ((masterPass.length() == 0) || (masterPass.length() > this.MAX_STR_LEN)) {
				System.out.println("Master password must be more than zero " + 
							"characters and less than " + this.MAX_STR_LEN + "characters long\n");
				System.out.println("Enter master password");
				masterPass = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		MasterPassword masterPassword = new MasterPassword();
		try {
			masterPassword.init(masterPass);
		} catch (EmptyStringException e) {
			e.printMessage();
		}
		return masterPassword;
	}
}