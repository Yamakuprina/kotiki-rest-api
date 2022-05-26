package ru.itmo.banks;

public class InterestRate { // Pair of numbers. Limit and percent after limit reached
    public final int limit;
    public final float percent;
    public InterestRate(int limit, float percent) {
        this.limit = limit;
        this.percent = percent;
    }
}
