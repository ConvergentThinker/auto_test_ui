package org.autotestui.annotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author Sakthivel I.
 * @since 01 Sep 2022
 */

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface PageObject {
    String value() default "";
}
