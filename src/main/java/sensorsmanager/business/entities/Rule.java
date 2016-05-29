package sensorsmanager.business.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rule {

    public enum Boundary {
        IC("Increase"), DC("Decrease"), DE("Delta");

        private String name;

        private Boundary(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PropertyType propertyType;

    @NotNull
    private Float value;

    @NotBlank
    private String unit;

    @NotNull
    private Long time;

    private Boundary boundary;

    public Rule() {

    }

    public Rule(PropertyType propertyType, Float value, String unit, Long time, Boundary boundary) {
        this.propertyType = propertyType;
        this.value = value;
        this.unit = unit;
        this.time = time;
        this.boundary = boundary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    @Override
    public String toString() {
        return "TimedProperty{" +
                "id=" + id +
                ", propertyType=" + propertyType +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", time=" + time +
                ", boundary=" + boundary +
                '}';
    }
}
