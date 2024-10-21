package com.ramazanmamyrbek.categorymanagertgbot;

import com.ramazanmamyrbek.categorymanagertgbot.models.Category;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CategoryManagerTgbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryManagerTgbotApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runner(CategoryService categoryService) {
//		return runner -> {
//			saveCategories(categoryService);
//		};
//	}
//
//	private void saveCategories(CategoryService categoryService) {
//		Category car = createCategory("Car", List.of("Bugatti", "Toyota", "BMW"));
//		Category food = createFoodCategory();
//
//		categoryService.save(car);
//		categoryService.save(food);
//
//		List<Category> allRootCategories = categoryService.getAllRootCategories();
//		allRootCategories.forEach(root -> {
//			printCategoryTree(root, 0);
//		});
//
//
//		Category bmw = categoryService.getCategoryByName("BMW");
//
//		categoryService.save(bmw);
//		System.out.println("------------------");
//		allRootCategories = categoryService.getAllRootCategories();
//		allRootCategories.forEach(root -> {
//			printCategoryTree(root, 0);
//		});
//	}
//
//	private Category createCategory(String parentName, List<String> childNames) {
//		Category parent = Category.builder().name(parentName).build();
//		List<Category> children = new ArrayList<>();
//		for (String childName : childNames) {
//			Category child = Category.builder().name(childName).parent(parent).build();
//			children.add(child);
//		}
//		parent.setChildren(children);
//		return parent;
//	}
//
//	private Category createFoodCategory() {
//		Category food = Category.builder().name("Food").build();
//		Category food1 = Category.builder().name("Salad").parent(food).build();
//		Category dairy = Category.builder().name("Dairy").parent(food).build();
//		Category dairy1 = Category.builder().name("Cheese").parent(dairy).build();
//		Category dairy2 = Category.builder().name("Butter").parent(dairy).build();
//		dairy.setChildren(List.of(dairy1, dairy2));
//		food.setChildren(List.of(dairy, food1));
//		return food;
//	}
//
//	private void printCategoryTree(Category category, int level) {
//		System.out.println("  ".repeat(level) + "-" + category.getName());
//		if (category.getChildren() != null) {
//			for (Category child : category.getChildren()) {
//				printCategoryTree(child, level + 1);
//			}
//		}
//	}
}

