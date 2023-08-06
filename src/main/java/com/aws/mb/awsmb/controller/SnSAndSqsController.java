package com.aws.mb.awsmb.controller;

import com.aws.mb.awsmb.service.SnSAndSqsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnSAndSqsController {
    private final SnSAndSqsService service;

    public SnSAndSqsController(SnSAndSqsService service) {
        this.service = service;
    }

    @PostMapping("/subscribe")
    public String subscribeToTopic(@RequestParam("email") String email) {
        return service.subscribeToTopic(email);
    }


    @PostMapping("/unsubscribe")
    public String unsubscribeToTopic(@RequestParam("email") String email) {
        return service.unsubscribeToTopic(email);
    }

}
