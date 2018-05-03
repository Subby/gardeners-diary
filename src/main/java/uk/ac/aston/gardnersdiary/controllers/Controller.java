package uk.ac.aston.gardnersdiary.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Map;

/**
 * Created by Denver on 15/10/2017.
 */
public abstract class Controller {

    /**
     * Method to handle the displaying of a view.
     * @param request the incoming request
     * @param attributes the attributes to pass along with the template
     * @param templateName the name of the template
     * @return the rendered view
     */
    protected String renderView(Request request, Map<String, Object> attributes, String templateName) {
        ModelAndView modelAndView = new ModelAndView(attributes, templateName);
        return new ThymeleafTemplateEngine().render(modelAndView);
    }
}
