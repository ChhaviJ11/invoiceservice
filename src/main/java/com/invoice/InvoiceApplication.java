package com.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ="com.invoice")
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
		System.out.println("================================");
		System.out.println("Invoice Service Started");
		System.out.println("================================");

	}

}
