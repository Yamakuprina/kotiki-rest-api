package ru.itmo.banks.accounts;

import ru.itmo.banks.BankException;
import ru.itmo.banks.Client;
import ru.itmo.banks.ItmoBank;
import ru.itmo.banks.transactions.RefillTransaction;
import ru.itmo.banks.transactions.SubtractCommission;
import ru.itmo.banks.transactions.TransferTransaction;
import ru.itmo.banks.transactions.WithdrawTransaction;

import java.util.Date;

public class CreditAccount extends Account {
    public CreditAccount(Client client) {
        super(client);
        accountType = ACCOUNT_TYPE.CREDIT;
    }

    public CreditAccount(Account account) {
        super(account);
    }

    @Override
    public void transfer(float transactionSum, Account destinationAccount, Date dateTime) {
        if (getBalance() - transactionSum + ItmoBank.getBelowZeroLimit() < 0) {
            System.out.println("Credit limit reached, cant complete operation");
            return;
        }
        try {
            var transaction = new TransferTransaction(this, dateTime, transactionSum, destinationAccount);
            transaction.completeOperation();
            history.add(transaction);
        } catch (BankException e) {
            System.out.println("Cant complete operation:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void withdrawal(float transactionSum, Date dateTime) {
        if (getBalance() - transactionSum + ItmoBank.getBelowZeroLimit() < 0) {
            System.out.println("Credit limit reached, cant complete operation");
            return;
        }
        try {
            var transaction = new WithdrawTransaction(this, dateTime, transactionSum);
            transaction.completeOperation();
            history.add(transaction);
        } catch (BankException e) {
            System.out.println("Cant complete operation:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void refill(float transactionSum, Date dateTime) {
        try {
            var transaction = new RefillTransaction(this, dateTime, transactionSum);
            transaction.completeOperation();
            history.add(transaction);
        } catch (BankException e) {
            System.out.println("Cant complete operation:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void subtractCommissions(float commissionSum, Date dateTime) {
        try {
            var transaction = new SubtractCommission(this, dateTime, commissionSum);
            transaction.completeOperation();
            history.add(transaction);
        } catch (BankException e) {
            System.out.println("Cant complete operation:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addInterest(float interestSum, Date dateTime) {
    }
}
