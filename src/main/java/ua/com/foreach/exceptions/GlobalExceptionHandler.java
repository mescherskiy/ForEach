package ua.com.foreach.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(NullEntityReferenceException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ModelAndView nullEntityReferenceExceptionHandler(HttpServletRequest request,
//                                                            NullEntityReferenceException exception) {
//        return getModelAndView(request, HttpStatus.BAD_REQUEST, exception);
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ModelAndView entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
//        return getModelAndView(request, HttpStatus.NOT_FOUND, exception);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView internalServerErrorHandler(HttpServletRequest request, Exception exception) {
//        return getModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, exception);
//    }
//
//    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception exception) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (httpStatus.value() == HttpStatus.NOT_FOUND.value()) {
//            modelAndView.setViewName("404");
//        } else if (httpStatus.value() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//            modelAndView.setViewName("500");
//        } else {
//            modelAndView.setViewName("error");
//        }
//        modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
//        modelAndView.addObject("message", exception.getMessage());
//        return modelAndView;
//    }

}