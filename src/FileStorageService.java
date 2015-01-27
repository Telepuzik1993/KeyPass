import java.io.*;
import java.util.*;

public class FileStorageService implements StorageService {
	private static final String FILE_EXTENSION = ".pas";
	private static final String BASEDIR = System.getProperty("user.dir");
	private static final String STORAGE_NAME = "storage";
	private static final String SEPARATOR = "0x010x010x01";
	private String storagePath;

	FileStorageService() {
		this.storagePath = BASEDIR + File.separator + STORAGE_NAME;
		File fileDir = new File(this.storagePath);
		if (!fileDir.isDirectory()) {
			fileDir.mkdir();
		}
	}

	public void add(Password password) throws IOException, StorageException {
		try {
			String passName = password.getName() + this.FILE_EXTENSION;
			String passPath = this.storagePath + File.separator + passName;
			String data = password.getLogin() + this.SEPARATOR + password.getPassword() + this.SEPARATOR + password.getDescription();
			File fileOut = new File(passPath);
			if (!fileOut.exists()) {
				fileOut.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut), "UTF-8"));
			bw.write(data);
			bw.close();
		} catch (FileNotFoundException e) {
			throw new StorageException("File not found");
		} catch (UnsupportedEncodingException e) {
			throw new StorageException("Wrong encoding type");
		}
	}

	public Password get(String passName) throws IOException, EmptyStringException, StorageException {
		try {
			String passFile = passName + this.FILE_EXTENSION;
			String passPath = this.storagePath + File.separator + passFile;
			File fileIn = new File(passPath);
			if (fileIn.exists()) {
				String line = null;
				String data = "";
				String[] dataSplited = new String[3];
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn), "UTF-8"));
				while((line = br.readLine()) != null) {
					data += line;
				}
				dataSplited = data.split(this.SEPARATOR);
				br.close();
				Password password = new Password(); 
				password.init(passName, dataSplited[0], dataSplited[1], dataSplited[2]);
				return password;
			}
			else throw new StorageException("File with name " + passName + " does not exists");
		} catch (FileNotFoundException e) {
			throw new StorageException("File not found");
		} catch (UnsupportedEncodingException e) {
			throw new StorageException("Wrong encoding type");
		}
	}

	public void update(Password password) throws IOException, StorageException {
		String passName = password.getName() + this.FILE_EXTENSION;
		String passPath = this.storagePath + File.separator + passName;
		File fileUpdate = new File(passPath);
		if (fileUpdate.exists()) {
			this.add(password);
		}
	}

	public void remove(String passName) throws StorageException{
		String passFile = passName + this.FILE_EXTENSION;
		String passPath = this.storagePath + File.separator + passFile;
		File fileRemove = new File(passPath);
		if (fileRemove.exists()) {
			fileRemove.delete();
		}
		else throw new StorageException("File with name " + passName + " does not exists");
	}

	public List<String> getNames(){
		String folder = this.storagePath;
        List<String> fileNames = new ArrayList<String>();
        for (File file : new File(folder).listFiles()) {
            if (!file.isDirectory()) {
            	String passName = file.getName();
            	passName = passName.substring(0, passName.length() - this.FILE_EXTENSION.length());
            	fileNames.add(passName);
            }
        }
        return fileNames;
	}
}