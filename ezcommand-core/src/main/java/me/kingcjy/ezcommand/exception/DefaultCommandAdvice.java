package me.kingcjy.ezcommand.exception;

import me.kingcjy.ezcommand.annotations.CommandAdvice;
import me.kingcjy.ezcommand.annotations.ExceptionHandler;
import me.kingcjy.ezcommand.annotations.Order;

@Order(999)
@CommandAdvice
public class DefaultCommandAdvice {

    @ExceptionHandler(RuntimeException.class)
    public void HandleRuntimeException(RuntimeException e) {
        e.printStackTrace();
    }
}