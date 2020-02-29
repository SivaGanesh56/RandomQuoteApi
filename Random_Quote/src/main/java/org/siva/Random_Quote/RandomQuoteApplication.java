package org.siva.Random_Quote;

import org.siva.Random_Quote.dao.MailRepo;
import org.siva.Random_Quote.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RandomQuoteApplication  {
	@Autowired private MailRepo mailRepo;
	@Autowired private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(RandomQuoteApplication.class, args);
	}

}
