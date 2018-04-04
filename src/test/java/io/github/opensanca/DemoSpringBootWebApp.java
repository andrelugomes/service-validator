package io.github.opensanca;

import javax.validation.constraints.NotNull;

import io.github.opensanca.annotation.ServiceValidation;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DemoSpringBootWebApp implements ApplicationListener<ApplicationReadyEvent> {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBootWebApp.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Locale.setDefault(Locale.ENGLISH);
    }
}

class DTO {

    @NotNull
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

@Component
class MyComponent {

    @ServiceValidation
    public DTO defaultValidation(final DTO dto) {
        return dto;
    }

    @ServiceValidation(nullSafe = false)
    public DTO nullSafeFalse(final DTO dto) {
        return dto;
    }

    @ServiceValidation(javaxValidation = false)
    public DTO javaxValidationFalse(final DTO dto) {
        return dto;
    }

    @ServiceValidation(nullSafe = false, javaxValidation = false)
    public DTO dontDoThat(final DTO dto) {
        return dto;
    }
}

interface MyService {

    DTO doSomething(DTO dto);

    void doSomethingElse(DTO dto1, DTO dto2, DTO dto3);

    String getStringByName(String name);

    Long getLong(Long number, String string);
}

@Service
class MyServiceImpl implements MyService {

    @Autowired
    private MyComponent component;

    @Override
    @ServiceValidation
    public DTO doSomething(final DTO dto) {
        return dto;
    }

    @Override
    @ServiceValidation
    public void doSomethingElse(DTO dto1, DTO dto2, DTO dto3) {}

    @Override
    @ServiceValidation
    public String getStringByName(String name) {
        return name;
    }

    @Override
    @ServiceValidation
    public Long getLong(Long number, String string) {
        return number + Long.valueOf(string);
    }
}

@Controller
class MyController {

    @Autowired
    private MyService service;

    @GetMapping("/test")
    public void test() {
        DTO dto1 = null;
        DTO dto2 = new DTO();
        DTO dto3 = new DTO();
        dto3.setText("TEST");
        this.service.doSomethingElse(dto1, dto2, dto3);
    }

}
