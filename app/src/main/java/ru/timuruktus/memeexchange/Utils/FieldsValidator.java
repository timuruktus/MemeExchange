package ru.timuruktus.memeexchange.Utils;

public class FieldsValidator{


    public static boolean isStringEmpty(String string){
        return string == null || string.isEmpty() || string.length() == 0 || string.equals("");
    }

    public static boolean isJoinPasswordValid(String password){
        return !isStringEmpty(password) && password.length() >= 6;
    }

    public static boolean isRegisterPasswordValid(String password){
        return isJoinPasswordValid(password) && !password.contains(" ") && onlyLatinAlphabet(password);
    }

    public static boolean isRegisterEmailValid(String email){
        return !isStringEmpty(email) && !email.contains(" ") && email.contains("@") && email.contains(".");
    }

    public static boolean isRegisterLoginValid(String login){
        return !isStringEmpty(login) && !login.contains(" ") && onlyLatinAlphabet(login) && !login.contains("-");
    }

    public static boolean onlyLatinAlphabet(String string){
       return string.matches("^[a-zA-Z0-9]+$");
    }
}
