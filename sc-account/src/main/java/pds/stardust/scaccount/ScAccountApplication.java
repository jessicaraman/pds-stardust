package pds.stardust.scaccount;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pds.stardust.scaccount.service.CustomerService;

import javax.annotation.PostConstruct;
import java.security.Security;

@SpringBootApplication
@EnableEncryptableProperties
public class ScAccountApplication  {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(ScAccountApplication.class, args);
	}

	@PostConstruct
	public void initData () {
		customerService.initCustomerData();
	}
}