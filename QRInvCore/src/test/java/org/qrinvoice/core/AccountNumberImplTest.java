package org.qrinvoice.core;

import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 15.7.2016.
 */
public class AccountNumberImplTest {

    static Logger log = Logger.getLogger(AccountNumberImplTest.class.getName());

    @Test
    public void computeIBANTest() throws AccountNotValidException {

        AccountNumberImpl number = new AccountNumberImpl("19", "1007001", "3030");
        String iban = number.computeIBAN();
        assertEquals("CZ4830300000190001007001", iban);


        number = new AccountNumberImpl(null, "1007001", "3030");
        iban = number.computeIBAN();
        assertEquals("CZ6230300000000001007001", iban);


    }


}
