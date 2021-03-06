package org.qrinvoice.core;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.qrinvoice.core.InvoiceAttributesConstants.*;

/**
 * Created by zcg on 5.6.2016.
 * Holder for invoice data, internal. Validation constraints set using bean validation
 *
 */

public class InvoiceParamDomain {

    //ID
    @NotNull
    @Size(max = 40)
    private String id;

    //DD
    private DateParam dateOfIssue;

    //AM

    @Digits(integer = 15, fraction = 2)
    @NotNull
    private BigDecimal totalAmount;

    //TP

    @Digits(integer = 1, fraction = 0)
    private Byte typeOfTax;

    //TD
    @Digits(integer = 1, fraction = 0)
    private Byte typeOfIdentification;

    //SA
    @Digits(integer = 1, fraction = 0)
    private Byte taxDeposit;

    //MSG
    @Size(max = 40)
    private String message;

    //ON
    @Size(max = 20)
    private String orderNumber;


    //VS
    @Size(max = 10)
    private String variableString;

    // VII
    @Size(max = 14)
    private String taxIdentificationNumberDrawer;

    // INI

    @Digits(integer = 8, fraction = 0)
    private String identificationNumberDrawer;

    //VIR

    @Size(max = 14)
    private String taxIdentificationNumberBene;

    //INR

    @Digits(integer = 8, fraction = 0)
    private String identificationNumberBene;

    //DUZP

    private DateParam dateOfTax;

    //DPPD

    private DateParam dateOfTaxDuty;

    //DT

    private DateParam dateOfDue;

    //TB0

    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseAmount;

    // T0

    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxAmount;

    //TB1

    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseReduced1Amount;

    //T1

    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxReduced1Amount;

    //TB2

    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseReduced2Amount;

    //T2

    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxReduced2Amount;

    //NTB

    @Digits(integer = 15, fraction = 2)
    private BigDecimal nonTaxAmount;

    //CC

    @Size(max = 3)
    private String currencyCode;

    //FX

    @Digits(integer = 15, fraction = 2)
    private BigDecimal exchangeRate;

    //FXA

    @Digits(integer = 5, fraction = 0)
    private BigDecimal exchangeUnits;

    // special attributes
    //ACC

    @Size(max = 34)
    private String IBAN;

    // no code

    @Size(max = 11)
    private String BIC;

    //X-SW
    @Size(max = 30)
    private String softwareOrigin;

    //X-URL
    @Size(max = 70)
    private String url;


    //X-URL
    @Size(max = 70)
    private String spaydUrl;

    //MSG
    @Size(max = 40)
    private String spaydMessage;

    private Map<String, Object> paramMap = new TreeMap<>();

    /**
     * add parameter to internal map of
     *
     * @param value
     * @param code
     */
    private void setValueCodePair(Object value, String code) {

        if (value != null) {
            paramMap.put(code, value);
        }

    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        setValueCodePair(currencyCode, CC);
    }


    public DateParam getDateOfDue() {
        return dateOfDue;
    }

    public void setDateOfDue(DateParam dateOfDue) {
        this.dateOfDue = dateOfDue;
        setValueCodePair(dateOfDue, DT);
    }


    public DateParam getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(DateParam dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
        setValueCodePair(dateOfIssue, DD);
    }


    public DateParam getDateOfTax() {
        return dateOfTax;
    }

    public void setDateOfTax(DateParam dateOfTax) {
        this.dateOfTax = dateOfTax;
        setValueCodePair(dateOfTax, DUZP);
    }


    public DateParam getDateOfTaxDuty() {
        return dateOfTaxDuty;

    }

    public void setDateOfTaxDuty(DateParam dateOfTaxDuty) {
        this.dateOfTaxDuty = dateOfTaxDuty;
        setValueCodePair(dateOfTaxDuty, DPPD);
    }


    public String getIdentificationNumberBene() {
        return identificationNumberBene;
    }

    public void setIdentificationNumberBene(String dentificationNumberBene) {
        this.identificationNumberBene = dentificationNumberBene;
        setValueCodePair(identificationNumberBene, INR);
    }


