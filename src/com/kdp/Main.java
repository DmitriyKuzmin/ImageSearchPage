package com.kdp;

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

    private static final String IMG_TAG_PATTERN = "(?<=<img\\s?\\S?\\s?src\\s?=\\s?\").*?(?=\")";
    private static final String IMG_PATTERN = "^(../+)?/?.+";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input URL address: ");
        String url = scanner.next();

        URI base = new URI(url);

        HashSet<String> hashSet = new HashSet<>();
        String result;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            result = br.lines().collect(Collectors.joining("\n"));
        }

        Pattern pattern = Pattern.compile(IMG_TAG_PATTERN);
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            if (matcher.group().matches(IMG_PATTERN)) {
                URI res = base.resolve(matcher.group());
                hashSet.add(String.valueOf(res));
                continue;
            }
            hashSet.add(matcher.group());
        }

        if (!hashSet.isEmpty())
            hashSet.forEach(System.out::println);
        else
            System.out.println("Not images");

    }
}