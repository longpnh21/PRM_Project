package com.example.projectrestaurant.utils;

public class EmailValidation {
    private static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}