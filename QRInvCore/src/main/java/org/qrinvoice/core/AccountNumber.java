package org.qrinvoice.core;

import java.io.Serializable;

/**
 * Created by zcg on 2.7.2016.
 */
public interface AccountNumber extends Serializable {

    String getAccountBase();

    void setAccountBase(String accountBase);

    String getAccountPrefix();

    void setAccountPrefix(String accountPrefix);

    String getBankCode();

    void setBankCode(String bankCode);

}

