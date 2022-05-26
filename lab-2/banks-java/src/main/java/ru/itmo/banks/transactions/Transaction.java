package ru.itmo.banks.transactions;

import ru.itmo.banks.BankException;
import ru.itmo.banks.accounts.Account;

import java.util.Date;
import java.util.UUID;

public abstract class Transaction {
    public final float balanceBeforeTransaction;
    private float balanceAfterTransaction;
    public final String id;
    public final float transactionSum;
    public final Date dateTime;
    public final Account account;
    boolean operationCanceled = false;
    public Transaction(Account account, Date date, float transactionSum) throws BankException {
        if (transactionSum <= 0) throw new BankException("Operation Sum less or equals zero");
        dateTime = date;
        this.transactionSum = transactionSum;
        this.account = account;
        id = UUID.randomUUID().toString();
        balanceBeforeTransaction = this.account.getBalance();
    }

    public abstract void completeOperation() throws BankException;

    public float getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(float balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public abstract void cancelOperation() throws Exception;
}
