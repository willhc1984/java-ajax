package com.javaajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javaajax.domain.SocialMetaTag;
import com.javaajax.service.SocialMetaTagService;

@SpringBootApplication
public class JavaAjaxApplication implements CommandLineRunner{
	
	@Autowired
	private SocialMetaTagService service;

	public static void main(String[] args) {
		SpringApplication.run(JavaAjaxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SocialMetaTag og = service.getOpenGraphByUrl("https://www.udemy.com/course/curso-html-basico/");
		System.out.println(og);
		
	}

}
