package sc.hqu.graduationdesign.homeworkmanager.exception;

import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;

/**
 * @author tzx
 * @date 2021-03-18 0:56
 */
public class BusinessException extends Exception{

    public BusinessException(ErrorCode errorCode,Exception e){
        super(errorCode.getDesc(),e);
    }

}
