package sc.hqu.graduationdesign.homeworkmanager.exception;

/**
 * @author tzx
 * @date 2021-04-07 21:32
 */
public class ApiException extends Exception{

    public ApiException(String msg){
        super(msg);
    }

    public ApiException(String msg,Exception e){
        super(msg,e);
    }

}
