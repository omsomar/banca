package com.bp.banca.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("oms.omar@gmail.com");
        contact.setName("Omar Solarte Martinez");
        contact.setUrl("https://www.linkedin.com/omar-solarte-java-developer");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8089");
        localServer.setDescription("Server de pruebas");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("BANCA API - PRUEBA TECNICA - NEORIS - BANCO PICHINCHA")
                .contact(contact)
                .version("1.0")
                .description("Esta api expone los servicios para manejar cuentas, transacciones y clientes de un banco como prueba.")
                .termsOfService("https://my-awesome-api.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
