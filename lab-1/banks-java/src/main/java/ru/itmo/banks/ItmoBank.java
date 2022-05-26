package ru.itmo.banks;

import ru.itmo.banks.accounts.*;
import ru.itmo.banks.transactions.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ItmoBank implements Bank {
    private static float unverifiedLimit; //Positive number
    private static float belowZeroLimit; //Positive number
    private final String id;
    private final String name;
    private float debitPercent;
    private float creditCommission; //Commission subtracts daily
    private final List<Account> accounts;
    private List<InterestRate> interestRates;
    public ItmoBank(String name, float debitPercent, float unverifiedLimit, float creditBelowZeroLimit, float creditCommission, ArrayList<InterestRate> interestRates) {
        accounts = new ArrayList<Account>();
        id = UUID.randomUUID().toString();
        this.name = name;
        this.debitPercent = debitPercent;
        ItmoBank.unverifiedLimit = unverifiedLimit;
        belowZeroLimit = creditBelowZeroLimit;
        this.creditCommission = creditCommission;
        this.interestRates = interestRates;
    }

    public static float getUnverifiedLimit() {
        return unverifiedLimit;
    }

    public static float getBelowZeroLimit() {
        return belowZeroLimit;
    }

    public DebitAccount addNewDebitAccount(Client client) {
        var account = new DebitAccount(client);
        accounts.add(account);
        return account;
    }

    public DepositAccount addNewDepositAccount(Client client, Date depositExpirationDate) {
        var account = new DepositAccount(client, depositExpirationDate);
        accounts.add(account);
        return account;
    }

    public CreditAccount addNewCreditAccount(Client client) {
        var account = new CreditAccount(client);
        accounts.add(account);
        return account;
    }

    public void subscribeClient(Account account) {
        account.subscribeAccount();
    }

    public void notifySubscribers(ACCOUNT_TYPE accountType, String message) {
        switch (accountType) {
            case ANY -> accounts.forEach(account -> account.getClient().getUpdate(message));
            case DEBIT -> accounts.stream().filter(account -> account.accountType.equals(ACCOUNT_TYPE.DEBIT)).forEach(account -> account.getClient().getUpdate(message));
            case DEPOSIT -> accounts.stream().filter(account -> account.accountType.equals(ACCOUNT_TYPE.DEPOSIT)).forEach(account -> account.getClient().getUpdate(message));
            case CREDIT -> accounts.stream().filter(account -> account.accountType.equals(ACCOUNT_TYPE.CREDIT)).forEach(account -> account.getClient().getUpdate(message));
        }
    }

    public void changeDebitPercent(float newPercent) {
        debitPercent = newPercent;
        notifySubscribers(ACCOUNT_TYPE.DEBIT, "Debit percentage changed for " + newPercent);
    }

    public void changeCreditCommission(float newCommission) {
        creditCommission = newCommission;
        notifySubscribers(ACCOUNT_TYPE.CREDIT, "Credit commission changed for " + newCommission);
    }

    public void changeUnverifiedLimit(float newLimit) {
        unverifiedLimit = newLimit;
        notifySubscribers(ACCOUNT_TYPE.ANY, "Unverified limit changed for " + newLimit);
    }

    public void changeCreditBelowZeroLimit(float newLimit) {
        belowZeroLimit = newLimit;
        notifySubscribers(ACCOUNT_TYPE.ANY, "BelowZero limit changed for " + newLimit);
    }

    public void changeInterestRates(ArrayList<InterestRate> newInterestRates) {
        interestRates = newInterestRates;
        notifySubscribers(ACCOUNT_TYPE.DEPOSIT, "Interest rates changed");
    }

    public void calculateInterestForAccount(Account account, Date dateTime, float percents) {
        if (account.accountType.equals(ACCOUNT_TYPE.CREDIT)) return;

        for (Date currentDate = account.getLastDayInterestsCommissionPaid();
             currentDate.before(new Date(dateTime.getTime() + 100));
             currentDate = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24))) {
            Transaction lastTransaction = account.getLastTransactionInDay(currentDate);
            if (lastTransaction == null) continue;
            float curBalance = lastTransaction.getBalanceAfterTransaction();
            account.setTempInterestCommissionSum(account.getTempInterestCommissionSum() + (curBalance * (percents / 36500)) + (account.getTempInterestCommissionSum() * (percents / 36500)));
        }

        account.setLastDayInterestsCommissionPaid(dateTime);
    }

    public void calculateCreditCommissionsForAccount(CreditAccount creditAccount, Date dateTime) {
        for (Date currentDate = creditAccount.getLastDayInterestsCommissionPaid();
             currentDate.before(new Date(dateTime.getTime() + 100));
             currentDate = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24))) {
            Transaction lastTransaction = creditAccount.getLastTransactionInDay(currentDate);
            if (lastTransaction == null) continue;
            if (lastTransaction.getBalanceAfterTransaction() < 0) {
                creditAccount.setTempInterestCommissionSum(creditAccount.getTempInterestCommissionSum() + creditCommission);
            }
        }

        creditAccount.setLastDayInterestsCommissionPaid(dateTime);
    }

    public float calculateDepositPercent(float depositSum) {
        for (InterestRate interestRate : interestRates) {
            if (depositSum < interestRate.limit) return interestRate.percent;
        }

        return interestRates.get(interestRates.size() - 1).percent;
    }

    public ArrayList<Account> getAccountsWithClient(Client client) {
        return new ArrayList<Account>(accounts.stream().filter(account -> account.getClient() == client).toList());
    }

    public Client findClientByName(String clientNameSurname) {
        for (Account account : accounts) {
            if (account.getClientNameSurname().equals(clientNameSurname)) return account.getClient();
        }
        return null;
    }

    public float checkDepositAccountBalanceAfterTime(DepositAccount account, Date skipTime) {
        var depositAccountCopy = new DepositAccount(account);
        depositAccountCopy.setTempInterestCommissionSum(0);
        calculateInterestForAccount(depositAccountCopy, skipTime, calculateDepositPercent(depositAccountCopy.depositSum));
        return depositAccountCopy.getBalance() + depositAccountCopy.getTempInterestCommissionSum();
    }

    public float checkDebitAccountBalanceAfterTime(DebitAccount account, Date skipTime) {
        var debitAccountCopy = new DebitAccount(account);
        debitAccountCopy.setTempInterestCommissionSum(0);
        calculateInterestForAccount(debitAccountCopy, skipTime, debitPercent);
        return debitAccountCopy.getBalance() + debitAccountCopy.getTempInterestCommissionSum();
    }

    public float checkCreditAccountBalanceAfterTime(CreditAccount account, Date skipTime) {
        var creditAccountCopy = new CreditAccount(account);
        creditAccountCopy.setTempInterestCommissionSum(0);
        calculateCreditCommissionsForAccount(creditAccountCopy, skipTime);
        return creditAccountCopy.getBalance() - creditAccountCopy.getTempInterestCommissionSum();
    }

    public void payInterestsAndCommissions(Date dateTime) {
        for (Account account : accounts) {
            if (account instanceof DebitAccount debitAccount) {
                calculateInterestForAccount(account, dateTime, debitPercent);
                account.addInterest(account.getTempInterestCommissionSum(), dateTime);
                account.setTempInterestCommissionSum(0);
            }
            if (account instanceof DepositAccount depositAccount) {
                calculateInterestForAccount(account, dateTime, calculateDepositPercent(depositAccount.depositSum));
                account.addInterest(account.getTempInterestCommissionSum(), dateTime);
                account.setTempInterestCommissionSum(0);
            }
            if (account instanceof CreditAccount creditAccount) {
                calculateCreditCommissionsForAccount(creditAccount, dateTime);
                account.subtractCommissions(account.getTempInterestCommissionSum(), dateTime);
                account.setTempInterestCommissionSum(0);
            } //couldnt use switch
        }
    }
}