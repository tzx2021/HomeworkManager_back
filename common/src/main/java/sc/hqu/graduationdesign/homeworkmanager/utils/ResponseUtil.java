package sc.hqu.graduationdesign.homeworkmanager.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author tzx
 * @date 2021-03-27 14:01
 */
public class ResponseUtil {

    public static void writeJson(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSON.toJSONString(GenericResponse.error(errorCode)));
    }

    public static void writeJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSON.toJSONString(data));
    }

}
