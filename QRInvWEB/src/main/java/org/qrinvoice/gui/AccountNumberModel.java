package org.qrinvoice.gui;

import org.qrinvoice.core.AccountNumber;

import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * Created by zcg on 2.7.2016.
 */
public class AccountNumberModel implements AccountNumber {
    @FormParam(value = "accountPrefix")
    @Size(max = 6)
    private String accountPrefix;

    @FormParam(value = "accountBase")
    @Size(max = 10)
    private String accountBase;


    @FormParam(value = "bankCode")
    @Size(max = 4)
    private String bankCode;

    @Override
    public String getAccountBase() {
        return null;
    }

    @Override
    public void setAccountBase(String accountBase) {

    }

    @Override
    public String getAccountPrefix() {
        return null;
    }

    @Override
    public void setAccountPrefix(String accountPrefix) {

    }

    @Override
    public String getBankCode() {
        return null;
    }

    @Override
    public void setBankCode(String bankCode) {

    }
}
