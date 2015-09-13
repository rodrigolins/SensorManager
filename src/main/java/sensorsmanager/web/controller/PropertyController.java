package sensorsmanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/property")
public class PropertyController {
	
	@RequestMapping("")
	public String listProperties(Model model) {
		return "property/list";
	}
	
	@RequestMapping("/new")
	public String newProperty(Model model) {
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