    public Number getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        setValueCodePair(exchangeRate, FX);
    }


    public Number getExchangeUnits() {
        return exchangeUnits;
    }

    public void setExchangeUnits(BigDecimal exchangeUnits) {
        this.exchangeUnits = exchangeUnits;
        setValueCodePair(exchangeUnits, FXA);
    }


    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
        if (getIBAN() != null) {
            setValueCodePair(getIBAN() + "+" + BIC, ACC);
        } else {
            setValueCodePair(BIC, ACC);
        }


    }


    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
        if (getBIC() != null) {
            setValueCodePair(getIBAN() + "+" + getBIC(), ACC);
        } else {
            setValueCodePair(IBAN, ACC);
        }

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        setValueCodePair(id, ID);
    }


    public String getIdentificationNumberDrawer() {
        return identificationNumberDrawer;
    }

    public void setIdentificationNumberDrawer(String identificationNumberDrawer) {
        this.identificationNumberDrawer = identificationNumberDrawer;
        setValueCodePair(identificationNumberDrawer, INI);
    }

    public BigDecimal getNonTaxAmount() {
        return nonTaxAmount;
    }

    public void setNonTaxAmount(BigDecimal nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
        setValueCodePair(nonTaxAmount, NTB);
    }


    public String getSoftwareOrigin() {
        return softwareOrigin;
    }

    public void setSoftwareOrigin(String softwareOrigin) {
        this.softwareOrigin = softwareOrigin;
        setValueCodePair(softwareOrigin, X_SW);
    }


    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        setValueCodePair(taxAmount, T0);
    }


    public BigDecimal getTaxBaseAmount() {
        return taxBaseAmount;
    }

    public void setTaxBaseAmount(BigDecimal taxBaseAmount) {
        this.taxBaseAmount = taxBaseAmount;
        setValueCodePair(taxBaseAmount, TB0);
    }


    public BigDecimal getTaxBaseReduced1Amount() {
        return taxBaseReduced1Amount;
    }

    public void setTaxBaseReduced1Amount(BigDecimal taxBaseReduced1Amount) {
        this.taxBaseReduced1Amount = taxBaseReduced1Amount;
        setValueCodePair(taxBaseReduced1Amount, TB1);
    }


    public BigDecimal getTaxBaseReduced2Amount() {
        return taxBaseReduced2Amount;
    }

    public void setTaxBaseReduced2Amount(BigDecimal taxBaseReduced2Amount) {
        this.taxBaseReduced2Amount = taxBaseReduced2Amount;
        setValueCodePair(taxBaseReduced2Amount, TB2);
    }

    public Byte getTaxDeposit() {
        return taxDeposit;
    }

    public void setTaxDeposit(Byte taxDeposit) {
        this.taxDeposit = taxDeposit;
        setValueCodePair(taxDeposit, SA);
    }


    public String getTaxIdentificationNumberBene() {
        return taxIdentificationNumberBene;
    }

    public void setTaxIdentificationNumberBene(String taxIdentificationNumberBene) {
        this.taxIdentificationNumberBene = taxIdentificationNumberBene;
        setValueCodePair(taxIdentificationNumberBene, VIR);
    }


    public String getTaxIdentificationNumberDrawer() {
        return taxIdentificationNumberDrawer;
    }

    public void setTaxIdentificationNumberDrawer(String taxIdentificationNumberDrawer) {
        this.taxIdentificationNumberDrawer = taxIdentificationNumberDrawer;
        setValueCodePair(taxIdentificationNumberDrawer, VII);
    }

    public BigDecimal getTaxReduced1Amount() {
        return taxReduced1Amount;
    }

    public void setTaxReduced1Amount(BigDecimal taxReduced1Amount) {
        this.taxReduced1Amount = taxReduced1Amount;
        setValueCodePair(taxReduced1Amount, T1);
    }

    public BigDecimal getTaxReduced2Amount() {
        return taxReduced2Amount;
    }

    public void setTaxReduced2Amount(BigDecimal taxReduced2Amount) {
        this.taxReduced2Amount = taxReduced2Amount;
        setValueCodePair(taxReduced2Amount, T2);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        setValueCodePair(totalAmount, AM);
    }


    public Byte getTypeOfIdentification() {
        return typeOfIdentification;
    }

    public void setTypeOfIdentification(Byte typeOfIdentification) {
        this.typeOfIdentification = typeOfIdentification;
        setValueCodePair(typeOfIdentification, TD);
    }

    public Byte getTypeOfTax() {
        return typeOfTax;
    }

    public void setTypeOfTax(Byte typeOfTax) {
        this.typeOfTax = typeOfTax;
        setValueCodePair(typeOfTax, TP);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setValueCodePair(url, X_URL);
    }

    public String getVariableString() {
        return variableString;
    }

    public void setVariableString(String variableString) {
        this.variableString = variableString;
        setValueCodePair(variableString, VS);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        this.message = message;
        setValueCodePair(message, MSG);
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        setValueCodePair(orderNumber, ON);


    }

    public Map<String, Object> getParamMap() {

        return (paramMap == null) ? null : Collections.unmodifiableMap(paramMap);
    }

    public String getSpaydUrl() {
        return spaydUrl;
    }

    public void setSpaydUrl(String spaydUrl) {
        this.spaydUrl = spaydUrl;
        setValueCodePair(spaydUrl, X_URL_SPAYD);
    }

    public String getSpaydMessage() {
        return spaydMessage;
    }

    public void setSpaydMessage(String spaydMessage) {
        this.spaydMessage = spaydMessage;
        setValueCodePair(spaydMessage, MSG_SPAYD);
    }
}
