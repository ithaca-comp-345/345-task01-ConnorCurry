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
    }

}