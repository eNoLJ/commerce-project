package com.enolj.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        Product product = new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 10);
        Product product2 = new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 10);
        Product product3 = new Product("MacBook pro", 2400000, "M3 칩셋이 탑재된 노트북", 10);
        Product product4 = new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 10);

        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
            for (int i = 0; i<products.size(); i++) {
                System.out.println(i + 1 + ". " + products.get(i).printProduct());
            }
            System.out.println("0. 종료");

            String choice = scanner.next();
            if (choice.equals("0")) {
                running = false;
            }
        }
    }
}
