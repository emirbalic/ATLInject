package atlinject.utils;

public class OCLAttributeDerived {
	
	private String name;
	private String value;
	
	public OCLAttributeDerived(String name, String value) {
		this.setName(name);
		this.setValue(value);
	}
	
	public String getName() {return name;}	
	public String getValue() {return value;}	
	public void setName(String name) {this.name = name;}	
	public void setValue(String value) {this.value = value;}
	
	public String toString() {
		return this.getName() + " - " + this.getValue();
	}
}
