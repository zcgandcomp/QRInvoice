package org.qrinvoice.core;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 11.6.2016.
 */
public class GeneratorTest {

    static Logger log = Logger.getLogger(GeneratorTest.class.getName());

    @Test
    public void getQRCode() throws Exception {

        Generator generator = new Generator();


        InvoiceParamDomain invoice = new InvoiceParamDomain();

        AccountNumberImpl number = new AccountNumberImpl("19", "1007001", "3030");

        invoice.setIBAN(number.computeIBAN());

        generator.getQRCode(null, generator.getInvoiceString(invoice, false, IntegrationModeEnum.INVOICE_MODE), IntegrationModeEnum.INVOICE_MODE, true);

    }


    @Test
    public void getInvoiceString() throws Exception {


        Generator generator = new Generator();

        InvoiceParamDomain invoice = new InvoiceParamDomain();

        AccountNumberImpl number = new AccountNumberImpl("19", "1007001", "3030");

        //invoice.setCurrencyCode("CZK");

        invoice.setIBAN(number.computeIBAN());

        invoice.setDateOfDue(new DateParam("20160131"));

        String expResult = "SID*1.0*ACC:CZ4830300000190001007001*DT:20160131*";

        String result = generator.getInvoiceString(invoice, true, IntegrationModeEnum.INVOICE_MODE);

        assertEquals(expResult, result);

    }

    @Test
    public void getInvoiceStringSPAYDIntegration() throws Exception {

        Generator generator = new Generator();

        InvoiceParamDomain invoice = new InvoiceParamDomain();

        AccountNumberImpl number = new AccountNumberImpl("19", "1007001", "3030");

        invoice.setCurrencyCode("CZK");

        invoice.setIBAN(number.computeIBAN());

        invoice.setDateOfDue(new DateParam("20160131"));

        invoice.setDateOfIssue(new DateParam("20160202"));


        invoice.setTotalAmount(new BigDecimal(9535.12).setScale(2, RoundingMode.HALF_UP));

        invoice.setMessage("qr message");

        invoice.setUrl("url");

        invoice.setId("1963/160/2015");

        invoice.setTypeOfTax((byte) 0);

        invoice.setVariableString("1234567890");

        invoice.setTaxIdentificationNumberDrawer("CZ60194383");

        invoice.setTaxIdentificationNumberBene("CZ12345678");

        invoice.setIdentificationNumberDrawer("60194383");

        invoice.setDateOfTax(new DateParam("20161201"));

        invoice.setTaxBaseAmount(BigDecimal.TEN);

        invoice.setTaxAmount(new BigDecimal(13.13).setScale(2, RoundingMode.HALF_UP));

        invoice.setTaxBaseReduced1Amount(new BigDecimal(15.15).setScale(2, RoundingMode.HALF_UP));

        invoice.setTaxReduced1Amount(new BigDecimal(20.20).setScale(2, RoundingMode.HALF_UP));

        invoice.setNonTaxAmount(new BigDecimal(21.21).setScale(2, RoundingMode.HALF_UP));

        String expResult = "SID*1.0*ACC:CZ4830300000190001007001*" +
                "AM:9535.12*" +
                "CC:CZK*" +
                "DD:20160202*" +
                "DT:20160131*" +
                "DUZP:20161201*ID:1963/160/2015*" +
                "INI:60194383*MSG:QR MESSAGE*NTB:21.21*" +
                "T0:13.13*T1:20.20*TB0:10*TB1:15.15*TP:0*" +
                "VII:CZ60194383*VIR:CZ12345678*" +
                "VS:1234567890*X-URL:URL*";

        String result = generator.getInvoiceString(invoice, true, IntegrationModeEnum.INVOICE_MODE);

        assertEquals(expResult, result);

        invoice.setSpaydMessage("spayd message");

        invoice.setSpaydUrl("spayd url");

        result = generator.getInvoiceString(invoice, true, IntegrationModeEnum.SPAYD_MODE);

        expResult = "SPD*1.0*ACC:CZ4830300000190001007001*" +
                "AM:9535.12*" +
                "CC:CZK*" +
                "DT:20160131*MSG:SPAYD MESSAGE*X-VS:1234567890*X-URL:SPAYD URL*" +
                "X-INV:SID%2A1.0%2ADD:20160202%2ADUZP:20161201%2AID:1963/160/2015%2AINI:60194383%2AMSG:QR MESSAGE%2A" +
                "NTB:21.21%2AT0:13.13%2AT1:20.20%2ATB0:10%2A" +
                "TB1:15.15%2ATP:0%2AVII:CZ60194383%2AVIR:CZ12345678%2AX-URL:URL%2A";

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


}