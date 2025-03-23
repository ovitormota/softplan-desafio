package com.desafio.softplan;

import org.springframework.boot.SpringApplication;

public class TestSoftplanApplication {

	public static void main(String[] args) {
		SpringApplication.from(SoftplanApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
