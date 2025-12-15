package com.enolj.commerce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        products = new ArrayList<>(Arrays.asList(
                new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 10),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 10),
                new Product("MacBook pro", 2400000, "M3 칩셋이 탑재된 노트북", 10),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 10)));

        CommerceSystem commerceSystem = new CommerceSystem(products);
        commerceSystem.start();
    }
}
