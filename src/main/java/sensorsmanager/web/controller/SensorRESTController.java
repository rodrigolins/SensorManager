package sensorsmanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sensorsmanager.business.entities.Sensor;
import sensorsmanager.business.repositories.SensorRepository;

@RestController
public class SensorRESTController {
	
	@Autowired
	SensorRepository sensorRepository;
	
	@RequestMapping(path="/sensor/{id}/json", method=RequestMethod.GET)
	public Sensor sensorGetJson(@PathVariable Long id, Model model) {
		Sensor sensor = sensorRepository.findOne(id);
		return sensor;
		
	}
	
	@RequestMapping(path="/sensor/json", method=RequestMethod.GET)
	public Iterable<Sensor> sensorListJson(Model model) {
		Iterable<Sensor> sensors = sensorRepository.findAll();
		
		return sensors;
	}

}
