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
 * Holder for query parameter for detail see http://qr-faktura.cz/popis-formatu
 *
 *
 */

public class InvoiceModel implements Serializable {


    //ID
    /**
     * ID of generated invoice
     */
    @QueryParam(value = "id")
    @NotNull
    @Size(max = 40)
    private String id;


    //DD
    /**
     * Date of issue of invoice
     */
    @QueryParam(value = "DD")
    @NotNull
    private DateParam dateOfIssue;

    /**
     * Total amount to pay in @see CC currency
     */
    //AM
    @QueryParam(value = "AM")
    @Digits(integer = 15, fraction = 2)
    @NotNull
    private BigDecimal totalAmount;

    /**
     * Identification of type of taxable event
     */
    //TP
    @DefaultValue("0")
    @QueryParam(value = "TP")
    @Digits(integer = 1, fraction = 0)
    private Byte typeOfTaxTransaction;

    /**
     * Type of tax document
     */
    //TD
    @QueryParam(value = "TD")
    @Digits(integer = 1, fraction = 0)
    private Byte typeOfTaxDocument;

    /**
     * Flag if tax advance payments are on invoice
     */
    //SA
    @DefaultValue("0")
    @QueryParam(value = "SA")
    @Digits(integer = 1, fraction = 0)
    private Byte taxAdvance;

    /**
     * Text description of subject of invoice
     */
    //MSG
    @QueryParam(value = "MSG")
    @Size(max = 40)
    private String message;

    /**
     * Number of order
     */
    //ON
    @QueryParam(value = "ON")
    @Size(max = 20)
    private String orderNumber;

    /**
     * Variable symbol
     */
    //VS
    @QueryParam(value = "VS")
    @Size(max = 10)
    @Pattern(regexp = "^([0-9]{0,10})$")
    private String variableString;

    /**
     * Tax identification number of issuer - DIČ
     */
    // VII
    @QueryParam(value = "VII")
    @Size(max = 14)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String taxIdentificationNumberIssuer;

    /**
     * Identification number of issuer - IČO
     */
    // INI
    @QueryParam(value = "INI")
    @Digits(integer = 8, fraction = 0)
    private BigDecimal identificationNumberIssuer;

    /**
     * Tax identification number of recipient - DIČ
     */
    //VIR
    @QueryParam(value = "VIR")
    @Size(max = 14)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String taxIdentificationNumberRecipient;

    /**
     * Identification number of recipient - IČO
     */
    //INR
    @QueryParam(value = "INR")
    @Digits(integer = 8, fraction = 0)
    private BigDecimal identificationNumberRecipient;

    /**
     * Date of taxable event
     */
    //DUZP
    @QueryParam(value = "DUZP")
    private DateParam dateOfTax;


    /**
     * Date of duty to declare tax
     */
    //DPPD
    @QueryParam(value = "DPPD")
    private DateParam dateOfTaxDuty;

    /**
     * Date of due of total amount
     */
    //DT
    @QueryParam(value = "DT")
    private DateParam dateOfDue;

    /**
     * Tax base
     */
    //TB0
    @QueryParam(value = "TB0")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseAmount;

    /**
     * Tax amount in base tax rate
     */
    // T0
    @QueryParam(value = "T0")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxAmount;

    /**
     * Tax base in first reduced rate
     */
    //TB1
    @QueryParam(value = "TB1")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseReduced1Amount;

    /**
     * Tax amount in first reduced rate
     */
    //T1
    @QueryParam(value = "T1")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxReduced1Amount;

    /**
     * Tax base in second reduced rate
     */
    //TB2
    @QueryParam(value = "TB2")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxBaseReduced2Amount;

    /**
     * Tax amount in second reduced rate
     */
    //T2
    @QueryParam(value = "T2")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal taxReduced2Amount;

    /**
     * Amount of non taxable event
     */
    //NTB
    @QueryParam(value = "NTB")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal nonTaxAmount;

    /**
     * Currency of total amount
     */
    //CC
    @DefaultValue("CZK")
    @QueryParam(value = "CC")
    @Size(max = 3)
    @Pattern(regexp = "^[A-Z]*$")
    private String currencyCode;

    /**
     * Exchange rate between CZK currency and currency of total amount
     */
    //FX
    @QueryParam(value = "FX")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal exchangeRate;

    /**
     * Currency units
     */
    //FXA
    @DefaultValue("1")
    @QueryParam(value = "FXA")
    @Digits(integer = 5, fraction = 0)
    private BigDecimal exchangeUnits;

    // special attributes

    /**
     * Account number in czech format
     */
    // TODO create in producer
    @CheckAccountNumber
    @Valid
    @BeanParam
    private AccountNumberModel accountNumber = new AccountNumberModel();

