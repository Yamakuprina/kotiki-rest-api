package ru.itmo.banks.accounts;

import ru.itmo.banks.BankException;
import ru.itmo.banks.Client;
import ru.itmo.banks.transactions.AddInterestTransaction;
import ru.itmo.banks.transactions.RefillTransaction;
import ru.itmo.banks.transactions.TransferTransaction;
import ru.itmo.banks.transactions.WithdrawTransaction;

import java.util.Date;

public class DepositAccount extends Account {
    public Date ExpirationDate;
    public float depositSum = 0;

    public DepositAccount(Client client, Date expirationDate) {
        super(client);
        ExpirationDate = expirationDate;
        accountType = ACCOUNT_TYPE.DEPOSIT;
    }
    public DepositAccount(DepositAccount account) {
        super(account);
        ExpirationDate = account.ExpirationDate;
    }

    @Override
    public void transfer(float transactionSum, Account destinationAccount, Date dateTime) {
        if (dateTime.before(ExpirationDate)) {
            System.out.println("Cant transfer money before expiration date");
            return;
        }
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
        if (dateTime.before(ExpirationDate)) {
            System.out.println("Cant withdraw money before expiration date");
            return;
        }
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
        if (depositSum == 0) {
            depositSum = transactionSum;
        }
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
