package sensorsmanager.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        System.out.println("GET - sensorsList");
		model.addAttribute("sensors", sensorRepository.findAll());
		return "sensor/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String sensorCreate(Model model) {
        System.out.println("GET - sensorNew");
		model.addAttribute("sensorType", sensorTypeRepository.findAll());
		model.addAttribute("sensor", new Sensor());
		return "sensor/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String sensorCreate(@Valid Sensor sensor, BindingResult bindingResult, Model model,
							final RedirectAttributes redirectAttributes) {
        System.out.println("POST - sensorNew");
		System.out.println(sensor);
		model.addAttribute("sensorType", sensorTypeRepository.findAll());
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
//            redirectAttributes.addFlashAttribute("errorMessage", "Fail to create a new sensor");
			model.addAttribute("errorMessage", "Fail to create a new sensor. Check error messages.");
			return "sensor/new";
		}
		sensorRepository.save(new Sensor(sensor.getSensorType(), sensor.getModel(), sensor.getManufacturer()));
        redirectAttributes.addFlashAttribute("successMessage", "Sensor created successfully");
		return "redirect:/sensor";
	}

    @RequestMapping("/{id}")
    public String sensorDetail(@PathVariable Long id, Model model) {
        System.out.println("GET - sensorDetail");
        Sensor sensor = sensorRepository.findOne(id);
        model.addAttribute("sensor", sensor);
        return "sensor/detail";
    }

    @RequestMapping(value="/{id}/update", method=RequestMethod.GET)
    public String sensorUpdate(@PathVariable Long id, Model model) {
        System.out.println("GET - sensorUpdate");
        Sensor sensor = sensorRepository.findOne(id);
        model.addAttribute("sensor", sensor);
        model.addAttribute("sensorType", sensorTypeRepository.findAll());
        return "sensor/update";
    }

    // FOLLOW ALWAYS THIS ORDER: PathVariable, ModelAttribute, BingResult, Model
    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String sensorUpdate(@PathVariable Long id, @Valid @ModelAttribute("sensor") Sensor sensor,
                               BindingResult bindingResult, Model model) {
        System.out.println("POST - sensorUpdate");
        System.out.println("Before update: " + sensor);
        model.addAttribute("sensorType", sensorTypeRepository.findAll());

        if (bindingResult.hasErrors()) {
            System.out.println("Error occured when updating sensor.");
            System.out.println(bindingResult);

            return "sensor/update";
        }

        System.out.println("Sensor updated successfully!");
        sensorRepository.save(sensor);
        return "redirect:/sensor";
    }

	@RequestMapping(value="/{id}/delete", method = RequestMethod.GET)
	public String sensorDelete(@PathVariable Long id, Model model) {
        System.out.println("GET - deleteSensor");
		model.addAttribute("id", id);
		return "sensor/delete";
	}
	
	@RequestMapping(value="/{id}/property/{propertyid}/delete", method=RequestMethod.GET)
	public String removePropertyFromSensor(@PathVariable Long id, @PathVariable Long propertyid, Model model) {
        System.out.println("GET - removePropertyFromSensor");
		Sensor sensor = sensorRepository.findOne(id);
		/*if(sensor == null) {
			model.addAttribute("errorMessage", "Sensor doesn't exist! Please check the sensor id and try again.");
			return "redirect:/sensor";
		}*/
		/*if(sensor.getProperties() == null || sensor.getProperties().size() == 0) {
			model.addAttribute("errorMessage", "No properties to remove");
			return "redirect:/sensor";
		}*/
		List<Property> properties = sensor.getProperties();
		for(Property property : properties) {
			if(property.getId() == propertyid) {
				sensor.getProperties().remove(property);
				sensorRepository.save(sensor);
				propertyRepository.delete(property);
				model.addAttribute("sensor", sensorRepository.findOne(id));
				model.addAttribute("sensorType", sensorTypeRepository.findAll());
				model.addAttribute("successMessage", "Property deleted successfully");
				return "sensor/update";
			}
		}
		model.addAttribute("errorMessage", "Going out directly");
		return "sensor/list";
	}

	// DO not remove Property from method signature
	@RequestMapping(value="/{id}/property/add", method=RequestMethod.GET)
	public String addPropertyToSensor(@PathVariable Long id, Model model, Property property) {
        System.out.println("GET - addPropertyToSensor");
		model.addAttribute("sensor", sensorRepository.findOne(id));
		model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
		return "sensor/property/new";
	}

    @RequestMapping(value="/{id}/property/add", method=RequestMethod.POST)
    public String addPropertyToSensor(@PathVariable Long id, @Valid Property property, BindingResult bindingResult,
                                      Model model, final RedirectAttributes redirectAttributes) {

        System.out.println("POST - addPropertyToSensor");
        System.out.println("property = " + property);

        Sensor sensor = sensorRepository.findOne(id);
        model.addAttribute("sensor", sensor);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
            model.addAttribute("errorMessage", "Fail to add a new property to a sensor. Check error messages.");
            return "sensor/property/new";
        }
        Property newProperty = new Property(property.getPropertyType(), property.getValue(),
                property.getUnit(),	property.getBoundary());

        System.out.println("newProperty = " + newProperty);
        propertyRepository.save(newProperty);

        System.out.println("newProperty = " + newProperty);

        sensor.addProperty(newProperty);
        sensorRepository.save(sensor);
        return "redirect:/sensor";
    }

	
	@RequestMapping(value="/{id}/property/{propertyid}", method=RequestMethod.GET)
	public String sensorPropertyDetail(@PathVariable Long id, @PathVariable Long propertyid, Model model) {
        System.out.println("GET - sensorPropertyDetail");
		Sensor sensor = sensorRepository.findOne(id);
		model.addAttribute("sensor", sensor);
		Property property = propertyRepository.findOne(propertyid);
		model.addAttribute("property", property);
		model.addAttribute("propertyType", propertyTypeRepository.findAll());
		return "sensor/property/detail";
	}
	
	@RequestMapping(value="/{id}/property/{propertyid}/update", method=RequestMethod.GET)
	public String sensorPropertyUpdate(@PathVariable Long id, @PathVariable Long propertyid, Model model) {
        System.out.println("GET - sensorPropertyUpdate");
		Sensor sensor = sensorRepository.findOne(id);
		model.addAttribute("sensor", sensor);
		Property property = propertyRepository.findOne(propertyid);
		model.addAttribute("property", property);
		model.addAttribute("propertyType", propertyTypeRepository.findAll());
		return "sensor/property/update";
	}
	
	@RequestMapping(value="/{id}/property/{propertyid}/update", method=RequestMethod.POST)
	public String sensorPropertyUpdate(@ModelAttribute("property") Property property, @PathVariable Long id,
									   @PathVariable Long propertyid, BindingResult bindingResult, Model model) {
        System.out.println("POST - sensorPropertyUpdate");
		System.out.println("Before update: " + property);
		if (bindingResult.hasErrors()) {
			System.out.println("Error occured when updating property.");
			System.out.println(bindingResult);
			model.addAttribute("propertyType", propertyTypeRepository.findAll());
			return "/sensor/property/update";
		}
		
		model.addAttribute("propertyType", propertyTypeRepository.findAll());
		model.addAttribute("sensor", sensorRepository.findOne(id));
		model.addAttribute("property", propertyRepository.findOne(propertyid));
		propertyRepository.save(property);
		
		System.out.println("Property updated successfully!");
		model.addAttribute("successMessage", "Property updated successfully");
//		return "redirect:/sensor";
		return "/sensor/property/detail";
	}

}
