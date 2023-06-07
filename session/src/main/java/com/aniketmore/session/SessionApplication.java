package com.aniketmore.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@SpringBootApplication
public class SessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionApplication.class, args);
	}

}

@Controller
class HelloController {
	@GetMapping("/")
	public String hello(HttpSession session, Model model) {
		// Get the counter from the session
		Counter counter = (Counter) session.getAttribute("counter");

		if (counter == null) {
			// If the counter doesn't exist in the session, create a new one and store it
			counter = new Counter();
			session.setAttribute("counter", counter);
		}

		// Increment the counter and update the session
		counter.increment();

		// Add the counter to the model to display it on the home page
		model.addAttribute("count", counter.getCount());

		return "hello";
	}
}

@Getter
@Setter
class Counter {
	int count;

	Counter() {
		this.count = 0;
	}

	public void increment() {
		this.count = this.count + 1;
	}
}