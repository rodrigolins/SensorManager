package sensorsmanager.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sensorsmanager.business.entities.Property;
import sensorsmanager.business.entities.PropertyType;
import sensorsmanager.business.repositories.PropertyRepository;
import sensorsmanager.business.repositories.PropertyTypeRepository;

@Controller
@RequestMapping("/property")
public class PropertyController {

	@Autowired
	PropertyRepository propertyRepository;
	
	@Autowired
	PropertyTypeRepository propertyTypeRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String propertiesList(Model model) {
		model.addAttribute("properties", propertyRepository.findAll());
		return "property/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String PropertyForm(Model model) {
		model.addAttribute("propertyType", propertyTypeRepository.findAll());
		model.addAttribute("property", new Property());
		return "property/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String propertyNew(@Valid Property property, BindingResult bindingResult, Model model) {
		System.out.println(property);
		model.addAttribute("propertyType", propertyTypeRepository.findAll());
		if (bindingResult.hasErrors()) {
			return "property/new";	
		}
		propertyRepository.save(new Property(property.getPropertyType(), property.getValue(), property.getUnit(), property.getBoundary()));
		return "redirect:/property";
	}
	
	@RequestMapping("/{id}/delete")
	public String deleteProperty(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "property/delete";
	}
	
	@RequestMapping("/{id}")
	public String propertyDetail(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "property/detail";
	}

}
