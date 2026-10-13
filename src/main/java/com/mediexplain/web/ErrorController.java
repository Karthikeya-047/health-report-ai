package com.mediexplain.web;
import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;import java.util.NoSuchElementException;
@org.springframework.stereotype.Controller @org.springframework.web.bind.annotation.ControllerAdvice class ErrorController {
 @ExceptionHandler(NoSuchElementException.class) @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND) public String notFound(Model m){m.addAttribute("message","We couldn't find that page or it may no longer be available.");return "error";}
 @ExceptionHandler(Exception.class) @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR) public String error(Model m){m.addAttribute("message","Something went wrong. Please return to your dashboard and try again.");return "error";}
}
