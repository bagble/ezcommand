package me.kingcjy.ezcommand.exception;

import me.kingcjy.ezcommand.EzLogger;
import me.kingcjy.ezcommand.annotations.CommandAdvice;
import me.kingcjy.ezcommand.annotations.ExceptionHandler;
import me.kingcjy.ezcommand.annotations.Order;
import me.kingcjy.ezcommand.command.RootCommandArgument;

import java.util.logging.Level;
import java.util.logging.Logger;

@Order(Order.LOW)
@CommandAdvice
public class DefaultCommandAdvice {

    private final Logger logger = EzLogger.getLogger(DefaultCommandAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public void HandleRuntimeException(RuntimeException e, RootCommandArgument commandArgument) {
        logger.log(Level.INFO, "KingCjy!@#!@#!@#");
        logger.log(Level.INFO, e.getMessage());
        logger.log(Level.INFO, commandArgument.toString());
    }
}
