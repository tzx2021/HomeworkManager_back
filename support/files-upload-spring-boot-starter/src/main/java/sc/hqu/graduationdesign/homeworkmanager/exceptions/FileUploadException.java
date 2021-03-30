package sc.hqu.graduationdesign.homeworkmanager.exceptions;

/**
 * @author tzx
 * @date 2021-03-29 18:51
 */
public class FileUploadException extends RuntimeException{

    public FileUploadException(String msg){
        super(msg);
    }

    public FileUploadException(String msg,Exception e){
        super(msg,e);
    }

}
