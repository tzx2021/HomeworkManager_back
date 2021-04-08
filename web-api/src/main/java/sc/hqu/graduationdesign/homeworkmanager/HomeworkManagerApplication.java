package sc.hqu.graduationdesign.homeworkmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vinfer.learnjava.queryhelper.autoconfigure.EnableQueryHelper;

/**
 * 程序主启动类
 * @author tzx
 * @date 2021-03-12 15:58
 */
@EnableQueryHelper
@SpringBootApplication
@MapperScan("sc.hqu.graduationdesign.homeworkmanager.mapper")
public class HomeworkManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }

}
