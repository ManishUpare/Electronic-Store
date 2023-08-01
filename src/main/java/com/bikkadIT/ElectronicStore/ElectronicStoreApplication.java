package com.bikkadIT.ElectronicStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

//		System.out.println(passwordEncoder.encode("roh123"));
//		System.out.println(passwordEncoder.encode("v@123"));
//		System.out.println(passwordEncoder.encode("sania123"));
//		System.out.println(passwordEncoder.encode("123"));

	}
}
