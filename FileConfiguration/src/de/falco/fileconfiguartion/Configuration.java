package de.falco.fileconfiguartion;

import java.util.LinkedHashMap;
import java.util.Map;

public class Configuration {
	
	private Map<String,Object> values;
	
	
	
	public Configuration() {
		values = new LinkedHashMap<>();
	}
	
	
	
	/*
	 * 
	 */
	public boolean hasKey(String key) {
		return values.containsKey(key);
	}
	
	public Object getValue(String key) {
		return values.get(key);
	}
	
	public void setValue(String key, Object value) {
		values.put(key, value);
	}
	
	
	/*
	 * 
	 */
	public boolean hasConfiguration(String key) {
		
		if(values.get(key) != null) {
			return values.get(key) instanceof Configuration;
		}else {
			return false;
		}
		
	}
	
	public Configuration getConfiguration(String key) {
		
		if(values.get(key) != null) {
			
			if(values.get(key) instanceof Configuration) {
				return (Configuration) values.get(key);
			}
			
		}
		
		return null;
		
	}
	
	public void setConfiguration(String key, Configuration con) {
		this.values.put(key, con);
	}
	
	
	
	/*
	 * 
	 */
	public boolean hasString(String key) {
		
		if(values.get(key) != null) {
			return values.get(key) instanceof String;
		}else {
			return false;
		}
		
	}
	
	public String getString(String key) {
		
		return (String) values.get(key);
		
	}
	
	public void setString(String key, String value) {
		values.put(key, value);
	}
	
	
	
	/*
	 * 
	 */
	public boolean hasInt(String key) {
		if(values.get(key) != null) {
			return values.get(key) instanceof Integer;
		}else {
			return false;
		}
	}
	
	public int getInt(String key) {
		return (int) values.get(key);
	}
	
	public void setInt(String key, int i) {
		this.values.put(key, i);
	}
	
	
	
	/*
	 * 
	 */
	public String toString(String prefix) {
		
		StringBuffer buffer = new StringBuffer();
		
		for(String key : values.keySet()) {
			
			if(values.get(key) instanceof Configuration) {
				
				Configuration c = (Configuration) values.get(key);
				
				buffer.append(prefix + key + ":\n");
				buffer.append(c.toString(prefix + FileConfiguration.seperator));
				
			}else {
				buffer.append(prefix + key + ": " + values.get(key) + "\n");
			}
			
			
			
		}
		
		return buffer.toString();
		
	}
	

}
