package br.com.arcls.routes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RoutesApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		//  prompt for the user's name
		System.out.print("Enter your name: ");

		// get their input as a String
		String username = scanner.next();

		System.out.println(username);
		SpringApplication.run(RoutesApplication.class, args);
	}

}
