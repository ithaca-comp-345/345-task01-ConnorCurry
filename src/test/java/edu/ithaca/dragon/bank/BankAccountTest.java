package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());

        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());

        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance()); // Works at zero

    }

    @Test
    void isAmountValidTest() throws IllegalArgumentException {
        assertTrue(BankAccount.isAmountValid(21.11)); // true with two decimals
        assertTrue(BankAccount.isAmountValid(21.1)); // true with one decimal
        assertTrue(BankAccount.isAmountValid(21)); // true with no decimals
        assertFalse(BankAccount.isAmountValid(-21.11)); // false with a negative
        assertFalse(BankAccount.isAmountValid(21.111)); // false with three decimal places
        assertFalse(BankAccount.isAmountValid(0)); // false for zero
        assertTrue(BankAccount.isAmountValid(0.1)); // true for zero with one decimal
        assertTrue(BankAccount.isAmountValid(0.11)); // true for zero with two decimals
        assertFalse(BankAccount.isAmountValid(0.111)); // false for zero with three decimals
    }

    @Test
    void depositTest() {
        BankAccount account = new BankAccount("asd@asdf.com", 200);
        account.deposit(100);
        assertEquals(300, account.getBalance()); // basic deposit

        account.deposit(100.11);
        assertEquals(400.11, account.getBalance()); // two decimal places

        account.deposit(100.2);
        assertEquals(500.31, account.getBalance()); // one decimal place

        account.deposit(0.1);
        assertEquals(500.41, account.getBalance());

        assertThrows(IllegalArgumentException.class, () -> account.deposit(-199)); // negative should throw exception
        assertThrows(IllegalArgumentException.class, () -> account.deposit(199.111)); // three decimal places
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0)); // zero
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.111));
    }

    @Test
    void transferTest() {
        BankAccount account1 = new BankAccount("asd@asd.com", 1000);
        BankAccount account2 = new BankAccount("asds@asd.com", 5000);
        BankAccount.transfer(account2, account1, 1000); // generic transfer test
        assertEquals(2000, account1.getBalance());
        assertEquals(4000, account2.getBalance());

        BankAccount.transfer(account2, account1, 1000.11); // transfer with two decimal places
        assertEquals(3000.11, account1.getBalance());
        assertEquals(2999.89, account2.getBalance());

        BankAccount.transfer(account1, account2, 1000.2); // transfer with one decimal place
        assertEquals(1999.91, account1.getBalance());
        assertEquals(4000.09, account2.getBalance());

        BankAccount.transfer(account1, account2, 0.02); // boundary case transfer 1
        assertEquals(1999.89, account1.getBalance());
        assertEquals(4000.11, account2.getBalance());

        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(account1, account2, -100)); // negative
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(account1, account2, 100.111)); // three decimal places
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(account1, account2, 0)); // 0
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(account1, account2, 0.111)); //boundary case three decimal places



        
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        // New tests, negative input and 0 input
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-200));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0));
        //assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.50));


        bankAccount.withdraw(1);
        assertEquals(99, bankAccount.getBalance());


        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(200.222));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-200.22));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-200));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.222));

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid("a@b.com")); // basic test should return true
        assertFalse(BankAccount.isEmailValid("")); // empty string
        assertFalse(BankAccount.isEmailValid("a@bcom")); // missing "."
        assertFalse(BankAccount.isEmailValid("ab.com")); // missing "@"
        assertFalse(BankAccount.isEmailValid("a.com")); // missing "@" and characters after
        assertTrue(BankAccount.isEmailValid("a@b.org")); // different ending, should return true
        assertFalse(BankAccount.isEmailValid("@b.com")); // missing name
        assertFalse(BankAccount.isEmailValid("a@.com")); // missing domain
        assertFalse(BankAccount.isEmailValid("a@b.")); // missing domain suffix
        assertFalse(BankAccount.isEmailValid("@.com")); // missing name and domain
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abc@c.com", 200.222));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abc@c.com", -200.22));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abc@c.com", -200));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abc@c.com", 0.222));
    }

}