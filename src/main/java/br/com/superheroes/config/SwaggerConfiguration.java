package br.com.superheroes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class SwaggerConfiguration {
  private Environment environment;

  @Bean
  public OpenAPI openAPI() {
    String contactName =
        environment.getProperty("springdoc.documentation.swagger.v2.api.info.contact.name");
    String contactEmail =
        environment.getProperty("springdoc.documentation.swagger.v2.api.info.contact.email");
    Contact contact = new Contact().name(contactName).email(contactEmail).url("");

    License license =
        new License()
            .name(environment.getProperty("springdoc.documentation.swagger.v2.api.info.license"))
            .url(environment.getProperty("springdoc.documentation.swagger.v2.api.info.licenseUrl"));

    return new OpenAPI()
        .info(
            new Info()
                .title(environment.getProperty("springdoc.documentation.swagger.v2.api.info.title"))
                .description(
                    environment.getProperty(
                        "springdoc.documentation.swagger.v2.api.info.description"))
                .version(
                    environment.getProperty("springdoc.documentation.swagger.v2.api.info.version"))
                .contact(contact)
                .license(license));
  }
}
