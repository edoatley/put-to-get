package com.edoatley.puttoget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;

@RestController
@Validated
public class Controller {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @GetMapping("/hello")
    public String message(@RequestParam Map<String,String> allRequestParams) {
           RequestBody body = new ObjectMapper().convertValue(allRequestParams, RequestBody.class);
           validator.validate(body);
           return "Hello from GET " + body.getName();
    }

    @PutMapping("/hello")
    public String messageFromPut(@Valid @org.springframework.web.bind.annotation.RequestBody RequestBody body) {
        String name = body.getName();
        Map<String, String> map = new ObjectMapper().convertValue(body, new TypeReference<>() {});
        return message(map);
    }
}
