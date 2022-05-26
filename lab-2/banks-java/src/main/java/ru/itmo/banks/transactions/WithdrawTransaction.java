package ru.itmo.banks.transactions;

import ru.itmo.banks.BankException;
import ru.itmo.banks.ItmoBank;
import ru.itmo.banks.accounts.Account;

import java.util.Date;

public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(Account account, Date date, float transactionSum) throws BankException {
        super(account, date, transactionSum);
    }

    @Override
    public void completeOperation() throws BankException {
        if (!account.getClient().verified() && transactionSum > ItmoBank.getUnverifiedLimit()) {
            throw new BankException("Unverified account cant withdraw above limit");
        }
        account.setBalance(account.getBalance() - transactionSum);
        setBalanceAfterTransaction(account.getBalance());
    }

    @Override
    public void cancelOperation() throws BankException {
        if (operationCanceled) {
            throw new BankException("Operation is already canceled");
        }
        account.setBalance(account.getBalance() + transactionSum);
        operationCanceled = true;
        setBalanceAfterTransaction(balanceBeforeTransaction);
    }
}
