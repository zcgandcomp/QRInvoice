package org.qrinvoice.core;

import java.io.Serializable;

/**
 * Created by zcg on 2.7.2016.
 * Interface representing methods for fields used in bank account identification. Czech bank account has three parts prefix base and address code of bank
 *
 */
public interface AccountNumber extends Serializable {

    /**
     * @return base part of account number
     */
    String getAccountBase();

    /**
     * sets base part of account number
     * @param accountBase base part of account number
     */
    void setAccountBase(String accountBase);

    /**
     *
     * @return prefix of bank account number
     */
    String getAccountPrefix();

    /**
     * sets prefix of bank account number
     * @param accountPrefix prefix of bank account number
     */
    void setAccountPrefix(String accountPrefix);

    /**
     *
     * @return address code of bank
     */
    String getBankCode();

    /**
     * sets address code of bank
     * @param bankCode address code of bank
     */
    void setBankCode(String bankCode);

}

