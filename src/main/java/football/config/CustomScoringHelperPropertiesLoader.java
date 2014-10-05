package football.config;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CustomScoringHelperPropertiesLoader {

	private static final Logger logger =
		LoggerFactory.getLogger(CustomScoringHelperPropertiesLoader.class.getName());
	private static final String overridePropertiesFilename = "/custom-scorer-override.properties";

	// singleton pattern
	private static CustomScoringHelperPropertiesLoader _loader = null;

	private Properties props;

	private CustomScoringHelperPropertiesLoader(String propertiesFilename) {
		props = new Properties();
		try {
			logger.info("reading properties from file {}", propertiesFilename);
			InputStream in = this.getClass().getResourceAsStream(propertiesFilename);
			if(in != null) {
				props.load(in);
				in.close();
				logger.info("properties loaded successfuly");
			} else {
				//TODO: throw FileNotFoundException here?
				logger.warn("cannot find file {}. Using default property values intead", propertiesFilename);
			}
		} catch(IOException e) {
			logger.error("exception parsing properties file {}:\n{}", propertiesFilename, e.toString());
		}
	}

	public static CustomScoringHelperPropertiesLoader getInstance(String propertiesFilename) {
		if(_loader == null) {
			_loader = new CustomScoringHelperPropertiesLoader(propertiesFilename);
		}
		return _loader;
	}

	public static CustomScoringHelperPropertiesLoader getInstance() {
		return getInstance(overridePropertiesFilename);
	}

	public String getProperty(String propertyName) {
		String value = props.getProperty(propertyName);
		if(value == null) {
			throw new IllegalArgumentException("property " + propertyName + " not found");
		}
		return value;
	}
}
