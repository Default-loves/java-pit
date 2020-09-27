package com.junyi.advancedfeatures.annotationinheritance;

import java.lang.annotation.*;

/**
 * \@Inherited mean subClass will have the same annotation from parentClass
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyAnnotation {
    String value();
}
