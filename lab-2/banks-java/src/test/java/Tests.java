import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.banks.Client;
import ru.itmo.banks.InterestRate;
import ru.itmo.banks.accounts.CreditAccount;
import ru.itmo.banks.accounts.DebitAccount;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Tests {
    CentralBank centralBank;
    Bank bank;
    Client client1;
    Client client2;
    DebitAccount debitAccount1;
    DebitAccount debitAccount2;

    @BeforeEach
    public void Setup() {
        var interestRates = new ArrayList<InterestRate>(Arrays.asList(
                new InterestRate(5000, 7),
                new InterestRate(10000, 9.5f)
        ));
        centralBank = new CentralBank();
        bank = centralBank.registerBank("ITMO ItmoBank", 6, 20000, 25000, 50, interestRates);
        client1 = new Client("Tagir", "Faizullin");
        client2 = new Client("Ivan", "Ivanov");
        debitAccount1 = bank.addNewDebitAccount(client1);
        debitAccount2 = bank.addNewDebitAccount(client2);
        debitAccount1.refill(10000, Date.from(Instant.now()));
        debitAccount2.refill(5000, Date.from(Instant.now()));
    }

    @Test
    public void Refill() {
        debitAccount1.refill(5000, Date.from(Instant.now()));
        Assertions.assertEquals(debitAccount1.getBalance(), 15000);
    }

    @Test
    public void CancelRefill() {
        debitAccount1.refill(5000, Date.from(Instant.now()));
        debitAccount1.undoTransaction(debitAccount1.history.get(1).id);
        Assertions.assertEquals(debitAccount1.getBalance(), 10000);
    }

    @Test
    public void PayInterest() {
        Date dateMonthAhead = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        centralBank.notify(dateMonthAhead);
        Assertions.assertEquals(Math.round(debitAccount1.getBalance()), 10049);
    }

    @Test
    public void SubtractCreditCommissions() {
        CreditAccount creditAccount = bank.addNewCreditAccount(client1);
        creditAccount.withdrawal(5000, Date.from(Instant.now()));
        Date dateMonthAhead = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        centralBank.notify(dateMonthAhead);
        Assertions.assertEquals(Math.round(creditAccount.getBalance()), -6500);

    }

    @Test
    public void Transfer() {
        debitAccount1.transfer(5000, debitAccount2, Date.from(Instant.now()));
        Assertions.assertEquals(debitAccount1.getBalance(), 5000);
        Assertions.assertEquals(debitAccount2.getBalance(), 10000);

    }

    @Test
    public void UnverifiedCatch() {
        debitAccount1.refill(15000, Date.from(Instant.now()));
        debitAccount1.transfer(21000, debitAccount2, Date.from(Instant.now()));
        Assertions.assertEquals(debitAccount1.getBalance(), 25000);
    }

    @Test
    public void BelowZeroLimit() {
        CreditAccount creditAccount = bank.addNewCreditAccount(client1);
        creditAccount.refill(15000, Date.from(Instant.now()));
        client1.setAddress("Vyazemskiy");
        client1.setPassport("12345678");
        creditAccount.transfer(50000, debitAccount2, Date.from(Instant.now()));
        Assertions.assertEquals(creditAccount.getBalance(), 15000);
    }
}
