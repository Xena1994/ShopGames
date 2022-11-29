package pl.gameshop.controllers;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.gameshop.exceptions.ItemNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice//można wskazać, których konkretnie kontrolerów ma dotyczyć porada

public class GlobalControllerAdvice {

    @ExceptionHandler(ItemNotFoundException.class)
    //@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such vehicle")
    public String handleVehocleNotFoundError(Model model, HttpServletRequest req, Exception ex) {

        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/error404ProductNotFound";
    }

    @ExceptionHandler({JDBCConnectionException.class, DataIntegrityViolationException.class})
    public String handleDbError(Model model, HttpServletRequest req, Exception ex) {

        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/databaseErrorView";
    }



}

