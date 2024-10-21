package com.ramazanmamyrbek.categorymanagertgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Category Manager Telegram Bot application.
 * This class is annotated with @SpringBootApplication, which enables
 * Spring Boot's auto-configuration and component scanning features.
 */
@SpringBootApplication
public class CategoryManagerTgbotApplication {

	/**
	 * The main method that serves as the entry point for the application.
	 * It starts the Spring application context, which initializes all
	 * the beans and sets up the application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CategoryManagerTgbotApplication.class, args);
	}
}