    /**
     * IBAN number
     */
    //ACC
    @QueryParam(value = "ACC")
    @Size(max = 34)
    private String IBAN;

    /**
     * BIC code of bank
     */
    // no code
    @QueryParam(value = "BIC")
    @Size(max = 11)
    private String BIC;

    /**
     * Identification of Software
     */
    //X-SW
    @QueryParam(value = "X-SW")
    @Size(max = 30)
    private String softwareOrigin;

    /**
     * URL to electronic document of invoice
     */
    //X-URL
    @QueryParam(value = "X-URL")
    @Size(max = 70)
    private String url;

    /**
     * URL from spayd specification
     */
    //X-URL
    @Size(max = 70)
    @QueryParam(value = "SPAYD-URL")
    private String spaydUrl;

    /**
     * Message for beneficiary from spayd specification
     */
    //MSG
    @Size(max = 40)
    @QueryParam(value = "SPAYD-MSG")
    private String spaydMessage;


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

    public Byte getTypeOfTaxTransaction() {
        return typeOfTaxTransaction;
    }

    public void setTypeOfTaxTransaction(Byte typeOfTaxTransaction) {
        this.typeOfTaxTransaction = typeOfTaxTransaction;
    }

    public Byte getTypeOfTaxDocument() {
        return typeOfTaxDocument;
    }

    public void setTypeOfTaxDocument(Byte typeOfTaxDocument) {
        this.typeOfTaxDocument = typeOfTaxDocument;
    }

    public Byte getTaxAdvance() {
        return taxAdvance;
    }

    public void setTaxAdvance(Byte taxAdvance) {
        this.taxAdvance = taxAdvance;
    }

    public String getVariableString() {
        return variableString;
    }

    public void setVariableString(String variableString) {
        this.variableString = variableString;
    }

    public String getTaxIdentificationNumberIssuer() {
        return taxIdentificationNumberIssuer;
    }

    public void setTaxIdentificationNumberIssuer(String taxIdentificationNumberIssuer) {
        this.taxIdentificationNumberIssuer = taxIdentificationNumberIssuer;
    }

    public BigDecimal getIdentificationNumberIssuer() {
        return identificationNumberIssuer;
    }

    public void setIdentificationNumberIssuer(BigDecimal identificationNumberIssuer) {
        this.identificationNumberIssuer = identificationNumberIssuer;
    }

    public String getTaxIdentificationNumberRecipient() {
        return taxIdentificationNumberRecipient;
    }

    public void setTaxIdentificationNumberRecipient(String taxIdentificationNumberRecipient) {
        this.taxIdentificationNumberRecipient = taxIdentificationNumberRecipient;
    }

    public BigDecimal getIdentificationNumberRecipient() {
        return identificationNumberRecipient;
    }

    public void setIdentificationNumberRecipient(BigDecimal identificationNumberRecipient) {
        this.identificationNumberRecipient = identificationNumberRecipient;
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

    @Override
    public String toString() {
        return "InvoiceModel{" +
                "id='" + id + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", totalAmount=" + totalAmount +
                ", typeOfTaxTransaction=" + typeOfTaxTransaction +
                ", typeOfTaxDocument=" + typeOfTaxDocument +
                ", taxAdvance=" + taxAdvance +
                ", message='" + message + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", variableString='" + variableString + '\'' +
                ", taxIdentificationNumberIssuer='" + taxIdentificationNumberIssuer + '\'' +
                ", identificationNumberIssuer=" + identificationNumberIssuer +
                ", taxIdentificationNumberRecipient='" + taxIdentificationNumberRecipient + '\'' +
                ", identificationNumberRecipient=" + identificationNumberRecipient +
                ", dateOfTax=" + dateOfTax +
                ", dateOfTaxDuty=" + dateOfTaxDuty +
                ", dateOfDue=" + dateOfDue +
                ", taxBaseAmount=" + taxBaseAmount +
                ", taxAmount=" + taxAmount +
                ", taxBaseReduced1Amount=" + taxBaseReduced1Amount +
                ", taxReduced1Amount=" + taxReduced1Amount +
                ", taxBaseReduced2Amount=" + taxBaseReduced2Amount +
                ", taxReduced2Amount=" + taxReduced2Amount +
                ", nonTaxAmount=" + nonTaxAmount +
                ", currencyCode='" + currencyCode + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", exchangeUnits=" + exchangeUnits +
                ", accountNumber=" + accountNumber +
                ", IBAN='" + IBAN + '\'' +
                ", BIC='" + BIC + '\'' +
                ", softwareOrigin='" + softwareOrigin + '\'' +
                ", url='" + url + '\'' +
                ", spaydUrl='" + spaydUrl + '\'' +
                ", spaydMessage='" + spaydMessage + '\'' +
                '}';
    }
}
