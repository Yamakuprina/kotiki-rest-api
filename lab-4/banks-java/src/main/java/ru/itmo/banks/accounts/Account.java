package ru.itmo.banks.accounts;

import ru.itmo.banks.Client;
import ru.itmo.banks.transactions.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class Account {
    private final Client client;
    private final String id;
    public List<Transaction> history;
    public ACCOUNT_TYPE accountType;
    private boolean clientSubscribed = false;
    private float balance;
    private Date lastDayInterestsCommissionPaid;
    private float tempInterestCommissionSum;
    public Account(Client client) {
        this.client = client;
        id = UUID.randomUUID().toString();
        history = new ArrayList<Transaction>();
    }

    public Account(Account account) {
        client = account.client;
        id = account.id;
        history = account.history;
        balance = account.balance;
        lastDayInterestsCommissionPaid = account.lastDayInterestsCommissionPaid;
        tempInterestCommissionSum = account.tempInterestCommissionSum;
        accountType = account.accountType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getLastDayInterestsCommissionPaid() {
        if (lastDayInterestsCommissionPaid == null) {
            lastDayInterestsCommissionPaid = history.get(0).dateTime;
        }
        return lastDayInterestsCommissionPaid;
    }

    public void setLastDayInterestsCommissionPaid(Date lastDayInterestsCommissionPaid) {
        this.lastDayInterestsCommissionPaid = lastDayInterestsCommissionPaid;
    }

    public float getTempInterestCommissionSum() {
        return tempInterestCommissionSum;
    }

    public void setTempInterestCommissionSum(float tempInterestCommissionSum) {
        this.tempInterestCommissionSum = tempInterestCommissionSum;
    }

    public abstract void transfer(float transactionSum, Account destinationAccount, Date dateTime);

    public abstract void withdrawal(float transactionSum, Date dateTime);

    public abstract void refill(float transactionSum, Date dateTime);

    public abstract void subtractCommissions(float commissionSum, Date dateTime);

    public abstract void addInterest(float interestSum, Date dateTime);

    public void undoTransaction(String transactionId) {
        Transaction transaction = history.stream().filter(transaction1 -> transaction1.id == transactionId).findFirst().get();
        try {
            transaction.cancelOperation();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        history.remove(transaction);
    }

    public void subscribeAccount() {
        clientSubscribed = true;
    }

    public void unsubscribeAccount() {
        clientSubscribed = false;
    }

    public String getClientNameSurname() {
        return client.getNameSurname();
    }

    public ru.itmo.banks.Client getClient() {
        return client;
    }

    public Transaction getLastTransactionInDay(Date date) {
        Stream<Transaction> TransactionsBeforeCurrentDate = history.stream().filter(transaction -> transaction.dateTime.before(date));
        return TransactionsBeforeCurrentDate.max((t1, t2) -> t1.dateTime.compareTo(t2.dateTime)).orElse(null);
    }
}
