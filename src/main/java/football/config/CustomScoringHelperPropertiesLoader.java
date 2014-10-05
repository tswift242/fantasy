package football.config;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
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

	private CustomScoringHelperPropertiesLoader(String propertiesFilename) throws FileNotFoundException, IOException {
		props = new Properties();
		logger.info("reading properties from file {}", propertiesFilename);
		InputStream in = this.getClass().getResourceAsStream(propertiesFilename);
		if(in != null) {
			try {
				props.load(in);
				in.close();
				logger.info("properties loaded successfuly");
			} catch(IOException e) {
				throw new IOException("Error while parsing properties file " + propertiesFilename, e);
			}
		} else {
			throw new FileNotFoundException("Cannot find file: " + propertiesFilename);
		}
	}

	public static CustomScoringHelperPropertiesLoader getInstance(String propertiesFilename) throws FileNotFoundException, IOException {
		if(_loader == null) {
			_loader = new CustomScoringHelperPropertiesLoader(propertiesFilename);
		}
		return _loader;
	}

	public static CustomScoringHelperPropertiesLoader getInstance() throws FileNotFoundException, IOException {
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
