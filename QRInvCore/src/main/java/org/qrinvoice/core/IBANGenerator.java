package org.qrinvoice.core;

import org.apache.commons.lang.StringUtils;

/**
 * Created by zcg on 29.5.2016.
 */
public class IBANGenerator {


    /**
     */
    public static String computeIBANFromCzechBankAccount(String accountPrefix, String accountNumber, String bankCode) {
        // preprocess the numbers

        String prefix = StringUtils.leftPad(accountPrefix, 6);
        String number = StringUtils.leftPad(accountNumber, 10);
        String bank = StringUtils.leftPad(bankCode, 4);


        // calculate the check sum
        String buf = bank + prefix + number + "123500";
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
        return "CZ" + checksum + bank + prefix + number;
    }

}

