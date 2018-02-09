package com.epam.elasticadddoc.controller;

import com.epam.elasticadddoc.constant.Attribute;
import com.epam.elasticadddoc.constant.Page;
import com.epam.elasticadddoc.form.IndexForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String showWelcomePage(Model model) {
        IndexForm indexForm = new IndexForm();
        model.addAttribute(Attribute.INDEX_FORM_ATTR, indexForm);
        return Page.MAIN_PAGE;
    }

    @RequestMapping("/exception")
    public String showErrorPage() {
        return Page.ERROR_PAGE;
    }
}
