package org.qrinvoice.core;

/**
 * Created by zcg on 29.5.2016.
 * constants used for QR invoice generation
 */
public class Constants {


    /**
     * minimum size for QR code
     */
    public static final Integer MIN_QR_SIZE = 200;
    /**
     * dfault size of QR code
     */
    public static final Integer DEF_QR_SIZE = 250;
    /**
     * maximum side of QR code
     */
    public static final Integer MAX_QR_SIZE = 800;

    public static final String QR_INVOICE = "QR Faktura";

    public static final String QR_PAYMENT_INVOICE = "QR Platba+F";

    // hidden default constructor
    private Constants() {
    }
}
