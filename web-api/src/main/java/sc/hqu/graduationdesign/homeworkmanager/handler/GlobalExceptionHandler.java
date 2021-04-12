package sc.hqu.graduationdesign.homeworkmanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.exception.ApiException;
import sc.hqu.graduationdesign.homeworkmanager.exception.BusinessException;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;

/**
 * 全局异常统一处理
 * @author tzx
 * @date 2021-04-07 21:25
 */
@Component
public class GlobalExceptionHandler {


    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {BusinessException.class, FileUploadException.class})
    public GenericResponse handleBusinessException(Exception e){
        logger.error("异常服务处理: {}",e.getMessage());
        return GenericResponse.error(ErrorCode.SERVICE_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public GenericResponse handleRuntimeException(Exception e){
        logger.error("运行时异常处理: {}",e.getMessage());
        return GenericResponse.error(ErrorCode.SERVICE_ERROR);
    }

    @ExceptionHandler(value = {ApiException.class})
    public GenericResponse handleApiBusinessException(Exception e){
        logger.error("异常api处理: {}",e.getMessage());
        return GenericResponse.error(ErrorCode.API_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class,IllegalArgumentException.class})
    public GenericResponse handleArgumentException(Exception e){
        logger.error("参数异常处理: {}",e.getMessage());
        return GenericResponse.error(ErrorCode.ARGUMENT_ERROR);
    }


}
