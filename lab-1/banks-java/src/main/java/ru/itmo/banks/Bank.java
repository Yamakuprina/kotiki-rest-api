package ru.itmo.banks;

import ru.itmo.banks.accounts.*;

import java.util.ArrayList;
import java.util.Date;

public interface Bank {

    DebitAccount addNewDebitAccount(Client client);

    DepositAccount addNewDepositAccount(Client client, Date depositExpirationDate);

    CreditAccount addNewCreditAccount(Client client);

    void subscribeClient(Account account);

    void notifySubscribers(ACCOUNT_TYPE accountType, String message);

    void changeDebitPercent(float newPercent);

    void changeCreditCommission(float newCommission);

    void changeUnverifiedLimit(float newLimit);

    void changeCreditBelowZeroLimit(float newLimit);

    void changeInterestRates(ArrayList<InterestRate> newInterestRates);

    void calculateInterestForAccount(Account account, Date dateTime, float percents);

    void calculateCreditCommissionsForAccount(CreditAccount creditAccount, Date dateTime);

    float calculateDepositPercent(float depositSum);

    ArrayList<Account> getAccountsWithClient(Client client);

    Client findClientByName(String clientNameSurname);

    float checkDepositAccountBalanceAfterTime(DepositAccount account, Date skipTime);

    float checkDebitAccountBalanceAfterTime(DebitAccount account, Date skipTime);

    float checkCreditAccountBalanceAfterTime(CreditAccount account, Date skipTime);

    void payInterestsAndCommissions(Date dateTime);
}
