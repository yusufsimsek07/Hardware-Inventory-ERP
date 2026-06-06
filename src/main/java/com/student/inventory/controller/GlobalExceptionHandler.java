package com.student.inventory.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler.
 * Catches all exceptions and shows a friendly error page instead of stack traces.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("errorMessage",
                "Bu kayit baska tablolarla iliskili oldugu icin silinemez. " +
                "Lutfen once iliskili kayitlari silin.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Beklenmeyen bir hata olustu. Lutfen tekrar deneyin.");
        return "error";
    }
}
