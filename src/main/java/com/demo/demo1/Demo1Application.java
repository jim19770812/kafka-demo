package com.demo.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Demo1Application {
	public static void main(String[] args) {
		try{
			SpringApplication.run(Demo1Application.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
