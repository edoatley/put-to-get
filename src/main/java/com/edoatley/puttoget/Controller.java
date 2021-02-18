package com.edoatley.puttoget;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@Validated
public class Controller {

    @GetMapping("/hello")
    public String message(@RequestParam @Size(max=7) String name) {
           return "Hello from GET " + name;
    }

    @PutMapping("/hello")
    public String messageFromPut(@org.springframework.web.bind.annotation.RequestBody RequestBody body) {
        String name = body.getName();
        return message(name);
    }
}
