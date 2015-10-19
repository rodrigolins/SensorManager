package sensorsmanager.business.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Property {
	
	private enum Boundary {
		EQ("Equals"), GT("Greater than"), LT("Lower than"), DV("Standard deviation");
		
		private String name;
		
		private Boundary(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private PropertyType propertyType;
	private Float value;
	private String unit;
	private Boundary boundary;
	
	public Property() {}
	
	public Property(PropertyType propertyType, Float value, String unit,
			Boundary boundary) {
		super();
		this.propertyType = propertyType;
		this.value = value;
		this.unit = unit;
		this.boundary = boundary;
	}
	
	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boundary getBoundary() {
		return boundary;
	}

	public void setBoundary(Boundary boundary) {
		this.boundary = boundary;
	}
	
	@Override
	public String toString() {
		return "Property [id=" + id + ", propertyType=" + propertyType
				+ ", value=" + value + ", unit=" + unit + ", boundary="
				+ boundary + "]";
	}
	
}
