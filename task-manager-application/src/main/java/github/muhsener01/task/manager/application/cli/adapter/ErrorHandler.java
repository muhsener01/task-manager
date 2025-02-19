package github.muhsener01.task.manager.application.cli.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ErrorHandler  {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    protected void logError(Exception e){
        log.error("[{}] {}" , e.getClass().getSimpleName(), e.getMessage());
        log.debug("Exception Stack Trace" , e);
    }
}
