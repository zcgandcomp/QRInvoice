package org.qrinvoice.core;

/**
 * Created by zcg on 2.7.2016.
 */
public interface AccountNumber {

    String getAccountBase();

    void setAccountBase(String accountBase);

    String getAccountPrefix();

    void setAccountPrefix(String accountPrefix);

    String getBankCode();

    void setBankCode(String bankCode);

}

