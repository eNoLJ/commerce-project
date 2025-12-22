package com.enolj.commerce;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommerceSystem {

    public static final String PASSWORD = "123";

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
            printMainMenu();

            int categoryChoice = inputInt(scanner, "메뉴 번호를 입력해 주세요: ");

            if (categoryChoice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                running = false;
                continue;
            }

            if (categoryChoice < 0 || categoryChoice > categories.size()) {
                handleETCChoice(scanner, categoryChoice);
                continue;
            }

            Category category = categories.get(categoryChoice - 1);
            handleCategoryMenu(scanner, category);
        }
    }

    // ===================== 메인 메뉴 관련 =====================

    private void printMainMenu() {
        System.out.println();
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(i + 1 + ". " + categories.get(i).getName());
        }
        System.out.println("0. 종료");

        if (!cart.isEmpty()) {
            printCartMenu();
        }

        System.out.println(categories.size() + 3 + ". 관리자 모드");
    }

    private void printCartMenu() {
        System.out.println();
        System.out.println("[ 주문 관리 ]");
        System.out.println(categories.size() + 1 + ". 장바구니 확인    | 장바구니를 확인 후 주문합니다.");
        System.out.println(categories.size() + 2 + ". 주문 취소       | 진행중인 주문을 취소합니다.");
    }

