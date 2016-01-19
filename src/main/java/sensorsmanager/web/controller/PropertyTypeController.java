package sensorsmanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sensorsmanager.business.entities.Property;
import sensorsmanager.business.entities.PropertyType;
import sensorsmanager.business.repositories.PropertyRepository;
import sensorsmanager.business.repositories.PropertyTypeRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/propertytype")
public class PropertyTypeController {
	
	@Autowired
	PropertyTypeRepository propertyTypeRepository;

    @Autowired
    PropertyRepository propertyRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String propertyTypes(Model model) {
		model.addAttribute("propertytypes", propertyTypeRepository.findAll());
		return "propertytype/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String propertyTypeCreate(PropertyType propertyType) {
		return "propertytype/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String propertyTypeCreate(@Valid PropertyType propertytype, BindingResult bindingResult,
                                     final RedirectAttributes redirectAttributes, Model model) {
		System.out.println(propertytype);
		if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Fail to create a property type. Check error messages.");
			return "propertytype/new";
		}
		propertyTypeRepository.save(new PropertyType(propertytype.getName()));
        redirectAttributes.addFlashAttribute("successMessage", "Property type created successfully");
		return "redirect:/propertytype";
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String propertyTypeDetail(@PathVariable Long id, Model model) {
        System.out.println("PropertyTypeController.propertyTypeDetail");
		PropertyType propertyType = propertyTypeRepository.findOne(id);
		model.addAttribute("propertytype", propertyType);
		return "propertytype/detail";
	}

    @RequestMapping(value="/{id}/update", method=RequestMethod.GET)
    public String propertyTypeUpdate(@PathVariable Long id, Model model) {
        System.out.println("PropertyTypeController.propertyTypeUpdate");
        PropertyType propertyType = propertyTypeRepository.findOne(id);
        model.addAttribute("propertytype", propertyType);
        return "propertytype/update";
    }

    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String propertyTypeUpdate(@PathVariable Long id, @Valid @ModelAttribute("propertytype") PropertyType propertyType,
                                   BindingResult bindingResult, Model model, final RedirectAttributes redirectAttributes) {
        System.out.println("PropertyTypeController.propertyTypeUpdate");
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Fail to update a property type. Check error messages.");
            return "propertytype/update";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Property type updated successfully");
        propertyTypeRepository.save(propertyType);
        return "redirect:/propertytype";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
    public String propertyTypeDelete(@PathVariable Long id, Model model) {
        System.out.println("PropertyTypeController.propertyTypeDelete");
        PropertyType propertyType = propertyTypeRepository.findOne(id);
        model.addAttribute("propertytype", propertyType);
        return "propertytype/delete";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.POST)
    public String propertyTypeDelete(@PathVariable Long id, Model model,
                                   final RedirectAttributes redirectAttributes) {
        System.out.println("PropertyTypeController.propertyTypeDelete");
        redirectAttributes.addFlashAttribute("successMessage", "Property type deleted successfully");
        PropertyType propertyType = propertyTypeRepository.findOne(id);
        Iterable<Property> propertyList = propertyRepository.findAll();

        for (Property property : propertyList){
            if (property.getPropertyType().getName().equals(propertyType.getName())) {
                //bindingResult.addError("Not possible to remove");
                model.addAttribute("errorMessage", "Fail to delete a property type. Not possible to remove a property type " +
                        "if there is a sensor with its type.");
                //model.addAttribute("errorMessage", "If you want to remove this sensor type first you should delete all sensors or change the type of sensors.");
                model.addAttribute("propertytype", propertyType);
                return "propertytype/delete";
            }
        }
        propertyTypeRepository.delete(id);
        return "redirect:/propertytype";
    }

}
