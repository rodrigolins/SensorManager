package sensorsmanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sensorsmanager.business.entities.Property;
import sensorsmanager.business.repositories.PropertyRepository;

@Controller
@RequestMapping("/property")
public class PropertyController {

	@Autowired
	PropertyRepository repository;
	
	@RequestMapping("")
	public String listProperties(Model model) {
		return "property/list";
	}
	
	@RequestMapping("/new")
	public String newProperty(Property property) {
		return "property/new";
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
