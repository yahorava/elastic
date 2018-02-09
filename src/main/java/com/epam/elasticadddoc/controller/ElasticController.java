package com.epam.elasticadddoc.controller;

import com.epam.elasticadddoc.constant.Attribute;
import com.epam.elasticadddoc.constant.Constant;
import com.epam.elasticadddoc.constant.Message;
import com.epam.elasticadddoc.counter.AddCounter;
import com.epam.elasticadddoc.form.IndexForm;
import com.epam.elasticadddoc.service.ElasticIndexerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ElasticController {

    private ElasticIndexerService elasticIndexerService;

    @Autowired
    public ElasticController(ElasticIndexerService elasticIndexerService) {
        this.elasticIndexerService = elasticIndexerService;
    }

    @PostMapping(value = { "/index" })
    public String index(final RedirectAttributes attributes, @ModelAttribute("indexForm") IndexForm indexForm) {
        AddCounter result = elasticIndexerService
                .indexFile(Constant.UPLOADED_FOLDER + indexForm.getPath(), indexForm.getIndex(), indexForm.getType());
        attributes.addFlashAttribute(Attribute.CREATED_ATTR, Message.CREATED + result.getCreated());
        attributes.addFlashAttribute(Attribute.UPDATED_ATTR, Message.UPDATED + result.getUpdated());
        return Constant.REDIRECT;
    }
}
