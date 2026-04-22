package pl.edu.uj.tp.nexo.exception.annotation;

import pl.edu.uj.tp.nexo.exception.ErrorInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrors {
    ErrorInfo[] value();
}
