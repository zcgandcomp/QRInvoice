package org.qrinvoice.core;

/**
 * Created by zcg on 29.5.2016.
 * Czech account number oobject, for validating and converison to IBAN
 */
public class AccountNumberImpl implements AccountNumber {

    private String mAccountPrefix;

    private String mAccountBase;

    private String mBankCode;

    public AccountNumberImpl() {
        // default constructor
    }

    public boolean isEmpty() {

        return (mAccountPrefix == null && mAccountBase == null && mBankCode == null);

    }

    public AccountNumberImpl(String accountPrefix, String accountBase, String bankCode) throws AccountNotValidException {

        AccountNumberValidator validator = new AccountNumberValidator();

        if (validator.isAccountNumberValid(accountPrefix, accountBase, bankCode)) {


            if (accountPrefix != null) {
                mAccountPrefix = String.format("%06d", Long.valueOf(accountPrefix));
            }

            if (accountBase != null) {
                mAccountBase = String.format("%010d", Long.valueOf(accountBase));
            }
            if (bankCode != null) {
                mBankCode = String.format("%04d", Long.valueOf(bankCode));
            }

        } else {

            throw new AccountNotValidException("invalid account number");

        }

    }


    /**
     * Computes the IBAN number
     *
     * @return An IBAN number.
     */
    public String computeIBAN() throws AccountNotValidException {
        // preprocess the numbers
        AccountNumberValidator validator = new AccountNumberValidator();

        if (!validator.isValid(this, null)) {
            throw new AccountNotValidException("Illegal acc number:" + getFormatedAccNumber());
        }

        // calculate the check sum
        String buf = mBankCode + mAccountPrefix + mAccountBase + "123500";
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

        // assign the checksum
        String checksum = String.format("%02d", pz);

        // build the IBAN number
        return "CZ" + checksum + mBankCode + mAccountPrefix + mAccountBase;
    }


    public String getAccountBase() {
        return mAccountBase;
    }

    public void setAccountBase(String accountBase) {

        this.mAccountBase = (accountBase != null) ? String.format("%010d", Long.valueOf(accountBase)) : null;
    }

    public String getAccountPrefix() {
        return mAccountPrefix;
    }

    public void setAccountPrefix(String accountPrefix) {


        this.mAccountPrefix = (accountPrefix != null) ? String.format("%06d", Long.valueOf(accountPrefix)) : null;
    }

    public String getBankCode() {
        return mBankCode;
    }

    public void setBankCode(String bankCode) {
        this.mBankCode = (bankCode != null) ? String.format("%04d", Long.valueOf(bankCode)) : null;
    }

    public String getFormatedAccNumber() {
        return mAccountPrefix + "-" + mAccountBase + "/" + mBankCode;
    }
}
