package org.siva.Random_Quote;

import org.siva.Random_Quote.dao.MailRepo;
import org.siva.Random_Quote.models.Quote;
import org.siva.Random_Quote.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RandomQuoteApplication {
	@Autowired private MailRepo mailRepo;
	@Autowired private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(RandomQuoteApplication.class, args);
	}

	@Configuration
	@EnableScheduling
	@ConditionalOnProperty(name = "Scheduling.enabled",matchIfMissing = true)
	class SchedulingConfiguration{

	}
}
