package org.siva.Random_Quote.controller;

import org.siva.Random_Quote.models.Mail;
import org.siva.Random_Quote.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("")
public class Api {

    @Autowired  private MailService mailService;

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public ModelAndView sample(){
        ModelAndView mav = new ModelAndView("home.html");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView showUser(){
        ModelAndView mav = new ModelAndView("add.html");
        Mail mail = new Mail();
        mav.addObject("mail",mail);
        return mav;
    }

    @PostMapping("/save")
    public String addUser(@Valid @ModelAttribute("mail") Mail mail, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "<h3 align =\"center\" style=\"color:red\">please provide Valid Mail</h3>";
        }
        else{
            int id = mailService.addUser(mail);
            if(id!=-1) {
                return "<h3 align =\"center\" style=\"color:green\">" +
                        "Your id is :" + id + "\n" + "Please Save it for future use</h3>";
            }
            else{
                return "<h3 align =\"center\" style=\"color:blue\">Your Mail May exist</h3>";
            }
        }
    }

    @RequestMapping("/remove")
    public ModelAndView showDeleteForm(){
        ModelAndView mav = new ModelAndView("unsubscribe.html");
        Mail mail = new Mail();
        mav.addObject("mail",mail);
        return mav;
    }

    @PostMapping("/delete")
    public String unsubscribe(@Valid  @ModelAttribute("mail") Mail mail,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "<h3 align =\"center\" style=\"color:red\">Please Provide Valid details</h3>";
        }
        else{
            int val = mailService.removeUser(mail.getEmail());
            if(val==1) {
                return "<h3 align =\"center\" style=\"color:green\">Succesfully unsubscribed</h3>";
            }
            else{
                return "<h3 align =\"center\" style=\"color:blue\">Mail You requested does not exists</h3>";
            }
        }
    }

    @GetMapping("/all")
    public List<Mail> getUsers(){
        return mailService.getAll();
    }

    @GetMapping("/id")
    public ModelAndView ShowIdForm(){
      ModelAndView mav = new ModelAndView("id.html");
      Mail mail = new Mail();
      mav.addObject("mail",mail);
      return mav;
    }

    @PostMapping("/getId")
    public String getId(@Valid @ModelAttribute("mail") Mail mail,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "<h3 align =\"center\" style=\"color:red\">Please Enter Valid Mail</h3>";
        }
        else {
            Mail email = mailService.getIdByEmail(mail.getEmail());
            if (email == null) {
                return "<h3 align =\"center\" style=\"color:blue\">Sorry Requested mail was not foundl</h3>";
            }
            return "<h3 align =\"center\" style=\"color:green\">Your id is:</h3>" + email.getId();
        }
    }
}
