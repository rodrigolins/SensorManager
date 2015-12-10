package sensorsmanager.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sensorsmanager.business.entities.SensorType;
import sensorsmanager.business.repositories.SensorTypeRepository;

@Controller
@RequestMapping("/sensortype")
public class SensorTypeController {
	
	@Autowired
	SensorTypeRepository sensorTypeRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String sensorTypes(Model model) {
		model.addAttribute("sensortypes", sensorTypeRepository.findAll());
		System.out.println(sensorTypeRepository.findAll());
		return "sensortype/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String sensorTypeNew(SensorType SensorType) {
		return "sensortype/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String sensorTypeNew(@Valid SensorType sensorType, BindingResult bindingResult, Model model) {
		System.out.println(sensorType);
		if (bindingResult.hasErrors()) {
			return "sensortype/new";
		}
		sensorTypeRepository.save(new SensorType(sensorType.getName()));
		return "redirect:/sensortype";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String sensorTypeDetail(@PathVariable Long id, Model model) {
		System.out.println("sensortype/" + id);
		SensorType sensorType = sensorTypeRepository.findOne(id);
		System.out.println(sensorType);
		model.addAttribute("sensortype", sensorType);
		return "sensortype/detail";
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.GET)
	public String sensorTypeUpdate(@PathVariable Long id, Model model) {
		System.out.println("sensortype/" + id + "/update");
		SensorType sensorType = sensorTypeRepository.findOne(id);
		model.addAttribute("sensortype", sensorType);
		return "sensortype/update";
	}


}
