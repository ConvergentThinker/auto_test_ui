package org.autotestui.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author Sakthivel I.
 * @since 01 Sep 2022
 */

@Autowired
@Lazy
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Page {
    String value() default "";
}
