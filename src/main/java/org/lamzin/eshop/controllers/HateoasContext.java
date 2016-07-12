package org.lamzin.eshop.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/**
 * Created by Dmitriy on 31.05.2016.
 */


public class HateoasContext {


    @Configuration
    @EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
    static class HypermediaConfig{
    }
}
