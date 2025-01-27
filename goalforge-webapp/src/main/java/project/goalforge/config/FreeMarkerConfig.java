package project.goalforge.config;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//Set application to configure with free marker template to integrate bootstrap
@org.springframework.context.annotation.Configuration
public class FreeMarkerConfig {
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/");
        return configurer;
    }
    @Bean
    public Configuration configuration() {
        return freeMarkerConfigurer().getConfiguration();
    }
}
