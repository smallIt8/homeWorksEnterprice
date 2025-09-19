package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		var context = new ClassPathXmlApplicationContext("application.xml");
	}
}
