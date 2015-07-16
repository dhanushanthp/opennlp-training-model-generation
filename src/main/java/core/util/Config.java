package core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	static Properties prop = new Properties();
	static {
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTextSourcePath() {
		return prop.getProperty("text-source-path");
	}
	
	public static String getTrainDataPath() {
		return prop.getProperty("en-ner-train-path");
	}
	
	public static String getModelDataPath() {
		return prop.getProperty("en-ner-model-path");
	}
	
	public static String getNERTrainingEntity() {
		return prop.getProperty("en-ner-train-entity");
	}
	
	public static String getPureTextFile() {
		return prop.getProperty("text-file-path");
	}
}
