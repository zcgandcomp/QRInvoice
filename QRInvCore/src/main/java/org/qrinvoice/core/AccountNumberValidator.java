package org.qrinvoice.core;

import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by zcg on 2.7.2016.
 * Implementation of czech bank account numbers validation
 */

public class AccountNumberValidator implements ConstraintValidator<CheckAccountNumber, AccountNumber> {

    static Logger log = Logger.getLogger(AccountNumberValidator.class.getName());

    @Override
    public void initialize(CheckAccountNumber constraintAnnotation) {
        // empty initialization
    }


    private Integer[] baseWeights = new Integer[]{6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    /**
     * check weights of string
     *
     * @param numberPart part of account number to check
     * @param weights    weights to apply for checks
     * @return true if part is by spec otherwise false
     */
    private Boolean checkWeight(String numberPart, Integer[] weights) {

        Boolean result;
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

        Boolean result;

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

    @Override
    public boolean isValid(AccountNumber value, ConstraintValidatorContext context) {

        log.info("isValid:" + value + " context:" + context);

        if (value == null) {
            return true;
        }

        return isAccountNumberValid(value.getAccountPrefix(), value.getAccountBase(), value.getBankCode());


    }
}
