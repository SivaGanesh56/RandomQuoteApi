package org.siva.Random_Quote.service;

import org.siva.Random_Quote.dao.MailRepo;
import org.siva.Random_Quote.models.Mail;
import org.siva.Random_Quote.models.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Autowired private MailRepo mailRepo;
    @Autowired private JavaMailSender javaMailSender;
    RestTemplate restTemplate = new RestTemplate();

    public int addUser(Mail mail){ ;
        Mail email = getIdByEmail(mail.getEmail());
        if(email==null){
            return mailRepo.saveAndFlush(mail).getId();
        }
        return -1;
    }

    public List<Mail> getAll(){
        List<Mail> mails = mailRepo.findAll();
        System.out.println(mails);
        return mails;
    }

    @Scheduled(cron = "${job.run}")
    public String sendEmail() throws InterruptedException{
//        System.out.println("You are in .......................");
        HttpHeaders headers = new HttpHeaders();
        String url ="https://quotes15.p.rapidapi.com/quotes/random/?language_code=en";
        headers.set("x-rapidapi-key", "919d9b84d7mshe45ea2752cfd2d1p19033cjsn3bc42e6e81be");
        HttpEntity request = new HttpEntity(headers);
        String body = new String();
        ResponseEntity<Quote> response = this.restTemplate.exchange(url, HttpMethod.GET, request, Quote.class);
        if(response.getStatusCode() == HttpStatus.OK) {
           body=response.getBody().print();
        } else {
          body="Good Morning"+'\n'+"Have a Nice day";
        }
        List<Mail> All = mailRepo.findAll();
        if(All==null || All.isEmpty()){
            return "No Mails";
        }
        body=body+ "\n\n\n\n" + "To Subscribe/Unsubscribe please visit ====>  "     +"https://morning-quotes.herokuapp.com/";

        List<@Email @NotNull String> mails = All.stream().
                map(mail -> mail.getEmail()).collect(Collectors.toList());
        String[] emails = new String[mails.size()];
            emails = mails.toArray(emails);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setCc(emails);
        mailMessage.setSubject("Good Quotes");
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
//        System.out.println("Mail sent &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        return "Succesfully sended the mail";

    }

    public Mail getIdByEmail(String mail){
        return mailRepo.findByEmail(mail);
    }

    public int removeUser(String mailId){
        Mail mail = getIdByEmail(mailId);
        if(mail!=null){
            mailRepo.delete(mail);
            return 1;
        }
        return 0;
    }
}
