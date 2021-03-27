package sc.hqu.graduationdesign.homeworkmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;

/**
 * 通用的web-mvc接口响应对象
 * @author tzx
 * @date 2021-03-27 14:04
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {

    private static final long serialVersionUID = 1321128103027819310L;

    private int code;

    private String msg;

    private Object data;

    private static final GenericResponse SUCCESS = new GenericResponse(ErrorCode.SUCCESS);

    public GenericResponse(ErrorCode code){
        this(code,null);
    }

    public GenericResponse(ErrorCode code, Object data){
        this.code = code.getCode();
        this.msg = code.getDesc();
        this.data = data;
    }

    public static GenericResponse of(ErrorCode errorCode, Object data){
        return new GenericResponse(errorCode, data);
    }

    public static GenericResponse success(){
        return SUCCESS;
    }

    public static GenericResponse successWithData(Object data){
        return new GenericResponse(ErrorCode.SUCCESS,data);
    }

    public static GenericResponse error(ErrorCode errorCode){
        return new GenericResponse(errorCode);
    }

    public static GenericResponse errorWithData(ErrorCode errorCode, Object data){
        return new GenericResponse(errorCode,data);
    }

}
