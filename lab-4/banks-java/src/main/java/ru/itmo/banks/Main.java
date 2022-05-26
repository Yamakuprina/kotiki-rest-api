package ru.itmo.banks;

import ru.itmo.banks.accounts.Account;
import ru.itmo.banks.accounts.CreditAccount;
import ru.itmo.banks.accounts.DebitAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var interestRates = new ArrayList<InterestRate>(Arrays.asList(
                new InterestRate(5000, 7),
                new InterestRate(10000, 9.5f)
        ));
        CentralBank centralBank = new CentralBank();
        Bank bank = centralBank.registerBank("ITMO ItmoBank", 6, 20000, 25000, 50, interestRates);
        Client client1 = new Client("Tagir", "Faizullin");
        Client client2 = new Client("Ivan", "Ivanov");
        DebitAccount debitAccount1 = bank.addNewDebitAccount(client1);
        CreditAccount creditAccount = bank.addNewCreditAccount(client1);
        DebitAccount debitAccount2 = bank.addNewDebitAccount(client2);
        debitAccount1.refill(10000, Date.from(Instant.now()));
        creditAccount.refill(500, Date.from(Instant.now()));
        debitAccount2.refill(5000, Date.from(Instant.now()));
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Welcome to ITMO ItmoBank App!");
                System.out.println("Enter your name and surname:");
                String userNameSurname = scanner.nextLine();
                Client userClient = bank.findClientByName(userNameSurname);
                if (userClient == null) {
                    System.out.println("Cant find client");
                    continue;
                }
                System.out.println("Authorisation success\n");
                while (true) {
                    var userAccounts = bank.getAccountsWithClient(userClient);
                    System.out.println("Your accounts:");
                    for (int i = 0; i < userAccounts.size(); i++) {
                        System.out.println((i + 1) + ". " + userAccounts.get(i).accountType + " Balance: " + userAccounts.get(i).getBalance());
                    }
                    System.out.println("\nWhat do you want to do?");
                    System.out.println("1.Create an account \n2.Transfer operation\n3.Withdraw operation \n4.Refill operation \n5.Exit");
                    int operationNumber = scanner.nextInt();
                    scanner.nextLine();
                    switch (operationNumber) {
                        case 1:
                            System.out.println("What account do you want to create?");
                            System.out.println("1.Debit \n2.Deposit \n3.Credit");
                            int accountNumber = scanner.nextInt();
                            scanner.nextLine();
                            switch (accountNumber) {
                                case 1 -> {
                                    bank.addNewDebitAccount(userClient);
                                    System.out.println("New Debit account created!");
                                }
                                case 2 -> {
                                    System.out.println("Enter deposit expiration date in format yyyy-MM-dd:");
                                    String depositExpirationDate = scanner.nextLine();
                                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        //String depositExpirationDate = scanner.nextLine();
                                        bank.addNewDepositAccount(userClient, new SimpleDateFormat("yyyy-MM-dd").parse(depositExpirationDate));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("New Deposit account created!");
                                }
                                case 3 -> {
                                    bank.addNewCreditAccount(userClient);
                                    System.out.println("New Credit account created!");
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Enter name of the person you want to transfer:");
                            String transferDestinationName = scanner.nextLine();
                            Client transferDestinationClient = bank.findClientByName(transferDestinationName);
                            if (transferDestinationClient == null) {
                                System.out.println("Cant find transfer destination");
                                break;
                            }
                            var transferDestinationAccounts = bank.getAccountsWithClient(transferDestinationClient);
                            System.out.println("Choose account you want to transfer to:");
                            for (int i = 0; i < transferDestinationAccounts.size(); i++) {
                                System.out.println((i + 1) + ". " + transferDestinationName + " " + transferDestinationAccounts.get(i).accountType + " Account");
                            }
                            var transferDestinationAccountNumber = scanner.nextInt();
                            scanner.nextLine();
                            Account transferDestinationAccount = transferDestinationAccounts.get(transferDestinationAccountNumber - 1);

                            System.out.println("Choose account you want to transfer from:");
                            for (int i = 0; i < userAccounts.size(); i++) {
                                System.out.println((i + 1) + ". " + userAccounts.get(i).accountType + " Balance: " + userAccounts.get(i).getBalance());
                            }
                            accountNumber = scanner.nextInt();
                            scanner.nextLine();
                            Account chosenAccount = userAccounts.get(accountNumber - 1);
                            System.out.println("Enter operation sum:");
                            float operationSum = scanner.nextFloat();
                            scanner.nextLine();
                            chosenAccount.transfer(operationSum, transferDestinationAccount, Date.from(Instant.now()));
                            break;
                        case 3:
                            System.out.println("Choose account you want to withdraw from:");
                            for (int i = 0; i < userAccounts.size(); i++) {
                                System.out.println((i + 1) + ". " + userAccounts.get(i).accountType + " Balance: " + userAccounts.get(i).getBalance());
                            }
                            accountNumber = scanner.nextInt();
                            chosenAccount = userAccounts.get(accountNumber - 1);
                            System.out.println("Enter operation sum:");
                            operationSum = scanner.nextFloat();
                            chosenAccount.withdrawal(operationSum, Date.from(Instant.now()));
                            break;
                        case 4:
                            System.out.println("Choose account you want to refill:");
                            for (int i = 0; i < userAccounts.size(); i++) {
                                System.out.println((i + 1) + ". " + userAccounts.get(i).accountType + " Balance: " + userAccounts.get(i).getBalance());
                            }
                            accountNumber = scanner.nextInt();
                            chosenAccount = userAccounts.get(accountNumber - 1);
                            System.out.println("Enter operation sum:");
                            operationSum = scanner.nextFloat();
                            chosenAccount.refill(operationSum, Date.from(Instant.now()));
                            break;
                        case 5:
                            return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Wrong value entered");
            }
        }
    }
}
