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
import sensorsmanager.business.entities.Sensor;
import sensorsmanager.business.entities.SensorType;
import sensorsmanager.business.repositories.SensorRepository;
import sensorsmanager.business.repositories.SensorTypeRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/sensortype")
public class SensorTypeController {
	
	@Autowired
	SensorTypeRepository sensorTypeRepository;

    @Autowired
    SensorRepository sensorRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String sensorTypes(Model model) {
        System.out.println("SensorTypeController.sensorTypes");
        model.addAttribute("sensortypes", sensorTypeRepository.findAll());
		System.out.println(sensorTypeRepository.findAll());
		return "sensortype/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String sensorTypeCreate(SensorType SensorType) {
        System.out.println("SensorTypeController.sensorTypeCreate");
        return "sensortype/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String sensorTypeCreate(@Valid SensorType sensorType, BindingResult bindingResult,
								final RedirectAttributes redirectAttributes, Model model) {
        System.out.println("SensorTypeController.sensorTypeCreate");
        System.out.println(sensorType);
		if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Fail to create a sensor type. Check error messages.");
			return "sensortype/new";
		}
		sensorTypeRepository.save(new SensorType(sensorType.getName()));
		redirectAttributes.addFlashAttribute("successMessage", "Sensor type created successfully");
		return "redirect:/sensortype";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String sensorTypeDetail(@PathVariable Long id, Model model) {
        System.out.println("SensorTypeController.sensorTypeDetail");
        System.out.println("sensortype/" + id);
		SensorType sensorType = sensorTypeRepository.findOne(id);
		System.out.println(sensorType);
		model.addAttribute("sensortype", sensorType);
		return "sensortype/detail";
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.GET)
	public String sensorTypeUpdate(@PathVariable Long id, Model model) {
        System.out.println("SensorTypeController.sensorTypeUpdate");
        SensorType sensorType = sensorTypeRepository.findOne(id);
		model.addAttribute("sensortype", sensorType);
		return "sensortype/update";
	}

    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String sensorTypeUpdate(@PathVariable Long id, @Valid @ModelAttribute("sensortype") SensorType sensorType,
                                   BindingResult bindingResult, Model model, final RedirectAttributes redirectAttributes) {
        System.out.println("SensorTypeController.sensorTypeUpdate");

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Fail to update a sensor type. Check error messages.");
            return "sensortype/update";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Sensor type updated successfully");
        sensorTypeRepository.save(sensorType);
        return "redirect:/sensortype";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
    public String sensorTypeDelete(@PathVariable Long id, Model model) {
        System.out.println("SensorTypeController.sensorTypeDelete");
        SensorType sensorType = sensorTypeRepository.findOne(id);
        model.addAttribute("sensortype", sensorType);
        return "sensortype/delete";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.POST)
    public String sensorTypeDelete(@PathVariable Long id, Model model,
                                   final RedirectAttributes redirectAttributes) {
        System.out.println("SensorTypeController.sensorTypeDelete");
        redirectAttributes.addFlashAttribute("successMessage", "Sensor type deleted successfully");
        SensorType sensorType = sensorTypeRepository.findOne(id);
        Iterable<Sensor> sensorList = sensorRepository.findAll();

        for (Sensor sensor : sensorList){
            if (sensor.getSensorType().getName().equals(sensorType.getName())) {
                //bindingResult.addError("Not possible to remove");
                model.addAttribute("errorMessage", "Fail to delete a sensor type. Not possible to remove a sensor type " +
                        "if there is a sensor with its type.");
                //model.addAttribute("errorMessage", "If you want to remove this sensor type first you should delete all sensors or change the type of sensors.");
                model.addAttribute("sensortype", sensorType);
                return "sensortype/delete";
            }
        }
        sensorTypeRepository.delete(id);
        return "redirect:/sensortype";
    }

}
