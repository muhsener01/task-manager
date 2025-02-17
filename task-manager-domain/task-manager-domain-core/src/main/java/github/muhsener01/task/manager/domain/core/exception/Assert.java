package github.muhsener01.task.manager.domain.core.exception;

public class Assert {


    public static void notNull(Object object , String message){
        if(object == null)
            throw new IllegalArgumentException(message);
    }


}
