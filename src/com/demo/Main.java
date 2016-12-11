package com.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите полный адресс сайта");
        String url = scanner.next();

        URI base = new URI(url);

        HashSet<String> hashSet = new HashSet<>();
        String result;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            result = br.lines().collect(Collectors.joining("\n"));
        }

        Pattern pattern = Pattern.compile("(?<=<img\\s?\\S?\\s?src\\s?=\\s?\").*?(?=\")");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            if (matcher.group().matches("^(../+)?/?.+")) {
                URI res = base.resolve(matcher.group());
                hashSet.add(String.valueOf(res));
                continue;
            }
            hashSet.add(matcher.group());
        }
        if (!hashSet.isEmpty()){
            hashSet.forEach(System.out::println);
        }else {
            System.out.println("Изображений не найдено");
        }

    }
}