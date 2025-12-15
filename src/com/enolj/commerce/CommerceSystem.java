package com.enolj.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Product> products;

    public CommerceSystem(List<Product> products) {
        this.products = products;
    }

    public void start() {
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
