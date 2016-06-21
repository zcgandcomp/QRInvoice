package org.qrinvoice.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 5.6.2016.
 */
public class AccountNumberValidatorTest {

    @org.junit.Test
    public void testValidator() throws Exception {

        AccountNumber number = new AccountNumber("19", "1007001", "3030");

        boolean result = number.isAccountNumberValid();

        assertEquals(result, true);

    }


    @org.junit.Test
    public void testValidatorEmpty() throws Exception {

        AccountNumber number = new AccountNumber(null, null, null);

        boolean result = number.isAccountNumberValid();
        assertEquals(result, true);

    }


    @Test(expected = IllegalStateException.class)

    public void testValidatorException() throws Exception {

        AccountNumber number = new AccountNumber("19", null, null);

    }

    @org.junit.Test
    public void testValidatorNotEmpty() throws Exception {

        AccountNumber number = new AccountNumber();

        number.setAccountPrefix(null);
        number.setAccountBase("1007001");
        number.setBankCode("3030");

        boolean result = number.isAccountNumberValid();
        assertEquals(result, true);
    }


}