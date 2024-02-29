package br.com.willian.clientes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelefoneUtil {

    private static final String REGEX_TELEFONE = "^\\(\\d{2}\\)\\s?\\d{4}-\\d{4}|\\(\\d{2}\\)\\s?\\d{5}-\\d{4}|\\d{10,11}$";

    public static String extrairNumero(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return null;
        }
        if (!Pattern.compile(REGEX_TELEFONE).matcher(telefone).matches()) {
            return null;
        }
        Matcher matcher = Pattern.compile(REGEX_TELEFONE).matcher(telefone);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static String extrairNumeros(String phoneNumber) {
        String regex = "\\d+";
        Matcher matcher = Pattern.compile(regex).matcher(phoneNumber);
        StringBuffer numbers = new StringBuffer();
        while (matcher.find()) {
            numbers.append(matcher.group());
        }
        return numbers.toString();
    }
}
