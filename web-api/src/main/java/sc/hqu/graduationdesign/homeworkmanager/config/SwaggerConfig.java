package sc.hqu.graduationdesign.homeworkmanager.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzx
 * @date 2021-04-09 15:57
 */
@Setter
@EnableKnife4j
@EnableSwagger2
@Profile({"dev","default"})
@Configuration
public class SwaggerConfig {

    private boolean open = true;

    private boolean required = true;

    private String name = "Authorization";

    private String description = "token";

    private String defaultValue = "token";

    private String docVersion = "1.0.0";

    private String docTitle = "作业推送管理系统-教师端 API文档";

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<Parameter>();
        if (open) {
            ParameterBuilder ticketPar = new ParameterBuilder();
            ticketPar.name(name)
                    .description(description)
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(required)
                    .defaultValue(defaultValue)
                    .build();
            pars.add(ticketPar.build());
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .groupName("HomeworkManager v1.0.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("sc.hqu.graduationdesign.homeworkmanager.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(docTitle)
                .version(docVersion)
                .build();
    }

}
