package sensorsmanager.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sensorsmanager.business.entities.Property;
import sensorsmanager.business.entities.Sensor;
import sensorsmanager.business.repositories.PropertyRepository;
import sensorsmanager.business.repositories.PropertyTypeRepository;
import sensorsmanager.business.repositories.SensorRepository;
import sensorsmanager.business.repositories.SensorTypeRepository;

@Controller
@RequestMapping("/sensor")
public class SensorController {
	
	@Autowired
	SensorRepository sensorRepository;
	
	@Autowired
	SensorTypeRepository sensorTypeRepository;
	
	@Autowired
	PropertyRepository propertyRepository;
	
	@Autowired
	PropertyTypeRepository propertyTypeRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String sensorsList(Model model) {
		model.addAttribute("sensors", sensorRepository.findAll());
		return "sensor/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String sensorNew(Model model) {
		model.addAttribute("sensorType", sensorTypeRepository.findAll());
		model.addAttribute("sensor", new Sensor());
		return "sensor/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String sensorNew(@Valid Sensor sensor, BindingResult bindingResult, Model model) {
		System.out.println(sensor);
		model.addAttribute("sensorType", sensorTypeRepository.findAll());
		if (bindingResult.hasErrors()) {
			return "sensor/new";	
		}
		sensorRepository.save(new Sensor(sensor.getSensorType(), sensor.getModel(), sensor.getManufacturer()));
		return "redirect:/sensor";
	}
	
	@RequestMapping("/{id}/delete")
	public String deleteSensor(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "sensor/delete";
	}
	
	@RequestMapping(value="/{id}/property/add", method=RequestMethod.GET)
	public String addPropertyToSensor(@PathVariable Long id, Model model) {
		model.addAttribute("sensor", sensorRepository.findOne(id));
		model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
		model.addAttribute("property", new Property());
		return "sensor/property/new";
	}
	
	@RequestMapping(value="/{id}/property/add", method=RequestMethod.POST)
	public String addPropertyToSensor(@Valid Property property, @PathVariable Long id, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			return "/" + id + "/property/add";
		}
		Sensor sensor = sensorRepository.findOne(id);
		System.out.println(sensor);
		System.out.println(property);
		Property newProperty = new Property(property.getPropertyType(), property.getValue(), 
				property.getUnit(),	property.getBoundary());
		propertyRepository.save(newProperty);
		System.out.println(newProperty);
		sensor.addProperty(newProperty);
		sensorRepository.save(sensor);
		/*if (sensor.getProperties() == null) {
			List<Property> properties = new ArrayList<Property>();
			properties.add(property);
			sensor.setProperties(properties);
			propertyRepository.save(property);
			sensorRepository.save(sensor);
		} else {
//			List<Property> properties = sensor.getProperties();
//			properties.add(property);
//			property.setSensor(sensor);
//			propertyRepository.save(property);
			sensor.getProperties().add(property);
			sensorRepository.save(sensor);
		}*/
		return "redirect:/sensor";
	}

	@RequestMapping("/{id}")
	public String sensorDetail(@PathVariable Long id, Model model) {
		Sensor sensor = sensorRepository.findOne(id);
		model.addAttribute("sensor", sensor);
		return "sensor/detail";
	}

}
