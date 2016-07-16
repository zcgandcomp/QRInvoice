package org.qrinvoice.core;

/**
 * Created by zcg on 16.7.2016.
 */
public enum IntegrationModeEnum {


    INVOICE_MODE, SPAYD_MODE;

    public static IntegrationModeEnum fromString(final String s) {

        return IntegrationModeEnum.valueOf(s);

    }
}
