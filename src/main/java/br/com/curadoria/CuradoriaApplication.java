package br.com.curadoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@EnableFeignClients
@PropertySource("classpath:application.properties")
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = { "br.com.generic.logformatter", "br.com.curadoria",
		"br.com.generic.security" })
public class CuradoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuradoriaApplication.class, args);
	}
}
