package sensorsmanager.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sensorsmanager.business.entities.Property;
import sensorsmanager.business.entities.PropertyType;
import sensorsmanager.business.entities.Sensor;
import sensorsmanager.business.entities.SensorType;
import sensorsmanager.business.repositories.PropertyRepository;
import sensorsmanager.business.repositories.PropertyTypeRepository;
import sensorsmanager.business.repositories.SensorRepository;
import sensorsmanager.business.repositories.SensorTypeRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BootstrapService {

    @Autowired
    PropertyTypeRepository propertyTypeRepository;

    @Autowired
    SensorTypeRepository sensorTypeRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    SensorRepository sensorRepository;


    private static BootstrapService instance = null;

    protected BootstrapService() {
        super();
    }

    public static BootstrapService getInstance() {
        if(instance == null) {
            instance = new BootstrapService();
        }

        return instance;
    }

    @PostConstruct
    private void populate() {

        final String PROPERTY_TYPE_TEMPERATURE = "Temperature";
        final String PROPERTY_TYPE_HUMIDITY = "Humidity";
        final String PROPERTY_TYPE_HEAT_INDEX = "Heat index";
        final String PROPERTY_TYPE_X_AXIS = "X axis";
        final String PROPERTY_TYPE_Y_AXIS = "Y axis";
        final String PROPERTY_TYPE_Z_AXIS = "Z axis";
        final String PROPERTY_TYPE_STATE = "State";
        final String PROPERTY_TYPE_DISTANCE = "Distance";

        final String SENSOR_TYPE_THERMOMETER = "Thermometer";
        final String SENSOR_TYPE_HYGROMETER = "Hygrometer";
        final String SENSOR_TYPE_ULTRASONIC = "Ultrasonic";
        final String SENSOR_TYPE_PUSH_BUTTON = "Push button";
        final String SENSOR_TYPE_TRIAXIAL_ACCELEROMETER = "Triaxial accelerometer";
        final String SENSOR_TYPE_PASSIVE_INFRARED = "Passive infrared";
        final String SENSOR_TYPE_PRESSURE = "Pressure";
        final String SENSOR_TYPE_GAS = "Gas";

        System.out.println("BootstrapService.initialize");
        System.out.println("Initializing database...");
        System.out.println("Properties in propertyTypeRespository: " + propertyTypeRepository.count());

        System.out.println("Creating property types");
        createPropertyType(PROPERTY_TYPE_TEMPERATURE);
        createPropertyType(PROPERTY_TYPE_HUMIDITY);
        createPropertyType(PROPERTY_TYPE_HEAT_INDEX);
        createPropertyType(PROPERTY_TYPE_X_AXIS);
        createPropertyType(PROPERTY_TYPE_Y_AXIS);
        createPropertyType(PROPERTY_TYPE_Z_AXIS);
        createPropertyType(PROPERTY_TYPE_STATE); // Push button as well
        createPropertyType(PROPERTY_TYPE_DISTANCE);

        System.out.println("Creating sensor types");
        createSensorType(SENSOR_TYPE_THERMOMETER);
        createSensorType(SENSOR_TYPE_HYGROMETER);
        createSensorType(SENSOR_TYPE_ULTRASONIC);
        createSensorType(SENSOR_TYPE_PUSH_BUTTON);
        createSensorType(SENSOR_TYPE_TRIAXIAL_ACCELEROMETER);
        createSensorType(SENSOR_TYPE_PASSIVE_INFRARED);
        createSensorType(SENSOR_TYPE_PRESSURE);
        createSensorType(SENSOR_TYPE_GAS);

        System.out.println("Creating sensors and its properties");
        List<Property> properties = new ArrayList<>();

        // Temperature - 1
        if(sensorRepository.findSensorByModelAndManufacturerAndSensorType("LM35", "Generic",
                sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_THERMOMETER)) == null) {
            System.out.println("Sensor (Thermometer) LM35 does not exists");
            PropertyType propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_TEMPERATURE);
            properties.add(new Property(propertyType, -30f, "C", Property.Boundary.GT));
            properties.add(new Property(propertyType, 120f, "C", Property.Boundary.LT));
            properties.add(new Property(propertyType, 3f, "C", Property.Boundary.DV));
            createSensor("LM35", "Generic", sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_THERMOMETER), properties);
        }

        // Push button - 2
        if(sensorRepository.findSensorByModelAndManufacturerAndSensorType("Generic", "Generic",
                sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_PUSH_BUTTON)) == null) {
            properties = new ArrayList<>();
            System.out.println("Sensor (Push button) GENERIC does not exists");
            PropertyType propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_STATE);
            properties.add(new Property(propertyType, 0f, "boolean", Property.Boundary.EQ));
            createSensor("Generic", "Generic", sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_PUSH_BUTTON), properties);
        }

        // Hygrometer
        if(sensorRepository.findSensorByModelAndManufacturerAndSensorType("DHT22", "Generic",
                sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_HYGROMETER)) == null) {
            properties = new ArrayList<>();
            System.out.println("Sensor (Hygrometer) GENERIC does not exists");
            PropertyType propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_TEMPERATURE);
            properties.add(new Property(propertyType, -30f, "C", Property.Boundary.GT));
            properties.add(new Property(propertyType, 120f, "C", Property.Boundary.LT));
            properties.add(new Property(propertyType, 3f, "C", Property.Boundary.DV));
            propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_HUMIDITY);
            properties.add(new Property(propertyType, 0f, "%", Property.Boundary.GT));
            properties.add(new Property(propertyType, 100f, "%", Property.Boundary.LT));
            properties.add(new Property(propertyType, 3f, "%", Property.Boundary.DV));
            propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_HEAT_INDEX);
            properties.add(new Property(propertyType, -40f, "C", Property.Boundary.GT));
            properties.add(new Property(propertyType, 130f, "C", Property.Boundary.LT));
            properties.add(new Property(propertyType, 5f, "C", Property.Boundary.DV));
            createSensor("DHT22", "Generic", sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_HYGROMETER), properties);
        }

        // Triaxial accelerometer
        if(sensorRepository.findSensorByModelAndManufacturerAndSensorType("MMA7361", "Geeetech",
                sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_TRIAXIAL_ACCELEROMETER)) == null) {
            properties = new ArrayList<>();
            System.out.println("Sensor (Triaxial Accelerometer) MMA7361 does not exists");
            PropertyType propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_X_AXIS);
            properties.add(new Property(propertyType, 0f, "g", Property.Boundary.GT));
            properties.add(new Property(propertyType, 5f, "g", Property.Boundary.LT));
            properties.add(new Property(propertyType, 0.5f, "g", Property.Boundary.DV));
            propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_Y_AXIS);
            properties.add(new Property(propertyType, 0f, "g", Property.Boundary.GT));
            properties.add(new Property(propertyType, 5f, "g", Property.Boundary.LT));
            properties.add(new Property(propertyType, 0.5f, "g", Property.Boundary.DV));
            propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_Z_AXIS);
            properties.add(new Property(propertyType, 0f, "g", Property.Boundary.GT));
            properties.add(new Property(propertyType, 5f, "g", Property.Boundary.LT));
            properties.add(new Property(propertyType, 0.5f, "g", Property.Boundary.DV));
            createSensor("MMA7361", "Geeetech", sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_TRIAXIAL_ACCELEROMETER), properties);
        }

        // PIR
        if(sensorRepository.findSensorByModelAndManufacturerAndSensorType("RE200B", "Generic",
                sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_PASSIVE_INFRARED)) == null) {
            properties = new ArrayList<>();
            System.out.println("Sensor (Passive Infrared) RE200B does not exists");
            createSensor("RE200B", "Generic", sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_PASSIVE_INFRARED), properties);
        }

        // Ultrasonic
        if(sensorRepository.findSensorByModelAndManufacturerAndSensorType("HC-SR04", "Generic",
                sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_ULTRASONIC)) == null) {
            System.out.println("Sensor (Ultrasonic) HC-SR04 does not exists");
            PropertyType propertyType = propertyTypeRepository.findPropertyTypeByName(PROPERTY_TYPE_DISTANCE);
            properties.add(new Property(propertyType, 3f, "cm", Property.Boundary.GT));
            properties.add(new Property(propertyType, 300f, "cm", Property.Boundary.LT));
            properties.add(new Property(propertyType, 4f, "cm", Property.Boundary.DV));
            createSensor("HC-SR04", "Generic", sensorTypeRepository.findSensorTypeByName(SENSOR_TYPE_ULTRASONIC), properties);
        }

    }

    private void createPropertyType(String propertyTypeName) {
        if(propertyTypeRepository.findPropertyTypeByName(propertyTypeName) == null) {
            PropertyType propertyType = new PropertyType(propertyTypeName);
            propertyTypeRepository.save(propertyType);
        }
    }

    private void createSensorType(String sensorTypeName) {
        if(sensorTypeRepository.findSensorTypeByName(sensorTypeName) == null) {
            SensorType sensorType = new SensorType(sensorTypeName);
            sensorTypeRepository.save(sensorType);
        }
    }

    private void createSensor(String sensorModel, String sensorManufacturer, SensorType sensorType, List<Property> properties) {
        Sensor sensor = new Sensor(sensorType, sensorModel, sensorManufacturer, properties);
        sensorRepository.save(sensor);
    }

}