//     카테고리 범위 밖의 숫자를 입력했을 때 처리
//     - 4: 장바구니 확인/주문
//     - 5: 주문 취소
//     - 그 외: 잘못된 번호

    private void handleETCChoice(Scanner scanner, int categoryChoice) {
        if (!cart.isEmpty() && categoryChoice == categories.size() + 1) {
            handleCartCheckAndOrder(scanner);
        } else if (!cart.isEmpty() && categoryChoice == categories.size() + 2) {
            handleCancelCart();
        } else if (categoryChoice == categories.size() + 3) {
            handleManagerMode(scanner);
        } else {
            System.out.println("올바른 번호를 입력해주세요.");
        }
    }

    // ===================== 장바구니 / 주문 처리 =====================

    private void handleCartCheckAndOrder(Scanner scanner) {
        System.out.println();
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");

        int totalPrice = 0;
        for (Product product : cart.keySet()) {
            int quantity = cart.get(product);
            totalPrice += product.getPrice() * quantity;
            System.out.println(product.printInfo() + " | 수량: " + quantity + "개");
        }

        System.out.println();
        System.out.println("[ 총 주문 금액 ]");
        System.out.println(totalPrice + "원");
        System.out.println();
        System.out.println("1. 주문 확정    2. 주문 삭제    3. 메인으로 돌아가기");

        int num = inputInt(scanner, "");

        if (num != 1 && num != 2 && num != 3) {
            System.out.println("올바른 번호를 입력해주세요.");
            return;
        }

        if (num == 1) {
            printGradeMenu();

            int gradeNum = inputInt(scanner, "");

            if (gradeNum < 0 || gradeNum > 4) {
                System.out.println("올바른 번호를 입력해주세요.");
                return;
            }

            Grade grade = Grade.fromNumber(gradeNum);
            confirmOrder(totalPrice, grade);
        }

        if (num == 2) {
            removeProductToCart(scanner);
        }
    }

    private void printGradeMenu() {
        System.out.println();
        System.out.println("고객 등급을 입력해주세요.");
        System.out.println("1. "+ Grade.BRONZE + " : "+ Grade.BRONZE.getDiscountRate() + "% 할인");
        System.out.println("2. "+ Grade.SILVER + " : "+ Grade.SILVER.getDiscountRate() + "% 할인");
        System.out.println("3. "+ Grade.GOLD + " : "+ Grade.GOLD.getDiscountRate() + "% 할인");
        System.out.println("4. "+ Grade.PLATINUM + " : "+ Grade.PLATINUM.getDiscountRate() + "% 할인");
    }

    private void confirmOrder(int totalPrice,  Grade grade) {
        int discountPrice = totalPrice * grade.getDiscountRate() / 100;

        System.out.println();
        System.out.println("주문이 완료되었습니다!");
        System.out.println("할인 전 금액: " + totalPrice);
        System.out.println(grade.name() + " 등급 할인(" + grade.getDiscountRate() + "%): -" + discountPrice);
        System.out.println("최종 결제 금액: " + (totalPrice - discountPrice));

        for (Product product : cart.keySet()) {
            int quantity = cart.get(product);
            int before = product.getQuantity();
            product.buy(quantity);
            int after = product.getQuantity();
            System.out.println(product.getName() + " 재고가 " + before + "개 -> " + after + "개로 업데이트 되었습니다.");
        }

        cart.clear();
    }

    private void removeProductToCart(Scanner scanner) {
        System.out.println();
        System.out.print("장바구니에서 제거하고 싶은 상품을 입력해주세요: ");
        scanner.nextLine();
        String productName = scanner.nextLine();
        Product targetProduct = null;
        for (Product product : cart.keySet()) {
            if (product.getName().equals(productName)) {
                targetProduct = product;
            }
        }

        if (targetProduct == null) {
            System.out.println("해당 제품이 존재하지 않습니다.");
        } else {
            cart.remove(targetProduct);
            System.out.println("장바구니에서 " + targetProduct.getName() + " 제품이 제거되었습니다.");
        }
    }

    private void handleCancelCart() {
        cart.clear();
        System.out.println("장바구니를 비웠습니다.");
    }

    // ===================== 카테고리 / 상품 선택 =====================

    private void handleCategoryMenu(Scanner scanner, Category category) {
        boolean categoryRunning = true;

        while (categoryRunning) {
            printCategoryMenu(category);

            int menuChoice = inputInt(scanner, "메뉴 번호를 선택하세요: ");

            switch (menuChoice) {
                case 0:
                    categoryRunning = false;
                    break;
                case 1:
                    List<Product> allProducts = category.getProducts();
                    handleProductList(scanner, "[ 전체 상품 목록 ]", allProducts);
                    break;
                case 2:
                    List<Product> underMillionProducts = category.getProducts().stream()
                            .filter(product -> product.getPrice() <= 1000000)
                            .toList();
                    handleProductList(scanner, "[ 100만원 이하 상품 목록 ]", underMillionProducts);
                    break;
                case 3:
                    List<Product> overMillionProducts = category.getProducts().stream()
                            .filter(product -> product.getPrice() > 1000000)
                            .toList();
                    handleProductList(scanner, "[ 100만원 초과 상품 목록 ]", overMillionProducts);
                    break;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
                    break;
            }
        }
    }

    private void printCategoryMenu(Category category) {
        System.out.println();
        System.out.println("[ " + category.getName() + " 카테고리 ]");
        System.out.println("1. 전체 상품 보기");
        System.out.println("2. 가격대별 필터링 (100만원 이하)");
        System.out.println("3. 가격대별 필터링 (100만원 초과)");
        System.out.println("0. 뒤로가기");
    }

    private void handleProductList(Scanner scanner, String title, List<Product> products) {
        if (products.isEmpty()) {
            System.out.println();
            System.out.println("조건에 맞는 상품이 없습니다.");
            return;
        }

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println(title);

            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". "  + products.get(i).printDetailInfo());
            }
            System.out.println("0. 뒤로가기");

            int choice = inputInt(scanner, "상품 번호를 선택하세요: ");

            if (choice == 0) {
                running = false;
            } else if (choice > 0 && choice <= products.size()) {
                Product product = products.get(choice - 1);
                handleAddToCart(scanner, product);
                running = false;
            } else {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    private void handleAddToCart(Scanner scanner, Product product) {
        System.out.println("선택한 상품: " + product.printDetailInfo());
        System.out.println();
        System.out.println(product.printInfo());
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        int num = inputInt(scanner, "");

        if (num != 1 && num != 2) {
            System.out.println("올바른 번호를 입력해주세요.");
            return;
        }

        if (num == 1) {
            addToCart(product);
        }
    }

    private void addToCart(Product product) {
        if (product.getQuantity() <= 0) {
            System.out.println("재고가 부족합니다.");
            return;
        }

        if (cart.containsKey(product)) {
            int current = cart.get(product);
            if (current < product.getQuantity()) {
                cart.compute(product, (k, v) -> v + 1);
                System.out.println(product.getName() + " 제품이 장바구니에 추가 되었습니다.");
            } else {
                System.out.println("재고가 부족합니다.");
            }
        } else {
            cart.put(product, 1);
            System.out.println(product.getName() + " 제품이 장바구니에 추가 되었습니다.");
        }
    }

    // ===================== 관리자 모드 =====================

    private void handleManagerMode(Scanner scanner) {
        int count = 0;
        boolean running = true;
        boolean login = false;

        while (running) {
            System.out.println();
            String password = "";

            if (!login) {
                password = inputPassword(scanner, "관리자 비밀번호를 입력해 주세요:");
            } else {
                password = PASSWORD;
            }

            if (count == 2) {
                System.out.println("비밀번호 3회 오류입니다. 메인화면으로 돌아갑니다.");
                break;
            }

            if (password.equals(PASSWORD)) {
                login = true;
                printManagerMenu();

                int choice = inputInt(scanner, "");

                switch (choice) {
                    case 1:
                        Category category = selectCategory(scanner);
                        if(category == null) {
                            break;
                        }
                        addProductToCategory(scanner, category);
                        break;
                    case 2:
                        editProduct(scanner);
                        break;
                    case 3:
                        removeProduct(scanner);
                        break;
                    case 4:
                        showAllProduct();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("올바른 번호를 입력해주세요.");
                        break;
                }
            } else {
                count++;
            }

        }
    }

    private void printManagerMenu() {
        System.out.println();
        System.out.println("[ 관리자 모드 ]");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 수정");
        System.out.println("3. 상품 삭제");
        System.out.println("4. 전체 상품 현황");
        System.out.println("5. 메인으로 돌아가기");
    }

    private Category selectCategory(Scanner scanner) {
        System.out.println();
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(i + 1 + ". " + categories.get(i).getName());
        }
        int categoryChoice = inputInt(scanner, "");

        if (categoryChoice < 0 || categoryChoice > categories.size()) {
            System.out.println("올바른 번호를 입력해주세요.");
            return null;
        }

        return categories.get(categoryChoice - 1);
    }

    private void addProductToCategory(Scanner scanner, Category category) {
        System.out.println();
        System.out.println("[ " + category.getName() + " 카테고리에 상품 추가 ]");
        System.out.print("상품명을 입력해주세요: ");
        scanner.nextLine();
        String productName = scanner.nextLine();
        try {
            validateProductName(productName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        int price = inputInt(scanner, "가격을 입력해주세요: ");
        System.out.print("상품 설명을 입력해주세요: ");
        scanner.nextLine();
        String productContent = scanner.nextLine();
        int quantity = inputInt(scanner, "재고 수량을 입력해주세요: ");

        System.out.println();
        System.out.println(productName + " | " + price + "원 | " + productContent + " | 재고: " + quantity + "개");
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");

        int num = inputInt(scanner, "");

        if (num != 1 && num != 2) {
            System.out.println("올바른 숫자를 입력해주세요.");
        }

        if (num == 1) {
            Product product = new Product(productName, price, productContent, quantity);
            category.addProduct(product);
            System.out.println();
            System.out.println("상품이 성공적으로 추가되었습니다!");
        }
    }

    private void validateProductName(String productName) {
        for (Category category : categories) {
            for (Product product : category.getProducts()) {
                if (product.getName().equals(productName)) {
                    throw new IllegalArgumentException("중복된 상품명입니다.");
                }
            }
        }
    }

    private void editProduct(Scanner scanner) {
        Product product = getProductToEdit(scanner);

        if (product == null) {
            System.out.println("해당 상품이 없습니다.");
            return;
        }

        printEditItem();

        int choice = inputInt(scanner, "");

        switch (choice) {
            case 1:
                editPriceToProduct(scanner, product);
                break;
            case 2:
                editContentToProduct(scanner, product);
                break;
            case 3:
                editQuantityToProduct(scanner, product);
                break;
            default:
                System.out.println("올바른 번호를 입력해주세요.");
                break;
        }
    }

    private Product getProductToEdit(Scanner scanner) {
        System.out.println();
        System.out.print("수정할 상품명을 입력해주세요: ");
        scanner.nextLine();
        String productName = scanner.nextLine();

        Product product = null;
        for (Category category : categories) {
            for (Product targetProduct : category.getProducts()) {
                if (targetProduct.getName().equals(productName)) {
                    product = targetProduct;
                }
            }
        }

        return product;
    }

    private void printEditItem() {
        System.out.println();
        System.out.println("수정할 항목을 선택해주세요.");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");
    }

    private void editPriceToProduct(Scanner scanner, Product product) {
        System.out.println();
        int currentPrice = product.getPrice();
        System.out.println("현재 가격: " + currentPrice + "원");
        int newPrice = inputInt(scanner, "새로운 가격을 입력해주세요: ");
        product.setPrice(newPrice);
        System.out.println();
        System.out.println(product.getName() + "의 가격이 " + currentPrice + "원 -> " + newPrice + "원으로 수정되었습니다.");
    }

    private void editContentToProduct(Scanner scanner, Product product) {
        System.out.println();
        String currentContent = product.getContent();
        System.out.println("현재 설명: " + currentContent);
        System.out.print("새로운 설명을 입력해주세요: ");
        scanner.nextLine();
        String newContent = scanner.nextLine();
        product.setContent(newContent);
        System.out.println(product.getName() + "의 설명이 " + currentContent + " -> " + newContent + "(으)로 수정되었습니다.");
    }

    private void editQuantityToProduct(Scanner scanner, Product product) {
        System.out.println();
        int currentQuantity = product.getQuantity();
        System.out.println("현재 수량: " + currentQuantity + "개");
        int newQuantity = inputInt(scanner, "새로운 수량을 입력해주세요: ");
        product.setQuantity(newQuantity);
        System.out.println();
        System.out.println(product.getName() + "의 재고수량이 " + currentQuantity + "개 -> " + newQuantity + "개로 수정되었습니다.");
    }

    private void removeProduct(Scanner scanner) {
        System.out.println();
        System.out.print("삭제할 상품명을 입력해주세요: ");
        scanner.nextLine();
        String productName = scanner.nextLine();

        boolean isRemoved = false;
        Category category = null;
        Product product = null;
        for (Category targetCategory : categories) {
            for (Product targetProduct : targetCategory.getProducts()) {
                if (targetProduct.getName().equals(productName)) {
                    category = targetCategory;
                    product = targetProduct;
                    isRemoved = true;
                }
            }
        }

        if (isRemoved) {
            category.removeProduct(product);
            System.out.println(product.getName() + " 제품이 성공적으로 삭제 되었습니다.");
        } else {
            System.out.println("해당 상품이 존재하지 않습니다.");
        }
    }

    private void showAllProduct() {
        System.out.println();
        System.out.println("[ 모든 제품 현황 ]");
        System.out.println();
        for (Category category : categories) {
            category.getProducts()
                    .forEach(product -> System.out.println(product.printDetailInfo()));
        }
    }

    // ===================== 유틸 =====================

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

    private String inputPassword(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.next();
            } catch (InputMismatchException e) {
                System.out.println("비밀번호를 입력해주세요.");
                scanner.nextLine();
            }
        }
    }
}
