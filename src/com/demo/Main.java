package com.demo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://rugby-penza.ru/");

        String result;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            result = br.lines().collect(Collectors.joining("\n"));
        }
            Pattern pattern = Pattern.compile("(?<=(img) src\\s?=\\s?\").*?(?=\")");
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
    }
}
