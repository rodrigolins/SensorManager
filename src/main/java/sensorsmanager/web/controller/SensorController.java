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
                               BindingResult bindingResult, Model model, final RedirectAttributes redirectAttributes) {
        System.out.println("POST - sensorUpdate");
        System.out.println("Before update: " + sensor);
        model.addAttribute("sensorType", sensorTypeRepository.findAll());

        if (bindingResult.hasErrors()) {
            System.out.println("Error occured when updating sensor.");
            System.out.println(bindingResult);
            model.addAttribute("errorMessage", "Fail to update a sensor. Check error messages.");
            return "sensor/update";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Sensor updated successfully");
        sensorRepository.save(sensor);
        return "redirect:/sensor/" + id.toString();
    }

	@RequestMapping(value="/{id}/delete", method = RequestMethod.GET)
	public String sensorDelete(@PathVariable Long id, Model model) {
        System.out.println("GET - deleteSensor");
        Sensor sensor = sensorRepository.findOne(id);
		model.addAttribute("sensor", sensor);
		return "sensor/delete";
	}

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String sensorDelete(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
        System.out.println("POST - deleteSensor");
        redirectAttributes.addFlashAttribute("successMessage", "Sensor deleted successfully");
        sensorRepository.delete(id);
        return "redirect:/sensor";
    }

    @RequestMapping(value = "/{id}/property/{propertyid}/delete", method = RequestMethod.POST)
    public String removePropertyFromSensor(@PathVariable Long id, @PathVariable Long propertyid,
                                           Model model, final RedirectAttributes redirectAttributes) {
        System.out.println("POST - removePropertyFromSensor");
        Sensor sensor = sensorRepository.findOne(id);

        List<Property> properties = sensor.getProperties();
        for(Property property : properties) {
            if(property.getId() == propertyid) {
                sensor.getProperties().remove(property);
                sensorRepository.save(sensor);
                propertyRepository.delete(property);
                redirectAttributes.addFlashAttribute("successMessage", "Property deleted successfully");
                return "redirect:/sensor/" + id.toString();
            }
        }
        model.addAttribute("sensor", sensor);
        Property property = propertyRepository.findOne(propertyid);
        model.addAttribute("property", property);

        model.addAttribute("errorMessage", "Fail to delete a property. Check error messages.");
        return "sensor/property/delete";
    }
	
	@RequestMapping(value="/{id}/property/{propertyid}/delete", method=RequestMethod.GET)
	public String removePropertyFromSensor(@PathVariable Long id, @PathVariable Long propertyid,
                                           Model model) {
        System.out.println("GET - removePropertyFromSensor");
        Sensor sensor = sensorRepository.findOne(id);
        model.addAttribute("sensor", sensor);
		Property property = propertyRepository.findOne(propertyid);
        model.addAttribute("property", property);

		return "sensor/property/delete";
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

        propertyRepository.save(newProperty);
        sensor.addProperty(newProperty);
        sensorRepository.save(sensor);
        return "redirect:/sensor/" + id.toString();
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
	public String sensorPropertyUpdate(@PathVariable Long id, @PathVariable Long propertyid,
                                       @Valid @ModelAttribute("property") Property property,
									   BindingResult bindingResult, Model model,
                                       final RedirectAttributes redirectAttributes) {
        System.out.println("POST - sensorPropertyUpdate");

        if (bindingResult.hasErrors()) {
			System.out.println("Error occured when updating property.");
			System.out.println(bindingResult);
			model.addAttribute("propertyType", propertyTypeRepository.findAll());
            model.addAttribute("sensor", sensorRepository.findOne(id));
            model.addAttribute("property", propertyRepository.findOne(propertyid));
            model.addAttribute("errorMessage", "Fail to update a property. Check error messages.");
			return "/sensor/property/update";
		}
		
		propertyRepository.save(property);
		
        redirectAttributes.addFlashAttribute("successMessage", "Property updated successfully");
		return "redirect:/sensor/" + id.toString();
	}

}
