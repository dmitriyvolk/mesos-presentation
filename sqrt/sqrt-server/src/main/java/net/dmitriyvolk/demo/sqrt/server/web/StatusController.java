package net.dmitriyvolk.demo.sqrt.server.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class StatusController {

    public static final String SQRT_SERVER = "SQRT Server";

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public InfoDto infoJson(HttpServletRequest request) {
        return new InfoDto(request);
    }

    @JsonAutoDetect
    public static class InfoDto {
        public final String name = "SQRT Server";
        public final String version = "1.0-SNAPSHOT";
        public final String url;

        public InfoDto(HttpServletRequest request) {
            this.url = "/" + request.getContextPath();
        }
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public HealthIndicator health() {
        return new HealthIndicator();
    }

    @JsonAutoDetect
    public static class HealthIndicator {
        public final String description = SQRT_SERVER;
        public final String status = "UP";
    }
}
