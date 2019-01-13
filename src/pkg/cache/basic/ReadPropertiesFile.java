package pkg.cache.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadPropertiesFile {
	public static Map<String, String> property() {
		Map<String,String> propertyMap = new HashMap<>();
		try {
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "\\resources\\test.properties";
			File file = new File(appConfigPath);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				propertyMap.put(key,properties.getProperty(key));
				System.out.println(key + ": " + propertyMap.get(key));				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertyMap;
	}
	public static void main(String[] args) {
		property();
	}
}

