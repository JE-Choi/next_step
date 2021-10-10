package webserver.servlet.annotation;

import webserver.request.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RequestMappings.class)
public @interface RequestMapping {
    String value() default "";
    HttpMethod method() default HttpMethod.NOT_FOUND;
}
