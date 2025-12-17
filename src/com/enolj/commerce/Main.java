package com.enolj.commerce;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Product> electronics = new ArrayList<>(Arrays.asList(
                new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 2),
                new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 10),
                new Product("MacBook pro", 2400000, "M3 칩셋이 탑재된 노트북", 10),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 10)
        ));

        List<Product> clothes = new ArrayList<>(Arrays.asList(
                new Product("롱패딩", 400000, "길고 따듯한 롱패딩", 10),
                new Product("슬랙스", 80000, "깔끔한 핏의 슬랙스", 10)
        ));

        List<Product> food = new ArrayList<>(Arrays.asList(
                new Product("김치찌개", 10000, "고기듬뿍 김치찌개", 10),
                new Product("떡볶이", 12000, "매콤한 떡볶이", 10)
        ));

        List<Category> categories = new ArrayList<>(Arrays.asList(
           new Category("전자제품", electronics),
           new Category("의류", clothes),
           new Category("식품", food)
        ));

        List<Customer> customers = new ArrayList<>(Arrays.asList(
           new Customer("홍길동", "gildong@gamil.com", "일반"),
           new Customer("정인호", "enolj@gamil.com", "vip")
        ));

        Map<Product, Integer> cart = new HashMap<>();

        CommerceSystem commerceSystem = new CommerceSystem(customers, categories, cart);
        commerceSystem.start();
    }
}
