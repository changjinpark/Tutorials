# Building a RESTful Web Service

## What I Build
Spring Boot를 사용해서 만든 심플한 RESTful Web Service입니다.

| HTTP GET 요청 | HTTP GET 응답 | 
|:--------:|:--------:|
| <code>http://localhost:8080/greeting</code> | <code>{"id":1,"content":"Hello, World!"}</code> | 
| <code>http://localhost:8080/greeting?name=User</code> | <code>{"id":1,"content":"Hello, User!"}</code> |



## GreetingController 소스 살펴보기

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

| 코드 | 설명 | 
|:--------:|:--------:|
| <code>@RestController</code> | JSON을 반환하는 컨트롤러 <br> <code>@Controller</code>와 <code>@ResponseBody</code>를 합친 약어 | 
| <code>@GetMapping</code> | HTTP Method인 Get 요청을 받을 수 있는 API를 만들어 준다. <br> <code>/greeting</code>을 greeting() 메소드에 매핑되도록 해준다. <br> 이전엔 <code>@RequestMapping(method=RequestMethod.GET)</code>으로 사용되었음 |
| <code>@RequestParam</code> | 외부에서 API로 넘긴 파라미터을 가져오는 어노테이션 <br> 외부에서 name이란 이름으로 넘긴 파라미터를 greeting() 메소드의 파라미터에 저장 <br>외부 파라미터가 없으면 "World"가 디폴트로 사용된다. |
| <code>incrementAndGet()</code> | AtomicLong 클래스의 메소드 <br> counter를 +1 증가시키고 변경된 값 리턴 |



