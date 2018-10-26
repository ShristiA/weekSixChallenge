package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    TwitterRepository twitterrepository;

    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("twits", twitterrepository.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String messageform(Model model){
        model.addAttribute("twitter",new Twitter());
        return "messageForm";
    }
    @PostMapping("/process")
    public String processForm(@Valid Twitter twitter, BindingResult result){
        if(result.hasErrors()){
            return "messageForm";
        }
        twitterrepository.save(twitter);
        return "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showTwits(@PathVariable("id") long id, Model model){
        model.addAttribute("twitter", twitterrepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("twitter",twitterrepository.findById(id).get());
        return "messageForm";
    }

    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id){
        twitterrepository.deleteById(id);
        return "redirect:/";
    }
}
