package com.enolj.commerce;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommerceSystem {

    private List<Customer> customers;

    private List<Category> categories;

    private Map<Product, Integer> cart;

    public CommerceSystem(List<Customer> customers, List<Category> categories, Map<Product, Integer> cart) {
        this.customers = customers;
        this.categories = categories;
        this.cart = cart;
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

            if (!cart.isEmpty()) {
                System.out.println();
                System.out.println("[ 주문 관리 ]");
                System.out.println("4. 장바구니 확인    | 장바구니를 확인 후 주문합니다.");
                System.out.println("5. 주문 취소       | 진행중인 주문을 취소합니다.");
            }

            int categoryChoice = inputInt(scanner, "메뉴 번호를 입력해 주세요: ");
            if (categoryChoice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                running = false;
                continue;
            }

            if (categoryChoice < 0 || categoryChoice > categories.size()) {
                if (!cart.isEmpty() && categoryChoice == 4) {
                    System.out.println();
                    System.out.println("아래와 같이 주문 하시겠습니까?");
                    System.out.println();
                    System.out.println("[ 장바구니 내역 ]");
                    int totalPrice = 0;
                    for (Product product : cart.keySet()) {
                        totalPrice += product.getPrice() * cart.get(product);
                        System.out.println(product.printInfo() + " | 수량: " + cart.get(product) + "개");
                    }
                    System.out.println();
                    System.out.println("[ 총 주문 금액 ]");
                    System.out.println(totalPrice + "원");
                    System.out.println();
                    System.out.println("1. 주문 확정     2. 메인으로 돌아가기");

                    int num = inputInt(scanner, "");

                    if (num != 1 && num != 2) {
                        System.out.println("올바른 번호를 입력해주세요.");
                    }

                    if (num == 1) {
                        System.out.println();
                        System.out.println("주문이 완료되었습니다! 총 금액: " + totalPrice + "원");
                        for (Product product : cart.keySet()) {
                            product.buy(cart.get(product));
                            System.out.println(product.getName() + " 재고가 " + (product.getQuantity() + cart.get(product)) + "개 -> " + product.getQuantity() + "개로 업데이트 되었습니다.");
                        }
                        cart.clear();
                    }

                } else if (!cart.isEmpty() && categoryChoice == 5) {
                    cart.clear();
                    System.out.println("장바구니를 비웠습니다.");
                } else {
                    System.out.println("올바른 번호를 입력해주세요.");
                }
                continue;
            }

            Category category = categories.get(categoryChoice - 1);
            boolean categoryRunning = true;

            while (categoryRunning) {
                System.out.println();
                System.out.println("[ "  + category.getName() + " 카테고리 ]");
                for (int i = 0; i < category.productSize(); i++) {
                    Product product = category.getProduct(i);
                    System.out.println(i + 1 + ". " + product.printDetailInfo());
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
                System.out.println("선택한 상품: " + product.printDetailInfo());
                System.out.println();

                System.out.println(product.printInfo());
                System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인      2. 취소");
                int num = inputInt(scanner, "");

                if (num != 1 && num != 2) {
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }

                if (num == 1) {
                    if (product.getQuantity() > 0) {
                        if (cart.containsKey(product)) {
                            if (cart.get(product) < product.getQuantity()) {
                                cart.compute(product, (k, i) -> i + 1);
                                System.out.println(product.getName() + "(이)가 장바구니에 추가 되었습니다.");
                            } else {
                                System.out.println("재고가 부족합니다.");
                            }
                            break;
                        } else {
                            cart.put(product, 1);
                            System.out.println(product.getName() + "(이)가 장바구니에 추가 되었습니다.");
                            break;
                        }
                    } else {
                        System.out.println("재고가 부족합니다.");
                        break;
                    }
                }
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
