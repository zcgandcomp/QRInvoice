package org.qrinvoice.model;

import org.qrinvoice.core.AccountNumber;

import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;

/**
 * Created by zcg on 2.7.2016.
 */

public class AccountNumberModel implements AccountNumber {

    /**
     * Account number prefix
     */
    @QueryParam(value = "accountPrefix")
    @Size(max = 6)
    private String accountPrefix;

    /**
     * Base of account number
     */
    @QueryParam(value = "accountBase")
    @Size(max = 10)
    private String accountBase;


    /**
     * Code of bank
     */
    @QueryParam(value = "bankCode")
    @Size(max = 4)
    private String bankCode;

    @Override
    public String getAccountPrefix() {
        return accountPrefix;
    }

    @Override
    public void setAccountPrefix(String accountPrefix) {
        this.accountPrefix = accountPrefix;
    }

    @Override
    public String getAccountBase() {
        return accountBase;
    }

    @Override
    public void setAccountBase(String accountBase) {
        this.accountBase = accountBase;
    }

    @Override
    public String getBankCode() {
        return bankCode;
    }

    @Override
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public boolean isEmpty() {
        return accountPrefix == null && accountBase == null && bankCode == null;
    }

    @Override
    public String toString() {
        return "AccountNumberModel{" +
                "accountPrefix='" + accountPrefix + '\'' +
                ", accountBase='" + accountBase + '\'' +
                ", bankCode='" + bankCode + '\'' +
                '}';
    }
}
