package sensorsmanager.business.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class SensorType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Column(unique = true)
	@Size(min=3, max=35)
	private String name;
	
	public SensorType() {}

	public SensorType(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SensorType [id=" + id + ", name=" + name + "]";
	};
}
