package pds.stardust.scaccount;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pds.stardust.scaccount.service.CustomerService;
import pds.stardust.scaccount.service.SwaggerService;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.security.Security;

/**
 * Main application class
 */
@SpringBootApplication
@EnableSwagger2
@EnableEncryptableProperties
public class ScAccountApplication {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Autowired
	private CustomerService customerService;
	@Autowired
	private SwaggerService swaggerService;

	public static void main(String[] args) {
		SpringApplication.run(ScAccountApplication.class, args);
	}

	/**
	 * Docket configuration wrapper for swagger
	 */
	@Bean
	public Docket SwaggerConfiguration() {
		// Return a prepared Docket Instance
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("pds.stardust.scaccount.controller"))
				.build()
				.apiInfo(swaggerService.apiDetails());
	}

	@PostConstruct
	public void initData() {
		customerService.initCustomerData();
	}
}