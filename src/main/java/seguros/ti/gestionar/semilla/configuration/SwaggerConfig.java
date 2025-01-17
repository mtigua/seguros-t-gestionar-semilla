package seguros.ti.gestionar.semilla.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport{
       
	   @Value("${SWAGGER_BASEPATH}")
	   private String basePath;
	
		@Bean
		public Docket apiDocket() {
		    return new Docket(DocumentationType.SWAGGER_2)
		    		.host(basePath)
		            .select()
		            .apis(RequestHandlerSelectors.basePackage("seguros.ti."))
		            .paths(PathSelectors.any())
		            .build();
		}
		
		@Override
		   public void addResourceHandlers(ResourceHandlerRegistry registry) {
		       registry.addResourceHandler("swagger-ui.html")
		       .addResourceLocations("classpath:/META-INF/resources/");

		       registry.addResourceHandler("/webjars/**")
		       .addResourceLocations("classpath:/META-INF/resources/webjars/");
		   }
		
		
}