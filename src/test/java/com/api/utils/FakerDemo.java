package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {

		Faker faker = new Faker(new Locale("en-IND"));

		String firstName = faker.name().firstName();
		
		String lastName = faker.name().lastName();

		System.out.println(firstName);
		
		System.out.println(lastName);
		
		System.out.println(faker.address().buildingNumber());
		
		System.out.println(faker.address().fullAddress());
		
		System.out.println(faker.numerify("Raj###"));
		
		System.out.println(faker.numerify("944#######"));
		
		System.out.println(faker.numerify("944#######"));
		
		System.out.println(faker.numerify("944#######"));
		
		System.out.println(faker.internet().emailAddress());
		
		System.out.println(faker.phoneNumber().phoneNumber());
		
		System.out.println(faker.phoneNumber().cellPhone());
		
	}

}
