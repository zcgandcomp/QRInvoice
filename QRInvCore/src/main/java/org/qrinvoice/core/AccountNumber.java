package org.qrinvoice.core;

/**
 * Created by zcg on 29.5.2016.
 * Czech account number oobject, for validating and converison to IBAN
 */
public class AccountNumber {

    private String mAccountPrefix;

    private String mAccountBase;

    private String mBankCode;

    public AccountNumber() {
        // default constructor
    }

    public boolean isEmpty() {

        return (mAccountPrefix == null && mAccountBase == null && mBankCode == null);

    }

    public AccountNumber(String accountPrefix, String accountBase, String bankCode) throws AccountNotValidException {

        if (isAccountNumberValid(accountPrefix, accountBase, bankCode)) {


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
     * weight mask for
     */
    //private Integer[] prefixWeights = new Integer[]{1, 2, 4, 8, 5, 10};


    private Integer[] baseWeights = new Integer[]{6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    /**
     * check weights of string
     *
     * @param numberPart part of account number to check
     * @param weights    weights to apply for checks
     * @return true if part is by spec otherwise false
     */
    private Boolean checkWeight(String numberPart, Integer[] weights) {

        Boolean result = true;
        int sum = 0;
        // count shift so we can loop string backwards and count weights in each position
        int shift = weights.length - numberPart.length();


        for (int i = weights.length - 1; i >= shift; i--) {

            sum += Character.getNumericValue(numberPart.charAt(i - shift)) * weights[i];

        }

        result = (sum % 11) == 0;

        return result;

    }

    /**
     * validate if prefix is by spec
     *
     * @param accountPrefix prefix param
     * @return true if valid otherwise false
     */
    private Boolean validateAccountPrefix(String accountPrefix) {

        Boolean result = true;

        // prefix is optional so if is ommited it's fine
        if (accountPrefix == null) {
            return true;
        }

        // validate if each arguments contains only digits
        result = accountPrefix.matches("\\d*");


        if (result) {
            result = checkWeight(accountPrefix, baseWeights);

        }


        return result;
    }

    /**
     * validate if base by spec
     *
     * @param accountBase base param
     * @return true if valid otherwise false
     */
    private Boolean validateAccountBase(String accountBase) {

        // base is mandatory
        if (accountBase == null) {
            return false;
        }
        // only digits are allowed
        Boolean result = accountBase.matches("\\d*");

        // check on modulo
        if (result) {
            result = checkWeight(accountBase, baseWeights);

        }

        return result;
    }

    /**
     * validate if bank code is by spec - ie 4 chars
     *
     * @param bankCode
     * @return true if bank code is 4 chars long, otherwise false
     */
    private Boolean validateBankCode(String bankCode) {

        if (bankCode == null) {
            return false;
        }

        if (bankCode.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAccountNumberValid() {

        return isAccountNumberValid(mAccountPrefix, mAccountBase, mBankCode);

    }

    /**
     * validate account number with code of bank
     *
     * @param accountPrefix prefix of bank account - coild be null
     * @param accountBase   base of account number - is mandatory
     * @param bankCode      code of bank - is mandatory
     * @return true if code is by spec otherwise false
     */
    public Boolean isAccountNumberValid(String accountPrefix, String accountBase, String bankCode) {

        Boolean result = true;

        // everything empty is valid
        if (accountPrefix == null && accountBase == null && bankCode == null) {
            return true;
        }

        // if anything is not null acccount number base and bank code are mandatory
        if (accountBase == null || bankCode == null) {
            result = false;
        }


        // validate modulo 11 on prefix
        if (result) {

            result = validateAccountPrefix(accountPrefix);
        }

        // validate modulo 11 on base
        if (result) {
            result = validateAccountBase(accountBase);
        }

        if (result) {
            result = validateBankCode(bankCode);
        }

        return result;

    }


    /**
     * Computes the IBAN number
     *
     * @return An IBAN number.
     */
    public String computeIBAN() throws AccountNotValidException {
        // preprocess the numbers
        if (!isAccountNumberValid()) {
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
