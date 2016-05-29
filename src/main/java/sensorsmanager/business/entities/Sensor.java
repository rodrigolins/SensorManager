package sensorsmanager.business.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Sensor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private SensorType sensorType;
	
	@NotBlank
	@Size(min=3, max=50)
	private String model;
	
	@NotBlank
	@Size(min=3, max=50)
	private String manufacturer;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> properties;

    @OneToMany(cascade = CascadeType.ALL)
	private List<Rule> rules;

	public Sensor() {}

    public Sensor(SensorType sensorType, String model, String manufacturer, List<Property> properties,
                  List<Rule> rules) {
        super();
        this.sensorType = sensorType;
        this.model = model;
        this.manufacturer = manufacturer;
        this.properties = properties;
        this.rules = rules;
    }

	public Sensor(SensorType sensorType, String model, String manufacturer, List<Property> properties) {
		super();
		this.sensorType = sensorType;
		this.model = model;
		this.manufacturer = manufacturer;
		this.properties = properties;
	}
	
	public Sensor(SensorType sensorType, String model, String manufacturer) {
		super();
		this.sensorType = sensorType;
		this.model = model;
		this.manufacturer = manufacturer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void addProperty(Property property) {
		if(this.properties == null) {
			this.properties = new ArrayList<Property>();
			this.properties.add(property);
		} else {
			this.properties.add(property);
		}
	}

    public void addRule(Rule rule) {
        if(this.rules == null) {
            this.rules = new ArrayList<Rule>();
            this.rules.add(rule);
        } else {
            this.rules.add(rule);
        }
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", sensorType=" + sensorType +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", properties=" + properties +
                ", rule=" + rules +
                '}';
    }
}
