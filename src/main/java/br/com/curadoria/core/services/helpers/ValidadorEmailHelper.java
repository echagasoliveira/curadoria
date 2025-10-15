package br.com.curadoria.core.services.helpers;

public class ValidadorEmailHelper {
    public static boolean validaEmail(String email) {
        if (email == null || email.isEmpty()) return false;

        // Expressão regular básica para validar e-mail
        String regex = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
}
