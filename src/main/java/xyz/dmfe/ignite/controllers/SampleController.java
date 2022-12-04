package xyz.dmfe.ignite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dmfe.ignite.services.NamesService;

@RestController
@RequestMapping("sample")
@RequiredArgsConstructor
public class SampleController {

    private final NamesService namesService;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("test");
        return String.format("{\"message\" : \"Hello, %s!\"}", namesService.getName());
    }
}
