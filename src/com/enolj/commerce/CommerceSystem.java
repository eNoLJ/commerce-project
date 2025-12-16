package com.enolj.commerce;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Customer> customers;

    private List<Category> categories;

    public CommerceSystem(List<Customer> customers, List<Category> categories) {
        this.customers = customers;
        this.categories = categories;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
            for (int i = 0; i< categories.size(); i++) {
                System.out.println(i + 1 + ". " + categories.get(i).getName());
            }
            System.out.println("0. 종료");

            int categoryChoice = inputInt(scanner, "메뉴 번호를 입력해 주세요: ");
            if (categoryChoice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                running = false;
                break;
            }

            if (categoryChoice < 0 || categoryChoice > categories.size()) {
                System.out.println("올바른 번호를 입력해주세요.");
                continue;
            }

            Category category = categories.get(categoryChoice - 1);
            boolean categoryRunning = true;

            while (categoryRunning) {
                System.out.println();
                System.out.println("[ "  + category.getName() + " 카테고리 ]");
                for (int i = 0; i < category.productSize(); i++) {
                    Product product = category.getProduct(i);
                    System.out.println(i + 1 + ". " + product.printProduct());
                }
                System.out.println("0. 뒤로가기");

                int productChoice = inputInt(scanner, "상품 번호를 선택하세요: ");
                if (productChoice == 0) {
                    categoryRunning = false;
                    continue;
                }

                if (productChoice < 0 || productChoice > category.productSize()) {
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }

                Product product = category.getProduct(productChoice - 1);
                System.out.println("선택한 상품: " + product.printDetailProduct());
            }
        }
    }

    private int inputInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요.");
                scanner.nextLine();
            }
        }
    }
 }
