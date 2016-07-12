package org.lamzin.eshop.controllers.viewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dmitriy on 29.06.2016.
 */
@Controller
@RequestMapping("/bucket")
public class BucketViewController {

    @RequestMapping(
            method = RequestMethod.GET)
    public ModelAndView getBucketPage(){
        ModelAndView modelandview = new ModelAndView("bucket");
        return modelandview;
    }
}
