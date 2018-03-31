package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abhilashsk on 03/03/18.
 */

public class Validator {
    public boolean isValidName(String name,Context c){
        String PATTERN = "^[A-Za-z]+[A-Za-z]*[A-Za-z]$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(name);
        if(matcher.matches()){
            return true;
        }else{
            Toast.makeText(c,"Invalid Name!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean isValidPhoneNumber(String phone,Context c){
        String PATTERN = "^[789][0-9]{9}$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(phone);
        if(matcher.matches()){
            return true;
        }else{
            Toast.makeText(c,"Invalid Phone Number!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean isValidEmail(String email){
        String PATTERN = "^[a-zA-Z][a-zA-Z0-9\\._]*[@]{1}[a-z]+[\\.]{1}[a-z]{2,}$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isValidUsername(String user,Context c){
        String PATTERN = "^[A-Za-z0-9]+[A-Za-z0-9_]*$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(user);
        if(matcher.matches()){
            return true;
        }else{
            Toast.makeText(c,"Invalid Username!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean isValidPassword(String pass,Context c){
        String PATTERN = "^[A-Za-z0-9$@#]{6,}$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(pass);
        if(matcher.matches()){
            return true;
        }else{
            Toast.makeText(c,"Invalid Password!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean isValidAddress(String address,Context c){
        String PATTERN = "^[A-Za-z]+[A-Za-z, ]*[A-Za-z]$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(address);
        if(matcher.matches()){
            return true;
        }else{
            Toast.makeText(c,"Invalid Address!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean isValidShopID(String shopid,Context c){
        String PATTERN = "^[A-Z]{3}[0-9]{3}$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(shopid);
        if(matcher.matches()){
            return true;
        }else{
            Toast.makeText(c,"Invalid ShopID!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    /*public boolean isValidCategory(String category){
        String PATTERN = "^[A-Za-z]+[A-Za-z]*[A-Za-z]$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(category);
        return matcher.matches();
    }*/
}
