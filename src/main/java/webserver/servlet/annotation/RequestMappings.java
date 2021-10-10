package webserver.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨테이너 어노테이션: 실 사용할 어노테이션과 그 어노테이션 묶음 값을 관리
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMappings {
    RequestMapping[] value();
}
