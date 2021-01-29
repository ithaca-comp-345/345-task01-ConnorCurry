package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        // New tests, negative input and 0 input
        // assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-200));
        // assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(0));
        
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