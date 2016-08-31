package com.example;


import com.example.domain.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;


@SpringBootApplication
@ComponentScan
public class App implements CommandLineRunner {

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
        Customer created = customerRepository.save(
                new Customer(null, "Hidetoshi", "Dekisugi"));
        System.out.println(created + " is created!");

        Pageable pageable = new PageRequest(1, 3);
        Page<Customer> page = customerRepository.findAll(pageable);
        System.out.println("한 페이지당 데이터 수=" + page.getSize());
        System.out.println("현재 페이지=" + page.getNumber());
        System.out.println("전체 페이지 수=" + page.getTotalPages());
        System.out.println("전체 데이터 수=" + page.getTotalElements());

        page.getContent()
                .forEach(System.out::println);
    }


    public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
