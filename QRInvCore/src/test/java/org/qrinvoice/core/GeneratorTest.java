package org.qrinvoice.core;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.qrinvoice.domain.DateParam;
import org.qrinvoice.domain.InvoiceParam;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 11.6.2016.
 */
public class GeneratorTest {

    static Logger log = Logger.getLogger(GeneratorTest.class.getName());

    @Test
    public void getQRCode() throws Exception {

        Generator generator = new Generator();


        InvoiceParam invoice = new InvoiceParam();

        AccountNumber number = new AccountNumber("19", "1007001", "3030");

        invoice.setIBAN(number.computeIBAN());


        generator.getQRCode(null, generator.getInvoiceString(invoice, false));


    }


    @Test
    public void getInvoiceString() throws Exception {


        Generator generator = new Generator();

        InvoiceParam invoice = new InvoiceParam();

        AccountNumber number = new AccountNumber("19", "1007001", "3030");

        //invoice.setCurrencyCode("CZK");


        invoice.setIBAN(number.computeIBAN());

        invoice.setDateOfDue(new DateParam("20160131"));

        String expResult = "SID*1.0*DT:20160131*ACC:CZ4830300000190001007001*";

        String result = generator.getInvoiceString(invoice, true);

        assertEquals(expResult, result);

    }

    @Test
    public void escapeDisallowedCharacters() throws UnsupportedEncodingException {

        Generator generator = new Generator();

        String original = "abc  123\u2665\u2620**123  abc-+ěščřžýáíé---%20";

        log.info(generator.escapeDisallowedCharacters(original));
        String expected = "abc  123%E2%99%A5%E2%98%A0%2A%2A123  abc-%2B%C4%9B%C5%A1%C4%8D%C5%99%C5%BE%C3%BD%C3%A1%C3%AD%C3%A9---%2520";
        assertEquals(expected, generator.escapeDisallowedCharacters(original));
    }


    /**
     * Test of paymentStringFromAccount method, of class SmartPayment.
     */
    /*
    @Test
    public void testPaymentStringFromAccountAmountAndAlternateAccounts() throws UnsupportedEncodingException {
        System.out.println("paymentStringFromAccount");
        SpaydPaymentAttributes parameters = new SpaydPaymentAttributes();
        parameters.setBankAccount(new CzechBankAccount("19", "123", "0800"));
        List<BankAccount> alternateAccounts = new LinkedList<BankAccount>();
        alternateAccounts.add(new CzechBankAccount(null, "19", "5500"));
        alternateAccounts.add(new CzechBankAccount(null, "19", "0100"));
        parameters.setAlternateAccounts(alternateAccounts);
        parameters.setAmount(100.5);
        SpaydExtendedPaymentAttributeMap extendedParameters = null;
        boolean transliterateParams = false;
        String expResult = "SPD*1.0*ACC:CZ2408000000190000000123*ALT-ACC:CZ9755000000000000000019,CZ7301000000000000000019*AM:100.5";
        String result = SpaydPayment.paymentStringFromAccount(parameters, extendedParameters, transliterateParams);
        System.out.println(result);
        assertEquals(expResult, result);
    }
*/

}