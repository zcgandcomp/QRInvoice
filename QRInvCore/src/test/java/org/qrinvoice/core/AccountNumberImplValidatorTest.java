package org.qrinvoice.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 5.6.2016.
 */
public class AccountNumberImplValidatorTest {

    @org.junit.Test
    public void testValidator() throws Exception {

        AccountNumberImpl number = new AccountNumberImpl("19", "1007001", "3030");

        AccountNumberValidator validator = new AccountNumberValidator();
        boolean result = validator.isValid(number, null);

        assertEquals(result, true);

    }


    @org.junit.Test
    public void testValidatorEmpty() throws Exception {

        AccountNumberImpl number = new AccountNumberImpl(null, null, null);


        AccountNumberValidator validator = new AccountNumberValidator();
        boolean result = validator.isValid(number, null);
        assertEquals(result, true);

    }


    @Test(expected = AccountNotValidException.class)

    public void testValidatorException() throws Exception {

        AccountNumberImpl number = new AccountNumberImpl("19", null, null);

    }

    @org.junit.Test
    public void testValidatorNotEmpty() throws Exception {

        AccountNumberImpl number = new AccountNumberImpl();

        number.setAccountPrefix(null);
        number.setAccountBase("1007001");
        number.setBankCode("3030");


        AccountNumberValidator validator = new AccountNumberValidator();
        boolean result = validator.isValid(number, null);
        assertEquals(result, true);
    }


}