package org.qrinvoice.core;

import java.io.IOException;

/**
 * Created by zcg on 11.7.2016.
 * identifies that QR code is not possible to generate
 */
public class ImageGenerationException extends Exception {

    public ImageGenerationException(String s) {
        super(s);
    }

    public ImageGenerationException(Throwable cause) {
        super(cause);
    }
}
