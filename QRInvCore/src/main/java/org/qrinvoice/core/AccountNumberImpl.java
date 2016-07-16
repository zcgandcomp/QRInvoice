package org.qrinvoice.core;

import org.apache.log4j.Logger;

/**
 * Created by zcg on 29.5.2016.
 * Czech account number object, for validating and converison to IBAN
 */
public class AccountNumberImpl implements AccountNumber {

    static Logger log = Logger.getLogger(AccountNumberImpl.class.getName());
    private String accountPrefix;

    private String accountBase;

    private String bankCode;

    public AccountNumberImpl() {
        // default constructor
    }


    public AccountNumberImpl(String accountPrefix, String accountBase, String bankCode) throws AccountNotValidException {

        AccountNumberValidator validator = new AccountNumberValidator();

        if (validator.isAccountNumberValid(accountPrefix, accountBase, bankCode)) {


            if (accountPrefix != null) {
                this.accountPrefix = String.format("%06d", Long.valueOf(accountPrefix));
            }

            if (accountBase != null) {
                this.accountBase = String.format("%010d", Long.valueOf(accountBase));
            }
            if (bankCode != null) {
                this.bankCode = String.format("%04d", Long.valueOf(bankCode));
            }

        } else {

            throw new AccountNotValidException("invalid account number");

        }

    }


    public boolean isEmpty() {

        return accountPrefix == null && accountBase == null && bankCode == null;

    }

    private int computeIBANChecksum(StringBuilder buf) {
        int index = 0;
        String dividend;
        int pz = -1;
        while (index <= buf.length()) {
            if (pz < 0) {
                dividend = buf.substring(index, Math.min(index + 9, buf.length()));
                index += 9;
            } else if (pz >= 0 && pz <= 9) {
                dividend = pz + buf.substring(index, Math.min(index + 8, buf.length()));
                index += 8;
            } else {
                dividend = pz + buf.substring(index, Math.min(index + 7, buf.length()));
                index += 7;
            }
            pz = Integer.valueOf(dividend) % 97;
        }
        pz = 98 - pz;

        return pz;
    }
    /**
     * Computes the IBAN number
     *
     * @return An IBAN number.
     */
    // TODO refactor
    public String computeIBAN() throws AccountNotValidException {
        // preprocess the numbers
        AccountNumberValidator validator = new AccountNumberValidator();

        if (!validator.isValid(this, null)) {
            throw new AccountNotValidException("Illegal acc number:" + getFormatedAccNumber());
        }

        // if no attributes are filled return null
        if (bankCode == null && accountBase == null && accountPrefix == null) {
            return null;
        }

        // calculate the check sum
        StringBuilder buf = new StringBuilder();

        buf.append(bankCode);
        if (accountPrefix != null) {
            buf.append(accountPrefix);
        } else {
            buf.append("000000");
        }
        buf.append(accountBase);
        buf.append("123500");

        int pz = computeIBANChecksum(buf);

        // assign the checksum
        String checksum = String.format("%02d", pz);

        // build the IBAN number
        return "CZ" + checksum + bankCode + ((accountPrefix != null) ? accountPrefix : "000000") + accountBase;
    }

    @Override
    public String getAccountBase() {
        return accountBase;
    }

    @Override
    public void setAccountBase(String accountBase) {

        this.accountBase = (accountBase != null) ? String.format("%010d", Long.valueOf(accountBase)) : null;
    }

    @Override
    public String getAccountPrefix() {
        return accountPrefix;
    }

    @Override
    public void setAccountPrefix(String accountPrefix) {


        this.accountPrefix = (accountPrefix != null) ? String.format("%06d", Long.valueOf(accountPrefix)) : null;
    }

    @Override
    public String getBankCode() {
        return bankCode;
    }

    @Override
    public void setBankCode(String bankCode) {
        this.bankCode = (bankCode != null) ? String.format("%04d", Long.valueOf(bankCode)) : null;
    }

    public String getFormatedAccNumber() {
        return accountPrefix + "-" + accountBase + "/" + bankCode;
    }
}
