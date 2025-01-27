package project.goalforge.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//Configuration to set up security with spring boot
@Configuration
public class SecurityConfig {
    //Inject bean that incorporates BCrypt password encoding to use in classes
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
