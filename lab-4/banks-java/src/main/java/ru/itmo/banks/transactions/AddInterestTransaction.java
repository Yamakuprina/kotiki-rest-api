package ru.itmo.banks.transactions;

import ru.itmo.banks.BankException;
import ru.itmo.banks.accounts.Account;

import java.util.Date;

public class AddInterestTransaction extends Transaction {
    public AddInterestTransaction(Account account, Date date, float transactionSum) throws BankException {
        super(account, date, transactionSum);
    }

    @Override
    public void completeOperation() throws BankException {
        account.setBalance(account.getBalance() + transactionSum);
        setBalanceAfterTransaction(account.getBalance());
    }

    @Override
    public void cancelOperation() throws BankException {
        if (operationCanceled) {
            throw new BankException("Operation is already canceled");
        }
        account.setBalance(account.getBalance() - transactionSum);
        operationCanceled = true;
        setBalanceAfterTransaction(balanceBeforeTransaction);
    }
}
