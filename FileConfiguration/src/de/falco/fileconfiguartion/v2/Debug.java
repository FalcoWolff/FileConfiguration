package de.falco.fileconfiguartion.v2;

import java.io.File;
import java.io.IOException;

public class Debug {

	public static void main(String[] args) throws IOException {
		
		
		FileConfiguration config = new FileConfiguration();
		
		
		
		System.out.println("{load config}");
		
		config.load(new File("config.yml"));
		
		System.out.println("has messages.kill.count: " + config.hasInt("messages.kill.count"));
		
		System.out.println(config);
		
		System.out.println("---");
		
		
		
		System.out.println("{edit config}");
		
		config.setString("messages.heal", "du wurdest geheilt");
		
		config.setString("messages.kill.message", "du wurdest gekillt");
		
		config.setInt("messages.kill.count2", 4);
		
		
		System.out.println(config);
		
		String heal = (String) config.getValue("messages.heal");
		System.out.println(heal);
		
		System.out.println("has messages.kill.messages: " + config.hasString("messages.kill.message"));
		System.out.println("has messages: " + config.hasConfiguration("messages"));
		
		System.out.println("---");
		
		
		
		System.out.println("{save config}");
		
		config.save(new File("config.yml"));
		
		System.out.println("---");
		
		

	}

}
