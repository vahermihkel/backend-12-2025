package ee.mihkel.webshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
//        System.out.println("Inside AppConfig");
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("sandbox.smtp.mailtrap.io");
        mailSender.setPort(587);

        mailSender.setUsername("f8c1f0cf5e22a2");
        mailSender.setPassword("0b30df2a58aaef");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Helpful for debugging

        return mailSender;
    }
//
//    @Bean
//    public ModelMapper getModelMapper(){
//        return new ModelMapper();
//    }
}
