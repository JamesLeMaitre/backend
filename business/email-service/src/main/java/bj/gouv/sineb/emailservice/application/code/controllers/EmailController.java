package bj.gouv.sineb.emailservice.application.code.controllers;

import bj.gouv.sineb.emailservice.application.code.dto.request.EmailRequest;
import bj.gouv.sineb.emailservice.application.code.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email/")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping("send")
    public String sendEmail(@RequestBody EmailRequest request) {
        service.send(request);
        return "Email sent with success";
    }

}
