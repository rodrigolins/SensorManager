package sensorsmanager.business.entities;

public class Property {
	
	private enum Boundary {
		EQ("Equals"), GT("Greater than"), LT("Lower than"), DV("Standard deviation");
		
		private String name;
		
		private Boundary(String name){
			this.name = name;
		}
	}
	
	private PropertyType propertyType;
	private Float value;
	private String unit;
	private Boundary boundary;
	private Sensor sensor;
	

}
