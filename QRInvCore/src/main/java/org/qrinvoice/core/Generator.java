package org.qrinvoice.core;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import net.sf.junidecode.Junidecode;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.EnumMap;
import java.util.Map;

import static org.qrinvoice.core.InvoiceAttributesConstants.*;

/**
 * Created by zcg on 29.5.2016.
 * generate image with QR code
 */
public class Generator {

    static Logger log = Logger.getLogger(Generator.class.getName());

    public ByteArrayOutputStream getQRCode(Integer sizeParam, String invoiceData) throws ImageGenerationException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Integer size = sizeParam;
        if (size == null) {
            size = Constants.DEF_QR_SIZE;
        } else if (size < Constants.MIN_QR_SIZE) {
            size = Constants.MIN_QR_SIZE;
        } else if (size > Constants.MAX_QR_SIZE) {
            size = Constants.MAX_QR_SIZE;
        }

        BitMatrix matrix;
        int h = size;
        int w = size;

        Writer writer = new MultiFormatWriter();
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            matrix = writer.encode(invoiceData, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);

            if (matrix == null ) {
                throw new ImageGenerationException("invalid format");
            }

            MatrixToImageWriter.writeToStream(matrix, "png", out);
            return out;

        } catch (WriterException|IOException e) {
            log.fatal("error generating QR code", e);
            throw new ImageGenerationException(e);

        }


    }

    // TODO refactor and rewrite

    /**
     * Escapes the payment string value in a correct way
     *
     * @param originalString The original non-escaped value
     * @return A string with percent encoded characters
     * @throws UnsupportedEncodingException Uses UTF-8 Encoding
     */
    public String escapeDisallowedCharacters(String originalString) throws UnsupportedEncodingException {
        String working = "";


        for (int i = 0; i < originalString.length(); i++) {
            if (originalString.charAt(i) > 127) { // escape non-ascii characters
                working += URLEncoder.encode( Character.toString(originalString.charAt(i)), "UTF-8");
            } else {
                if (originalString.charAt(i) == '*') { // star is a special character for the SPAYD format
                    working += "%2A";
                } else if (originalString.charAt(i) == '+') { // plus is a special character for URL encode
                    working += "%2B";
                } else if (originalString.charAt(i) == '%') { // percent is an escape character
                    working += "%25";
                } else {
                    working += Character.toString(originalString.charAt(i)); // ascii characters may be used as expected
                }
            }
        }
        return working;
    }


    protected void addValue(StringBuilder buf, String constant, String value, boolean transliterate) throws UnsupportedEncodingException {
        if (value != null) {
            String innerValue ;
            if (transliterate) {
                innerValue = Junidecode.unidecode(value.toUpperCase());
            } else {
                innerValue = value;
            }
            addValue(buf, constant, escapeDisallowedCharacters(innerValue));
        }
    }

    protected void addValue(StringBuilder buf, String constant, Object value) {

        if (value != null) {
            buf.append(constant);
            buf.append(ATTR_EQ);
            buf.append(value);
            buf.append(ATTR_SEPARATOR);
        }
    }


    public String getInvoiceString(InvoiceParamDomain invoice, boolean transliterate) throws UnsupportedEncodingException {

        StringBuilder buf = new StringBuilder();

        buf.append(PREFIX);

        buf.append(VERSION);

        buf.append(ATTR_SEPARATOR);

        Map<String, Object> attributes = invoice.getParamMap();

        // TODO validate account

        // TODO make position in QR string static ?
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            if (entry.getValue() instanceof String) {
                addValue(buf, entry.getKey(), (String) entry.getValue(), transliterate);
            } else {
                addValue(buf, entry.getKey(), entry.getValue());
            }
        }
        // TODO add CRC32

        log.info("getInvoiceString:"+buf);
        return buf.toString();

    }

}