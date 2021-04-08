package vinfer.learnjava.queryhelper.core;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import vinfer.learnjava.queryhelper.annotation.QueryHelper;
import vinfer.learnjava.queryhelper.constant.InterceptMode;
import vinfer.learnjava.queryhelper.core.processor.InterceptionPostProcessor;
import vinfer.learnjava.queryhelper.core.processor.PaginationQueryPostProcessor;
import vinfer.learnjava.queryhelper.exception.ProcessorNotFoundException;
import vinfer.learnjava.queryhelper.exception.ProcessorTypeNotMatchException;
import vinfer.learnjava.queryhelper.support.CommonMapper;
import vinfer.learnjava.queryhelper.support.Mapper;
import vinfer.learnjava.queryhelper.support.PaginationQueryDataTransporter;

/**
 * @author Vinfer
 * @date 2021-02-04    10:02
 **/
@Getter
public class GenericQueryHelperDefinition implements QueryHelperDefinition{

    /**当前的queryHelper是否用于做分页查询*/
    private final boolean paginationQuery;

    /**分页查询模式*/
    private final InterceptMode interceptMode;

    /**拦截后置处理器*/
    private final InterceptionPostProcessor interceptionPostProcessor;

    /**封装查询方法所在的dao接口的元数据信息的对象*/
    private final Mapper mapper;

    public GenericQueryHelperDefinition(Class<?> mapperClass, QueryHelper queryHelper, BeanFactory beanFactory){
        this.interceptMode = queryHelper.interceptMode();
        Class<?> processor = queryHelper.processor();
        this.paginationQuery = queryHelper.isForPaginationQuery();
        if (processor.isAssignableFrom(PaginationQueryPostProcessor.class)){
            //使用的默认处理器，该处理器不会被注册到ioc容器中
            this.interceptionPostProcessor = new PaginationQueryPostProcessor();
        }else {
            //如果没有使用默认处理器，那么到bf中查询用户自定义的处理器
            try {
                Object customInterceptionPostProcessor = beanFactory.getBean(processor);
                Class<?> type = customInterceptionPostProcessor.getClass();
                //自定义的拦截器处理器必须实现了InterceptionPostProcessor
                if (InterceptionPostProcessor.class.isAssignableFrom(type)){
                    this.interceptionPostProcessor = (InterceptionPostProcessor) customInterceptionPostProcessor;
                }else {
                    throw new ProcessorTypeNotMatchException("Required a Processor type of me.learnjava.queryhelper.core.processor.InterceptionPostProcessor " +
                            "but found: " + type.getName());
                }
            }catch (BeansException | ProcessorTypeNotMatchException ex){
                //触发异常需要清除ThreadLocal缓存
                PaginationQueryDataTransporter.remove();
                throw new ProcessorNotFoundException("Can not find a custom interception postProcessor in current bean factory!", ex);
            }
        }
        this.mapper = new CommonMapper(mapperClass);
    }
}
