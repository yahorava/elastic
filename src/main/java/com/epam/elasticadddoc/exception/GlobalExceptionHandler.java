package com.epam.elasticadddoc.exception;

import com.epam.elasticadddoc.constant.Attribute;
import com.epam.elasticadddoc.constant.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException e, RedirectAttributes redirectAttributes) {
        LOGGER.error(e);
        redirectAttributes.addFlashAttribute(Attribute.ERROR_MESSAGE_ATTR, e.getCause().getMessage());
        return Constant.REDIRECT + Constant.EXCEPTION_MAPPING;
    }

    @ExceptionHandler(ElasticException.class)
    public String handleElasticException(ElasticException e, RedirectAttributes redirectAttributes) {
        LOGGER.error(e);
        redirectAttributes.addFlashAttribute(Attribute.ERROR_MESSAGE_ATTR, e.getCause().getMessage());
        return Constant.REDIRECT + Constant.EXCEPTION_MAPPING;
    }
}
