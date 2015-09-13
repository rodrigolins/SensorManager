package sensorsmanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sensor")
public class SensorController {
	
	@RequestMapping("")
	public String listSensors(Model model) {
		return "sensor/list";
	}
	
	@RequestMapping("/new")
	public String newSensor(Model model) {
		return "sensor/new";
	}
	
	@RequestMapping("/{id}/delete")
	public String deleteSensor(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "sensor/delete";
	}
	
	@RequestMapping("/{id}")
	public String sensorDetail(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "sensor/detail";
	}

}
