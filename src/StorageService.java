import java.util.*;
import java.io.*;

public interface StorageService {

	public abstract void add(Password password) throws IOException, StorageException;

	public abstract Password get(String passName) throws IOException, EmptyStringException, StorageException;

	public abstract void update(Password password) throws IOException, StorageException;

	public abstract void remove(String passName) throws StorageException;

	public abstract List<String> getNames();
}