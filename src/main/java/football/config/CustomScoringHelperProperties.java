package football.config;

public final class CustomScoringHelperProperties {

	// properties
	private static boolean useCompositeModel = true;

	// property keys
	private static final String USE_COMPOSITE_MODEL = "useCompositeModel";

	private CustomScoringHelperProperties() {}

	public static void init() {
		CustomScoringHelperPropertiesLoader loader = CustomScoringHelperPropertiesLoader.getInstance();

		// set properties by getting properties from loader
		setUseCompositeModel(Boolean.parseBoolean(loader.getProperty(USE_COMPOSITE_MODEL)));
	}

	/*
	* Getters
	*/
	public static boolean useCompositeModel() {
		return useCompositeModel;
	}

	/*
	* Setters
	*/
	public static void setUseCompositeModel(boolean createCompositeModel) {
		useCompositeModel = createCompositeModel;
	}
}
