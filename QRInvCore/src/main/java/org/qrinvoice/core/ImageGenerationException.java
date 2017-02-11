package org.qrinvoice.core;

/**
 * Created by zcg on 11.7.2016.
 * identifies that QR code is not possible to generate
 */
public class ImageGenerationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 44304375987399953L;

	public ImageGenerationException(String s) {
        super(s);
    }

    public ImageGenerationException(Throwable cause) {
        super(cause);
    }
}
