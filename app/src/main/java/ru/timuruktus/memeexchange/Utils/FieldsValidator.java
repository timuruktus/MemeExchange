package ru.timuruktus.memeexchange.Utils;

public class FieldsValidator{


    public static boolean isStringEmpty(String string){
        return string == null || string.isEmpty() || string.length() == 0 || string.equals("");
    }

    public static boolean isJoinPasswordValid(String password){
        return !isStringEmpty(password) && password.length() >= 6;
    }

    public static boolean isRegisterPasswordValid(String password){
        boolean onlyLatinAlphabet = password.matches("^[a-zA-Z0-9]+$");
        return isJoinPasswordValid(password) && !password.contains(" ") && onlyLatinAlphabet;
    }
}
