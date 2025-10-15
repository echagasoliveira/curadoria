package br.com.curadoria.adapter.http.entrypoint.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenhaForteValidator implements ConstraintValidator<SenhaForte, String> {

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        boolean isSenhaForteValida = false;
        if (senha != null && !senha.isEmpty()) {
            //String expression = "/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$/";
            //String expression = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])(?:([0-9a-zA-Z$*&@#])(?!\\1)){8,}$";
            String expression = "^(?=.*[A-Z])(?=.*[!#@$%&*-_])(?=.*[0-9])(?=.*[a-z]).{8,20}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(senha);
            if (matcher.matches())
                isSenhaForteValida = true;
        }
        return isSenhaForteValida;
    }
}