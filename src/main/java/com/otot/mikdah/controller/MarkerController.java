package com.otot.mikdah.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MarkerController {

    @RequestMapping(value="/map")
    public String trafficSpy() {
        System.out.println("hhh");
        return "index";
    }

//    @RequestMapping({"/", "/index"})
//    public ModelandView index(){
//        LOGGER.info("Running index from           MVC, returning index view");
//        return new ModelandView("index");
//    }




    @RequestMapping("/welcome.html")
    public ModelAndView firstPage() {
            return new ModelAndView("hello");
        }


}
