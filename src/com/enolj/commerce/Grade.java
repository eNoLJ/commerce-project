package com.enolj.commerce;

public enum Grade {
    BRONZE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15);

    private int discountRate;

    Grade(int discountRate) {
        this.discountRate = discountRate;
    }

    public static Grade fromNumber(int number) {
        return switch (number) {
            case 1 -> BRONZE;
            case 2 -> SILVER;
            case 3 -> GOLD;
            case 4 -> PLATINUM;
            default -> throw new IllegalArgumentException("해당 등급은 존재하지 않습니다.");
        };
    }

    public int getDiscountRate() {
        return discountRate;
    }
}
