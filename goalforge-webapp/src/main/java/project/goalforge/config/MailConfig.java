package project.goalforge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/*Mark this class as a configuration source for Spring Boot*/
@Configuration
public class MailConfig {

    /*We inject mail host, port, username, and password value in order to connect with the SMP protocol*/

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    /*Define bean to configure JavaMailSender with specific properties that were injected previously*/
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host); //Set SMP host
        mailSender.setPort(port); //Set SMP port
        mailSender.setUsername(username); //Set SMP username
        mailSender.setPassword(password); //Set SMP password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); //Set protocol to the SMTP
        props.put("mail.smtp.auth", "true"); //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS
        props.put("mail.smtp.ssl.trust", host); // Trust SMP server host
        props.put("mail.debug", "true"); //Enable debugging to output SMTP server interactions

        return mailSender;
    }
}
