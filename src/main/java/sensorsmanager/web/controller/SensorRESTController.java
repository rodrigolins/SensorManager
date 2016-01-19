package sensorsmanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sensorsmanager.business.entities.Sensor;
import sensorsmanager.business.entities.SensorType;
import sensorsmanager.business.repositories.SensorRepository;
import sensorsmanager.business.repositories.SensorTypeRepository;

@RestController
public class SensorRESTController {
	
	@Autowired
	SensorRepository sensorRepository;

    @Autowired
    SensorTypeRepository sensorTypeRepository;
	
	@RequestMapping(path="/sensor/{id}/json", method = RequestMethod.GET)
	public Sensor getSensorFromId(@PathVariable Long id, Model model) {
		Sensor sensor = sensorRepository.findOne(id);
		return sensor;
	}
	
	@RequestMapping(path="/sensor/json/all", method = RequestMethod.GET)
	public Iterable<Sensor> getAllSensors(Model model) {
		Iterable<Sensor> sensors = sensorRepository.findAll();
		return sensors;
	}

    @RequestMapping(path="/sensor/json", method = RequestMethod.GET)
	public Sensor getSensorByModelAndManufacturerAndSensorType(@RequestParam("manufacturer") String manufacturer,
                                                              @RequestParam("model") String model,
                                                              @RequestParam("sensortype") String sensortype) {

        SensorType sensorType = sensorTypeRepository.findSensorTypeByName(sensortype);
        if(sensorType == null) {
            return null;
        }

        Sensor sensor = sensorRepository.findSensorByModelAndManufacturerAndSensorType(model, manufacturer, sensorType);
        return sensor;

    }

}
