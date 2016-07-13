package org.qrinvoice.model;

import org.qrinvoice.core.AccountNumber;
import org.qrinvoice.core.CheckAccountNumber;
import org.qrinvoice.core.DateParam;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zcg on 27.6.2016.
 */


public class InvoiceModel implements Serializable {


    //ID
    /**
     * Id of generated invoice
     */
    @QueryParam(value = "id")
    @NotNull
    @Size(max = 40)
    private String id;


    //DD
    @QueryParam(value = "DD")
    @NotNull
    private DateParam dateOfIssue;

    //AM
    @QueryParam(value = "AM")
    @Digits(integer = 15, fraction = 2)
    @NotNull
    private BigDecimal totalAmount;

    //TP
    @QueryParam(value = "TP")
    @Digits(integer = 1, fraction = 0)
    private Byte typeOfTax;

    //TD
    @QueryParam(value = "TD")
    @Digits(integer = 1, fraction = 0)
    private Byte typeOfIdentification;

    //SA
    @QueryParam(value = "SA")
    @Digits(integer = 1, fraction = 0)
    private Byte taxDeposit;

    //MSG
    @QueryParam(value = "MSG")
    @Size(max = 40)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    //ON
    @QueryParam(value = "ON")
    @Size(max = 20)
    private String orderNumber;

    //VS
    @QueryParam(value = "VS")
    @Size(max = 10)
    @Pattern(regexp = "^([0-9]{0,10})$")
    private String variableString;

    // VII
    @QueryParam(value = "VII")
    @Size(max = 14)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String taxIdentificationNumberDrawer;

    // INI

    @QueryParam(value = "INI")
    @Digits(integer = 8, fraction = 0)
    private BigDecimal identificationNumberDrawer;

    //VIR
    @QueryParam(value = "VIR")
    @Size(max = 14)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String taxIdentificationNumberBene;

    //INR

    @QueryParam(value = "INR")
    @Digits(integer = 8, fraction = 0)
    private BigDecimal identificationNumberBene;

    //DUZP
    @QueryParam(value = "DUZP")
    private DateParam dateOfTax;

    //DPPD
    //TODO validation
    @QueryParam(value = "DPPD")
    private DateParam dateOfTaxDuty;

    //DT
    @QueryParam(value = "DT")
    private DateParam dateOfDue;

    //TB0
    @QueryParam(value = "TB0")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseAmount;

    // T0
    @QueryParam(value = "T0")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxAmount;

    //TB1
    @QueryParam(value = "TB1")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseReduced1Amount;

    //T1
    @QueryParam(value = "T1")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxReduced1Amount;

    //TB2
    @QueryParam(value = "TB2")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseReduced2Amount;

    //T2
    @QueryParam(value = "T2")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxReduced2Amount;

    //NTB
    @QueryParam(value = "NTB")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal nonTaxAmount;

    //CC
    @DefaultValue("CZK")
    @QueryParam(value = "CC")
    @Size(max = 3)
    @Pattern(regexp = "^[A-Z]*$")
    private String currencyCode;

    //FX
    @QueryParam(value = "FX")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal exchangeRate;

    //FXA
    @QueryParam(value = "FXA")
    @Digits(integer = 5, fraction = 0)
    private BigDecimal exchangeUnits;

    // special attributes

    // TODO create in producer
    @CheckAccountNumber
    @Valid
    @BeanParam
    private AccountNumberModel accountNumber = new AccountNumberModel();


    //ACC
    @QueryParam(value = "ACC")
    @Size(max = 34)
    private String IBAN;

    // no code
    @QueryParam(value = "BIC")
    @Size(max = 11)
    private String BIC;

    //X-SW
    @QueryParam(value = "X-SW")
    @Size(max = 30)
    private String softwareOrigin;

