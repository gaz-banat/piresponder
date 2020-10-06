package net.gaz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PIResponderController {
	
	@GetMapping(value = "/printpis")
	public String returnPIs () {
			PIResponder aPIResponder = new PIResponder();
			return aPIResponder.getAllPIs();
			
	}

}
