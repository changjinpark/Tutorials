# Building a RESTful Web Service

### 1. What I Build
Spring Boot를 사용해서 만든 심플한 RESTful Web Service입니다.

HTTP GET 요청(without query string parameter) 

<code>http://localhost:8080/greeting</code>

HTTP GET 응답 

<code>{"id":1,"content":"Hello, World!"}</code>


HTTP GET 요청(with query string parameter) 

<code>http://localhost:8080/greeting?name=User</code>

HTTP GET 응답 

<code>{"id":1,"content":"Hello, User!"}</code>


### 2. GreetingController 에 대한 설명

``` java
package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
```

<code>@GetMapping</code> : HTTP GET 요청 uri 경로 <code>/greeting</code>와 <code>greeting()</code> method가 매핑되도록 해준다. <code>@RequestMapping(method=GET)</code>와 동일 기능

<code>@RequestParam</code> : query string parameter인 name이 <code>greeting()</code> method의 name 매개변수에 바인딩된다. query string parameter 값이 없으면 defaultValue인 "World"가 사용된다.

<code>@RestController</code> : <code>@Controller</code>와 <code>@ResponseBody</code>를 포함하는 약어

<code>incrementAndGet()</code> : +1 증가시키고 변경된 값 리턴


