package org.lamzin.eshop.viewcontroller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Dmitriy on 25.02.2016.
 */
@Controller
@RequestMapping("/load/admin")
public class AdminLoadController {
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMainAdminPage(){
        return "main";
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
