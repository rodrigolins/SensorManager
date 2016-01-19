package sensorsmanager.business.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
	private List<TimedProperty> timedProperties;

	public Sensor() {}

    public Sensor(SensorType sensorType, String model, String manufacturer, List<Property> properties,
                  List<TimedProperty> timedProperties) {
        super();
        this.sensorType = sensorType;
        this.model = model;
        this.manufacturer = manufacturer;
        this.properties = properties;
        this.timedProperties = timedProperties;
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

    public List<TimedProperty> getTimedProperties() {
        return timedProperties;
    }

    public void setTimedProperties(List<TimedProperty> timedProperties) {
        this.timedProperties = timedProperties;
    }

    public void addProperty(Property property) {
		if(this.properties == null) {
			this.properties = new ArrayList<Property>();
			this.properties.add(property);
		} else {
			this.properties.add(property);
		}
	}

    public void addTimedProperty(TimedProperty timedProperty) {
        if(this.timedProperties == null) {
            this.timedProperties = new ArrayList<TimedProperty>();
            this.timedProperties.add(timedProperty);
        } else {
            this.timedProperties.add(timedProperty);
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
                ", timedProperties=" + timedProperties +
                '}';
    }
}
