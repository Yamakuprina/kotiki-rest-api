package ru.itmo.banks.accounts;

import ru.itmo.banks.BankException;
import ru.itmo.banks.Client;
import ru.itmo.banks.transactions.AddInterestTransaction;
import ru.itmo.banks.transactions.RefillTransaction;
import ru.itmo.banks.transactions.TransferTransaction;
import ru.itmo.banks.transactions.WithdrawTransaction;

import java.util.Date;

public class DebitAccount extends Account {
    public DebitAccount(Client client) {
        super(client);
        accountType = ACCOUNT_TYPE.DEBIT;
    }

    public DebitAccount(Account account) {
        super(account);
    }

    @Override
    public void transfer(float transactionSum, Account destinationAccount, Date dateTime) {
        if (getBalance() < transactionSum) {
            System.out.println("Cant complete operation: Not enough money");
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
        if (getBalance() < transactionSum) {
            System.out.println("Cant complete operation: Not enough money");
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
    }

    @Override
    public void addInterest(float interestSum, Date dateTime) {
        try {
            var transaction = new AddInterestTransaction(this, dateTime, interestSum);
            transaction.completeOperation();
            history.add(transaction);
        } catch (BankException e) {
            System.out.println("Cant complete operation:");
            System.out.println(e.getMessage());
        }
    }
}
