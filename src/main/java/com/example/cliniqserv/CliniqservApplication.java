package com.example.cliniqserv;

import com.example.cliniqserv.entity.User;
import com.example.cliniqserv.extra.Role;
import com.example.cliniqserv.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class CliniqservApplication extends SpringBootServletInitializer implements CommandLineRunner, WebMvcConfigurer  {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(CliniqservApplication.class, args);
	}

	public void run(String... args) throws Exception {
	// User adminAcc = userRepo.findByRole(Role.Admin).get(0);
		List<User> users = userRepo.findByRole(Role.Admin);
		System.out.println("users: " + users.size());
		if(users.isEmpty()) {
			User user = new User();
			user.setLogin("admin");
			user.setName("admin");
			user.setSurname("admin");
			user.setRole(Role.Admin);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepo.save(user);
		}
	}
}
