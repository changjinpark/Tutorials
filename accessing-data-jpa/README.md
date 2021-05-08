# Accessing Data with JPA

## What I Build

<code>Customer</code> POJOs(Plain Old Java Objects)를 인메모리 관계형 데이터베이스(h2)에 저장하는 애플리케이션입니다.

## Customer 클래스를 JPA Entity 클래스로 정의하기

``` java
package com.example.accessingdatajpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;

  protected Customer() {}

  public Customer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%d, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
```

| 코드 | 설명 | 
|:--------:|:--------:|
| <code>@Entity</code> | <code>Customer</code> 클래스가 테이블에 매핑되는 클래스임을 나타낸다. <br> <code>@Table</code> 이 없기 때문에 <code>Customer</code>이름 테이블에 매핑 | 
| <code>@Id</code> | 해당 테이블의 PK 필드를 나타낸다. |
| <code>@GeneratedValue</code> | PK의 생성 규칙을 나타낸다. +1씩 자동 증가. |
| <code>Long id;<br>String firstName; <br>String lastName;</code> | 이 필드들은 모두 테이블의 칼럼이 된다. <br> 옵션 변경이 필요하면 <code>@Column</code>를 사용한다. |
| <code>protected Customer() {}</code> | JPA를 위한 기본 생성자이고, 이걸 직접 사용하지 않음. <br> DB에 저장하기 위해 사용하는 생성자는 <code>public Customer(String firstName, String lastName) {...}</code> 이다.   |

## Repository 만들기

``` java
package com.example.accessingdatajpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}
```

| 코드 | 설명 | 
|:--------:|:--------:|
| <code>CrudRepository</code> | 기본적인 CRUD 기능을 제공한다. | 
| <code>List&lt;Customer> findByLastName(String lastName);</code> | 추가한 Query 메소드이고, 사용 규칙이 있음<br>findBy에 이어 해당 Entity 필드 이름을 입력 |

## Application 클래스 만들기

``` java
package com.example.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {

  private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataJpaApplication.class);
  }

  @Bean
  public CommandLineRunner demo(CustomerRepository repository) {
    return (args) -> {
      // save a few customers
      repository.save(new Customer("Jack", "Bauer"));
      repository.save(new Customer("Chloe", "O'Brian"));
      repository.save(new Customer("Kim", "Bauer"));
      repository.save(new Customer("David", "Palmer"));
      repository.save(new Customer("Michelle", "Dessler"));

      // fetch all customers
      log.info("Customers found with findAll():");
      log.info("-------------------------------");
      for (Customer customer : repository.findAll()) {
        log.info(customer.toString());
      }
      log.info("");

      // fetch an individual customer by ID
      Customer customer = repository.findById(1L);
      log.info("Customer found with findById(1L):");
      log.info("--------------------------------");
      log.info(customer.toString());
      log.info("");

      // fetch customers by last name
      log.info("Customer found with findByLastName('Bauer'):");
      log.info("--------------------------------------------");
      repository.findByLastName("Bauer").forEach(bauer -> {
        log.info(bauer.toString());
      });
      // for (Customer bauer : repository.findByLastName("Bauer")) {
      //  log.info(bauer.toString());
      // }
      log.info("");
    };
  }

}
```

| 코드 | 설명 | 
|:--------:|:--------:|
| <code>@SpringBootApplication</code> | 스프링 Bean 읽기와 생성이 모두 자동으로 설정된다.<br> 해당 위치부터 설정을 읽어간다. | 
| <code>CommandLineRunner</code> | 애플리케이션 구동 시점에 코드를 실행시키는 방법 |

## 출력 결과

``` 
== Customers found with findAll():
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=2, firstName='Chloe', lastName='O'Brian']
Customer[id=3, firstName='Kim', lastName='Bauer']
Customer[id=4, firstName='David', lastName='Palmer']
Customer[id=5, firstName='Michelle', lastName='Dessler']

== Customer found with findById(1L):
Customer[id=1, firstName='Jack', lastName='Bauer']

== Customer found with findByLastName('Bauer'):
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=3, firstName='Kim', lastName='Bauer']
```