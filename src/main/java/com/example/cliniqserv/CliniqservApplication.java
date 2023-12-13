package com.example.cliniqserv;

import com.example.cliniqserv.entity.User;
import com.example.cliniqserv.extra.Role;
import com.example.cliniqserv.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CliniqservApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(CliniqservApplication.class, args);
	}

	public void run(String... args) throws Exception {
		User adminAcc = userRepo.findByRole(Role.Admin);
		if(null == adminAcc) {
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
