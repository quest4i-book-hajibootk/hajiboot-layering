package com.example;


import com.example.domain.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;


@SpringBootApplication
@ComponentScan
public class HajibootLayeringApplication implements CommandLineRunner {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    SimpleJdbcInsert insert;

    @PostConstruct
    public void init() {
        insert = new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
                .withTableName("customers")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public void run(String... args) throws Exception {
        Customer created = customerRepository.save(new Customer(null, "Hidetoshi", "Dekisugi"));
        System.out.println(created + " is created!");

        customerRepository
                .findAll()
                .forEach(System.out::println);
    }


    public static void main(String[] args) {
		SpringApplication.run(HajibootLayeringApplication.class, args);
	}
}
