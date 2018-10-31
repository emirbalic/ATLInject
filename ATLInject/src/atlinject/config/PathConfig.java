package atlinject.config;

public class PathConfig {
	
	private static PathConfig instance;
	
	static final String rootFolder = "Resources";					//Resource root folder path
	static final String inputMetamodelFolder = "metamodels/IN";		//Input MetaModels folder
	static final String outputMetamodelFolder = "metamodels/OUT";	//Output MetaModels folder
	static final String inputModelFolder = "models/IN";				//Input Models folder
	static final String outputModelFolder = "models/OUT";			//Output Models folder
	static final String transformationFolder = "trafo";				//Transformations folder
	
	private PathConfig() {};
	
	public static PathConfig getInstance() {
		if(instance == null)
			instance = new PathConfig();
		return instance;
	}
	
	public String getRootFolder() {return rootFolder;}
	public String getInputMetamodelFolder() {return inputMetamodelFolder;}
	public String getInputModelFolder() {return inputModelFolder;}
	public String getTransformationFolder() {return transformationFolder;}
	public String getOutputMetamodelFolder() {return outputMetamodelFolder;}
	public String getOutputModelFolder() {return outputModelFolder;}
	
}
