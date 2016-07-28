package org.lamzin.eshop.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dmitriy on 27.06.2016.
 */
@Controller
@RequestMapping("/main")
public class MainViewController {
    @RequestMapping(
            method = RequestMethod.GET)
    public ModelAndView getAdminPage(){
        ModelAndView modelandview = new ModelAndView("main");
        return modelandview;
    }
}
