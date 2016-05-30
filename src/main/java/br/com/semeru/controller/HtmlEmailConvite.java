package br.com.semeru.controller;

import java.lang.*;

public class HtmlEmailConvite {
    
    

    public HtmlEmailConvite() {
    }
    
    public static void main(String[] args) {
        // Declare the source string
        String stringValue = "102345000";
        // Define the regex pattern
        String regex = "0";
        // Replacing all the occurrences of String "0"
        String objString = stringValue.replaceAll("0", "**");
        System.out.println("New String Object: "+objString);
    }    
    
}
