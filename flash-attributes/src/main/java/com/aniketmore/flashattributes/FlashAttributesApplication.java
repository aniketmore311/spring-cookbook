package com.aniketmore.flashattributes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.servlet.http.HttpSession;

@SpringBootApplication
public class FlashAttributesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashAttributesApplication.class, args);
	}

}

@Controller
class HelloController {

	@GetMapping("/")
	public String Index(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "this is a one time message");
		return "redirect:/hello";
	}

	@GetMapping("/hello")
	public String Hello(Model model) {
		System.out.println("\n");
		System.out.println("the message attribute:");
		System.out.println(model.getAttribute("message"));
		return "hello";
	}

}