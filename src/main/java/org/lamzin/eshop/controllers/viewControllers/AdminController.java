package org.lamzin.eshop.controllers.viewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Created by Dmitriy on 18/02/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(
            method = RequestMethod.GET)
    public ModelAndView getAdminPage(){
        ModelAndView modelandview = new ModelAndView("admin");
        return modelandview;
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String getCatalog(){
        return "catalog";
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String getCategoryAdminPage(){
        return "category";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String getSubCategoryAdminPage(){
        return "subcategory";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getProductAdminPage()
    {
        return "product";
    }

}
