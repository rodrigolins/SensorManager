package sensorsmanager.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sensorsmanager.business.entities.PropertyType;
import sensorsmanager.business.repositories.PropertyTypeRepository;

@Controller
@RequestMapping("/propertytype")
public class PropertyTypeController {
	
	@Autowired
	PropertyTypeRepository repository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String listPropertyTypes(Model model) {
		model.addAttribute("properties", repository.findAll());
		System.out.println(repository.findAll());
		return "propertytype/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String propertyTypeForm(PropertyType propertyType) {
		return "propertytype/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String newPropertyType(@Valid PropertyType propertytype, BindingResult bindingResult, Model model) {
		System.out.println(propertytype);
		if (bindingResult.hasErrors()) {
			return "propertytype/new";
		}
		repository.save(new PropertyType(propertytype.getName()));
		return "redirect:/propertytype";
	}

}
