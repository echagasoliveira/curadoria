package br.com.curadoria.adapter.http.entrypoint.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "{endereco-eletronico.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}