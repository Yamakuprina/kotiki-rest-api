package ru.itmo.banks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CentralBank {
    private final List<Bank> _banks;

    public CentralBank() {
        _banks = new ArrayList<Bank>();
    }

    public void notify(Date dateTime) {
        for (Bank bank : _banks) {
            bank.payInterestsAndCommissions(dateTime);
        }
    }

    public Bank registerBank(String name, float debitPercent, int unverifiedLimit, int creditBelowZeroLimit, float creditCommission, ArrayList<InterestRate> interestRates) {
        var bank = new ItmoBank(name, debitPercent, unverifiedLimit, creditBelowZeroLimit, creditCommission, interestRates);
        _banks.add(bank);
        return bank;
    }
}
