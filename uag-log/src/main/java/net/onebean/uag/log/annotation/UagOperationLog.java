package net.onebean.uag.log.annotation;

import java.lang.annotation.*;



@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Inherited
public @interface UagOperationLog {
    String description() default "";
}
