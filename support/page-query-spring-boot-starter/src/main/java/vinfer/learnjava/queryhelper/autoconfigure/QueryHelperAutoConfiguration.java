package vinfer.learnjava.queryhelper.autoconfigure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import vinfer.learnjava.queryhelper.core.QueryHelperDefinitionsMapHolder;
import vinfer.learnjava.queryhelper.core.QueryInterceptor;
import vinfer.learnjava.queryhelper.core.ResultInterceptor;
import vinfer.learnjava.queryhelper.core.processor.QueryHelperProcessor;

/**
 * @author Vinfer
 * @date 2021-02-03    22:37
 **/
@Configuration
@ConfigurationProperties(prefix = "query.helper")
public class QueryHelperAutoConfiguration {

    private boolean showModifySql = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryHelperAutoConfiguration.class);

    public QueryHelperAutoConfiguration(){
        LOGGER.info("Enable mybatis-query-helper-plugins");
    }

    @Bean
    public QueryHelperProcessor pageHelperProcessor(){
        return new QueryHelperProcessor();
    }

    @Bean
    public QueryHelperDefinitionsMapHolder queryHelperDefinitionsMapHolder(){
        return new QueryHelperDefinitionsMapHolder();
    }

    @Bean
    @DependsOn("queryHelperDefinitionsMapHolder")
    public QueryInterceptor queryInterceptor(){
        return new QueryInterceptor(queryHelperDefinitionsMapHolder());
    }

    @Bean
    @DependsOn("queryHelperDefinitionsMapHolder")
    public ResultInterceptor resultInterceptor(){
        return new ResultInterceptor(queryHelperDefinitionsMapHolder());
    }

    public void setShowModifySql(boolean showModifySql){
        this.showModifySql = showModifySql;
    }

    public boolean isShowModifySql(){
        return this.showModifySql;
    }
}