    //X-URL
    @QueryParam(value = "X-URL")
    @Size(max = 70)
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateParam getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(DateParam dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Byte getTypeOfTax() {
        return typeOfTax;
    }

    public void setTypeOfTax(Byte typeOfTax) {
        this.typeOfTax = typeOfTax;
    }

    public Byte getTypeOfIdentification() {
        return typeOfIdentification;
    }

    public void setTypeOfIdentification(Byte typeOfIdentification) {
        this.typeOfIdentification = typeOfIdentification;
    }

    public Byte getTaxDeposit() {
        return taxDeposit;
    }

    public void setTaxDeposit(Byte taxDeposit) {
        this.taxDeposit = taxDeposit;
    }

    public String getVariableString() {
        return variableString;
    }

    public void setVariableString(String variableString) {
        this.variableString = variableString;
    }

    public String getTaxIdentificationNumberDrawer() {
        return taxIdentificationNumberDrawer;
    }

    public void setTaxIdentificationNumberDrawer(String taxIdentificationNumberDrawer) {
        this.taxIdentificationNumberDrawer = taxIdentificationNumberDrawer;
    }

    public BigDecimal getIdentificationNumberDrawer() {
        return identificationNumberDrawer;
    }

    public void setIdentificationNumberDrawer(BigDecimal identificationNumberDrawer) {
        this.identificationNumberDrawer = identificationNumberDrawer;
    }

    public String getTaxIdentificationNumberBene() {
        return taxIdentificationNumberBene;
    }

    public void setTaxIdentificationNumberBene(String taxIdentificationNumberBene) {
        this.taxIdentificationNumberBene = taxIdentificationNumberBene;
    }

    public BigDecimal getIdentificationNumberBene() {
        return identificationNumberBene;
    }

    public void setIdentificationNumberBene(BigDecimal identificationNumberBene) {
        this.identificationNumberBene = identificationNumberBene;
    }

    public DateParam getDateOfTax() {
        return dateOfTax;
    }

    public void setDateOfTax(DateParam dateOfTax) {
        this.dateOfTax = dateOfTax;
    }

    public DateParam getDateOfTaxDuty() {
        return dateOfTaxDuty;
    }

    public void setDateOfTaxDuty(DateParam dateOfTaxDuty) {
        this.dateOfTaxDuty = dateOfTaxDuty;
    }

    public DateParam getDateOfDue() {
        return dateOfDue;
    }

    public void setDateOfDue(DateParam dateOfDue) {
        this.dateOfDue = dateOfDue;
    }

    public BigDecimal getTaxBaseAmount() {
        return taxBaseAmount;
    }

    public void setTaxBaseAmount(BigDecimal taxBaseAmount) {
        this.taxBaseAmount = taxBaseAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxBaseReduced1Amount() {
        return taxBaseReduced1Amount;
    }

    public void setTaxBaseReduced1Amount(BigDecimal taxBaseReduced1Amount) {
        this.taxBaseReduced1Amount = taxBaseReduced1Amount;
    }

    public BigDecimal getTaxReduced1Amount() {
        return taxReduced1Amount;
    }

    public void setTaxReduced1Amount(BigDecimal taxReduced1Amount) {
        this.taxReduced1Amount = taxReduced1Amount;
    }

    public BigDecimal getTaxBaseReduced2Amount() {
        return taxBaseReduced2Amount;
    }

    public void setTaxBaseReduced2Amount(BigDecimal taxBaseReduced2Amount) {
        this.taxBaseReduced2Amount = taxBaseReduced2Amount;
    }

    public BigDecimal getTaxReduced2Amount() {
        return taxReduced2Amount;
    }

    public void setTaxReduced2Amount(BigDecimal taxReduced2Amount) {
        this.taxReduced2Amount = taxReduced2Amount;
    }

    public BigDecimal getNonTaxAmount() {
        return nonTaxAmount;
    }

    public void setNonTaxAmount(BigDecimal nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeUnits() {
        return exchangeUnits;
    }

    public void setExchangeUnits(BigDecimal exchangeUnits) {
        this.exchangeUnits = exchangeUnits;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public String getSoftwareOrigin() {
        return softwareOrigin;
    }

    public void setSoftwareOrigin(String softwareOrigin) {
        this.softwareOrigin = softwareOrigin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumberModel accountNumber) {
        this.accountNumber = accountNumber;
    }
}
