package org.siva.Random_Quote.service;

import org.siva.Random_Quote.dao.MailRepo;
import org.siva.Random_Quote.models.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Autowired private MailRepo mailRepo;
    @Autowired private JavaMailSender javaMailSender;

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
    //0 0 9 */ * *
    @Scheduled(cron="0 0 9 */ * *",zone = "GMT+5:30")
    public String sendEmail(){
        RestTemplate restTemplate = new RestTemplate();
       // Quote quote= restTemplate.getForObject("https://api.quotable.io/random", Quote.class);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<String> mails =
                mailRepo.findAll().stream().
                map(mail -> mail.getEmail()).collect(Collectors.toList());
        String[] emails = new String[mails.size()];
        emails = mails.toArray(emails);
        mailMessage.setCc(emails);
        mailMessage.setSubject("Good Quotes");
        mailMessage.setText("Hello");
        javaMailSender.send(mailMessage);
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
