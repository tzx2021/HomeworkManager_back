package vinfer.learnjava.queryhelper.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vinfer
 * @date 2021-02-03    22:38
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(QueryHelperAutoConfiguration.class)
public @interface EnableQueryHelper {
}
