package pds.stardust.frequentation;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
@EnableEncryptableProperties
public class FrequentationApplication {


	public static void main(String[] args) {
		SpringApplication.run(FrequentationApplication.class, args);
	}
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

}
