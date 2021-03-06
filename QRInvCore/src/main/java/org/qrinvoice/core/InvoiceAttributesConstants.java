package org.qrinvoice.core;

/**
 * Created by zcg on 5.6.2016.
 *
 * @see http://qr-faktura.cz/popis-formatu
 */
// TODO make it an enum
public class InvoiceAttributesConstants {


    public static final String PREFIX = "SID*";

    public static final String SPAYD_PREFIX = "SPD*";

    // TODO move it to maven dependencies
    public static final String VERSION = "1.0";

    public static final String ATTR_SEPARATOR = "*";

    public static final String ATTR_EQ = ":";

    public static final String ID = "ID";

    public static final String DD = "DD";

    public static final String AM = "AM";

    public static final String TP = "TP";

    public static final String TD = "TD";

    public static final String SA = "SA";

    public static final String MSG = "MSG";

    public static final String ON = "ON";

    public static final String VS = "VS";

    public static final String X_VS = "X-VS";

    public static final String VII = "VII";

    public static final String INI = "INI";

    public static final String VIR = "VIR";

    public static final String INR = "INR";

    public static final String DUZP = "DUZP";

    public static final String DPPD = "DPPD";

    public static final String DT = "DT";

    public static final String TB0 = "TB0";

    public static final String T0 = "T0";
    public static final String TB1 = "TB1";

    public static final String T1 = "T1";
    public static final String TB2 = "TB2";
    public static final String T2 = "T2";
    public static final String NTB = "NTB";
    public static final String CC = "CC";
    public static final String FX = "FX";
    public static final String FXA = "FXA";
    public static final String ACC = "ACC";

    // special attributes
    public static final String X_SW = "X-SW";

    public static final String X_URL = "X-URL";

    public static final String X_INV = "X-INV";

    public static final String CRC32 = "CRC32";


    public static final String X_URL_SPAYD = "X-URL-SPAYD";

    public static final String CRC32_SPAYD = "CRC32-SPAYD";

    public static final String MSG_SPAYD = "MSG-SPAYD";


    // hidden default constructor
    private InvoiceAttributesConstants() {}


}
