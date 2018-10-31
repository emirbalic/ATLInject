package atlinject.config;

public class WeightConfig {
	
	private static WeightConfig instance;
	
	static final Double ClassWeigth = 1.0;		//Class weight used in coverage computation
	static final Double AttributesWeigth = 0.5;	//Attribute weight used in coverage computation
	static final Double ReferencesWeight = 0.5;	//References weight  used in coverage computation
	
	private WeightConfig() {};
	
	public static WeightConfig getInstance() {
		if(instance == null)
			instance = new WeightConfig();
		return instance;
	}
	
	public Double getClassweigth() {return ClassWeigth;}
	public Double getAttributesweigth() {return AttributesWeigth;}
	public Double getReferencesweight() {return ReferencesWeight;}
	
}
