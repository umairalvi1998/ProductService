package com.example.ProductServices.Controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
@RestController
@RequestMapping("Say")
public class SampleController {
       @GetMapping("Hello/{name}/{times}")
    public String sayHello(@PathVariable String name,@PathVariable("times") int times) {
           StringBuilder output= new StringBuilder();
           for (int i = 0; i < times; i++) {
               output.append("Hello ").append(name).append("!\n");
           }
    return output.toString();
    }
    @GetMapping("Bye")
    public String sayBye() {
           return "Bye World";
    }
}
