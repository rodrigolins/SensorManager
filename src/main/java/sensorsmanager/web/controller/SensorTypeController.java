package sensorsmanager.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sensorsmanager.business.entities.PropertyType;
import sensorsmanager.business.entities.SensorType;
import sensorsmanager.business.repositories.SensorTypeRepository;

@Controller
@RequestMapping("/sensortype")
public class SensorTypeController {
	
	@Autowired
	SensorTypeRepository sensorTypeRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String sensorTypesList(Model model) {
		model.addAttribute("sensortypes", sensorTypeRepository.findAll());
		System.out.println(sensorTypeRepository.findAll());
		return "sensortype/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String sensorTypeForm(SensorType SensorType) {
		return "sensortype/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String SenropertyTypeNew(@Valid SensorType sensorType, BindingResult bindingResult, Model model) {
		System.out.println(sensorType);
		if (bindingResult.hasErrors()) {
			return "sensortype/new";
		}
		sensorTypeRepository.save(new SensorType(sensorType.getName()));
		return "redirect:/sensortype";
	}


}
