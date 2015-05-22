package net.dmitriyvolk.demo.sqrt.server.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebController {

    @MessageMapping("/status")
    public void connectWS() {
        System.err.println("Something connected");
    }
}
