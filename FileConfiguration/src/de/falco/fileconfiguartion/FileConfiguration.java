package de.falco.fileconfiguartion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileConfiguration {
	
	private Configuration config;
	
	public static String seperator = "   ";
	
	
	
	public FileConfiguration() {
		this.config = new Configuration();
	}
	
	
	
	public void load(File file) throws IOException {
		
		if(file.exists()) {
			
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				
				String line = null;
				
				ArrayList<String> lines = new ArrayList<>();
				
				while((line = reader.readLine()) != null) {
					lines.add(line);
				}
				
				config = generateConfiguration(lines);
				
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void save(File file) throws IOException {
		
		//message used for printing returns perfect String for saving
		String txt = config.toString("");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		PrintWriter w = new PrintWriter(file);
		w.write(txt);
		w.flush();
		
		w.close();
		
	}
	
	
	
	/*
	 * util methods
	 */
	
	/**
	 * generate Configuration based on lines
	 * recursion is used intern
	 * used in save(File file)
	 * 
	 * @param line
	 * @return
	 */
	private Configuration generateConfiguration(ArrayList<String> line) {

		Configuration con = new Configuration();

		int blockindex = 0;

		for (int i = 0; i < line.size(); i++) {

			if (i < blockindex) {
				//System.out.println("blocking line " + line.get(i));
				continue;
			}else {
				//System.out.println("processing line " + line.get(i));
			}
			
			String l = line.get(i);
			
			if(i == line.size()-1) {//last line
				String key = l.split("\\:")[0];
				String value = l.split("\\:")[1].replaceFirst(" ", "");
				
				Object ob = value;
				
				try {
					ob = Integer.parseInt(value);
				}catch(NumberFormatException ex) {
				}
				
				//System.out.println("set key: " + key + " value: " + value);
				
				con.setValue(key, ob);
				continue;
			}
			
			

			if (!line.get(i + 1).startsWith(seperator)) {// value

				String key = l.split("\\:")[0];
				String value = l.split("\\:")[1].replaceFirst(" ", "");

				Object ob = value;
				
				try {
					ob = Integer.parseInt(value);
				}catch(NumberFormatException ex) {
				}
				
				//System.out.println("set key: " + key + " value: " + value);
				
				con.setValue(key, ob);

			} else {// configuration

				String key = l.replaceAll(":", "");

				ArrayList<String> l2 = new ArrayList<>();

				for (int x = i+1; x < line.size(); x++) {

					if (line.get(x).startsWith(seperator)) {
						l2.add(line.get(x).replaceFirst(seperator, ""));
					} else {
						System.out.println("set index to " + x);
						blockindex = x;
						break;
					}

				}
				
				blockindex = line.size();
				
				//System.out.println("generateConfiguration with key " + key + " lines: " + l2);
				con.setValue(key, generateConfiguration(l2));

			}

		}

		return con;
		
	}
	
	/**
	 * search object with path in Configuraion
	 * recursion is used intern
	 * used in getValue, getString, getConfiguration, getInt
	 * 
	 * @param path
	 * @param c
	 * @return null if not found
	 */
	private Object getValue(ArrayList<String> path, Configuration c) {
		
		String key = path.get(0);
		
		if(path.size() == 1) {
			
			return c.getValue(key);
			
		}
		
		if(c.hasConfiguration(key)) {
			
			path.remove(0);
			
			return getValue(path, c.getConfiguration(key));
			
		}else {
			
			return null;
			
		}
		
	}
	
	/**
	 * convert path to list
	 * 
	 * @param path
	 * @return
	 */
	private ArrayList<String> transformPath(String path) {
		
		String[] v = path.split("\\.");
		
		ArrayList<String> t = new ArrayList<>();
		
		for(int x = 0; x < v.length; x++) {
			t.add(v[x]);
		}
		
		return t;
		
	}
	
	/**
	 * add Object with path to Configuration
	 * recursion is used intern
	 * used in set, setString, setConfiguration, setInt
	 * 
	 * @param path
	 * @param ob
	 * @param c
	 */
	private void setValue(ArrayList<String> path, Object ob, Configuration c) {
		
		String key = path.get(0);
		
		if(path.size() == 1) {
			
			c.setValue(key, ob);
			
		}else {
			
			path.remove(0);
			
			if(c.hasConfiguration(key)) {
				
				setValue(path, ob, c.getConfiguration(key));
				
			}else {
				
				Configuration tmpconfig = new Configuration();
				
				c.setValue(key, tmpconfig);
				
				setValue(path, ob, tmpconfig);
				
			}
			
		}
		
	}
	
	
	/*
	 * handle field values
	 */
	public void set(String path, Object value) {
		
		setValue(transformPath(path), value, this.config);
		
	}
	
	public Object getValue(String path) {
		
		return getValue(transformPath(path), config);
		
	}
	
	public boolean hasValue(String path) {
		
		ArrayList<String> p = transformPath(path);
		
		Object ob = getValue(p,this.config);
		
		return ob != null;
		
	}
	
	
	/*
	 * handle field strings
	 */
	public void setString(String path, String value) {
		
		setValue(transformPath(path), value, this.config);
		
	}
	
	public boolean hasString(String path) {
		
		ArrayList<String> p = transformPath(path);
		
		Object value = getValue(p,this.config);
		
		if(value != null) {
			
			if(value instanceof String) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public String getString(String path) {
		
		Object value = getValue(transformPath(path), this.config);
		
		if(value != null) {
			
			if(value instanceof String) {
				return (String) value;
			}
			
		}
		
		return "";
		
	}
	
	
	/*
	 * handle field configurations
	 */
	public void setConfiguration(String path, Configuration con) {
		
		setValue(transformPath(path), con, config);
		
	}
	
	public boolean hasConfiguration(String path) {
		
		ArrayList<String> p = transformPath(path);
		
		Object value = getValue(p,this.config);
		
		if(value != null) {
			
			if(value instanceof Configuration) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public Configuration getConfiguration(String path) {
		
		Object value = getValue(transformPath(path), this.config);
		
		if(value != null) {
			
			if(value instanceof Configuration) {
				return (Configuration) value;
			}
			
		}
		
		return new Configuration();
		
	}
	
	
	/*
	 * handle field ints
	 */
	public void setInt(String path, int number) {
		setValue(transformPath(path), number, config);
	}
	
	public boolean hasInt(String path) {
		
		ArrayList<String> p = transformPath(path);
		
		Object value = getValue(p,this.config);
		
		if(value != null) {
			
			if(value instanceof Integer) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public int getInt(String path) {
		
		Object value = getValue(transformPath(path), this.config);
		
		if(value != null) {
			
			if(value instanceof Integer) {
				return (int) value;
			}
			
		}
		
		return 0;
		
	}
	
	
	/*
	 * print tree structur 
	 * + used to save data in file
	 */
	public String toString() {
		return config.toString("");
	}
	
	

}
