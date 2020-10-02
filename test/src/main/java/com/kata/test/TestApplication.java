package com.kata.test;

import com.kata.test.domain.Account;
import com.kata.test.repository.AccountRepository;
import com.kata.test.repository.OperationRepository;
import com.kata.test.service.AccountService;
import com.kata.test.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

	}

}
