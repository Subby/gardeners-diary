package uk.ac.aston.gardnersdiary.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Map;

/**
 * Created by Denver on 15/10/2017.
 */
public abstract class Controller {

    protected String renderView(Request request, Map<String, Object> attributes, String templateName) {
        ModelAndView modelAndView = new ModelAndView(attributes, templateName);
        return new ThymeleafTemplateEngine().render(modelAndView);
    }
}
