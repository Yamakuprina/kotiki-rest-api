package ru.itmo.banks.transactions;

import ru.itmo.banks.BankException;
import ru.itmo.banks.ItmoBank;
import ru.itmo.banks.accounts.Account;

import java.util.Date;

public class TransferTransaction extends Transaction {
    public Account TransferDestination;

    public TransferTransaction(Account account, Date date, float transactionSum, Account transferDestination) throws BankException {
        super(account, date, transactionSum);
        TransferDestination = transferDestination;
    }

    @Override
    public void completeOperation() throws BankException {
        if (!account.getClient().verified() && transactionSum > ItmoBank.getUnverifiedLimit()) {
            throw new BankException("Unverified account cant transfer above limit");
        }
        account.setBalance(account.getBalance() - transactionSum);
        TransferDestination.setBalance(TransferDestination.getBalance() + transactionSum);
        setBalanceAfterTransaction(account.getBalance());
    }

    @Override
    public void cancelOperation() throws BankException {
        if (operationCanceled) {
            throw new BankException("Operation is already canceled");
        }
        account.setBalance(account.getBalance() + transactionSum);
        TransferDestination.setBalance(TransferDestination.getBalance() - transactionSum);
        operationCanceled = true;
        setBalanceAfterTransaction(balanceBeforeTransaction);
    }
}
