package code.medconnect.infrastructure.configuration;

import code.medconnect.MedConnectApp;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(MedConnectApp.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("MedConnect app")
                        .contact(contact())
                        .version("6.9"));
    }

    private Contact contact() {
        return new Contact()
                .name("Robert")
                .url("https://github.com/0xRobert1997")
                .email("robert.jezierski@protonmail.com");
    }
}
