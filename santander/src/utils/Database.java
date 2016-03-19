package netizens.bank.utils;

import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Database.java
 *
 * Stores data in the form "key:value"
 * The data is written to a file on close, and read from the file on open
 * In essence, this is a fancy wrapper of the HashMap class,
 * making it simple to read and write to a file
 * <br/>
 * Example usage:
 * <pre>
 * 
 * // Bank.java
 * Database.initialise(mainJSON);
 * 
 * // Any method in the program
 * Database<String, String> database = new Database();
 * database.open();
 * ...
 * database.put("index 1", "random string to be stored");
 * ...
 * String index1 = database.get("index 1");
 * ...
 * // End of method
 * database.close();
 * </pre>
 * 
 * @param <K> The type of key to use (must extend Serializable)
 * @param <V> The type of value to use (must extend Serializable)
 **/
public class Database<K extends Serializable, V extends Serializable> {
	
	private static String dbPath;

	/**
	 * gets the path given in database.json and stores
	 * it on the Database class
	 *
	 * @param mainJSON The main json tokener
	 **/
	public static void initialise(JSONTokener mainJSON){
		// main.json
		JSONObject mainObj = (new JSONObject(mainJSON)).getJSONObject("main");
		JSONObject settingsObj = mainObj.getJSONObject("settings");
		String databasePath = settingsObj.getString("database");

		// database.json
		JSONObject databaseObj = (new JSONObject(JSON.getJSONTokener(databasePath))).getJSONObject("database");

		dbPath = databaseObj.getString("path");

		Debug.println("Database -> " + dbPath);
	}

	private HashMap<K, V> database;

	/**
	 * Creates a new database object,
	 * loading and saving everything to the database file
	 * given in database.json
	 *
	 * open() must be called first to load the database
	 * close() must be called last to save the database
	 * 
	 **/
	public Database(){
		database = new HashMap<>();
	}

	/**
	 * Puts the value in the database at location given by the key
	 * If there is already a value in the location, it is overwritten
	 * 
	 * @param key The location to put the value
	 * @param value The object to store in the database
	 **/
	public void put(K key, V value){
		database.put(key, value);
	}

	/**
	 * Gets the object at the given location
	 * Returns null if there's nothing at that location
	 * 
	 * @param key the location of the object to return
	 * 
	 * @return the object at the given location, null if there's nothing there/there's an error casting
	 **/
	public V get(K key){
		try{
			return database.get(key);
		}catch(ClassCastException e){
			System.err.println("Type stored in database is different from the type specified");
			return null;
		}
	}

	/**
	 * Load the database from the database file given in "database.json"
	 * if the file doesn't exist, it gets created here
	 * if the file has a database in it already, it is loaded here
	 * 
	 * @return false if there is an error opening the database
	 **/
	public boolean open(){
		try{
			System.out.println(dbPath);
			File dbFile = new File(dbPath);

			// Only read the database from the file if it exists
			if(dbFile.exists()){
				FileInputStream fileIn = new FileInputStream(dbPath);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				// load the database here
				database = (HashMap<K, V>) in.readObject();
				// make sure the streams close properly
				in.close();
				fileIn.close();

				Debug.println("Database loaded");
			}else{
				dbFile.getParentFile().mkdirs(); 
				dbFile.createNewFile();

				Debug.println("Database created");
			}
			return true;
		}catch(Exception e){
			System.err.println("Error opening database");
			System.err.println(e);
			return false;
		}
	}

	/**
	 * Save the database to the database file given in "database.json"
	 * 
	 * @return false if there is an error closing the database
	 **/
	public boolean close(){
		try{
			FileOutputStream fileOut = new FileOutputStream(dbPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			// write the database before we close
			out.writeObject(database);
			// make sure the streams close correctly
			out.close();
			fileOut.close();

			return true;
		}catch(Exception e){
			System.err.println("Error closing database");
			System.err.println(e);
			return false;
		}
	}
}