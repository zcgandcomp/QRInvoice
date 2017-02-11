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

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

import static org.qrinvoice.core.Constants.*;
import static org.qrinvoice.core.InvoiceAttributesConstants.*;

/**
 * Created by zcg on 29.5.2016.
 * generate image with QR code
 */
public class Generator {

    static Logger log = Logger.getLogger(Generator.class.getName());


    private static final Set<String> COMMON_KEYS = new HashSet<>(Arrays.asList(ACC, AM, CC, DT, VS, X_URL_SPAYD, MSG_SPAYD));

    private BufferedImage createBar(int barsize, int size, int h, int w, IntegrationModeEnum mode, BufferedImage image) {

        Graphics2D g = (Graphics2D) image.getGraphics();

        BasicStroke bs = new BasicStroke(2);
        g.setStroke(bs);
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, w, 0);
        g.drawLine(0, 0, 0, h);
        g.drawLine(w, 0, w, h);
        g.drawLine(0, h, w, h);

        String str = (mode == IntegrationModeEnum.INVOICE_MODE) ? QR_INVOICE : QR_PAYMENT_INVOICE;

        int fontSize = size / 12;

        g.setFont(new Font("Verdana", Font.BOLD, fontSize));

        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(str, g);

        g.setColor(Color.WHITE);
        g.fillRect(2 * barsize, h - fm.getAscent(), (int) rect.getWidth() + 4 * barsize, (int) rect.getHeight());

        int padding = 4 * barsize;

        BufferedImage paddedImage = new BufferedImage(w + 2 * padding, h + padding + (int) rect.getHeight(), image.getType());
        Graphics2D g2 = paddedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2.setFont(new Font("Verdana", Font.BOLD, fontSize));
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, paddedImage.getWidth(), paddedImage.getHeight());
        g2.drawImage(image, padding, padding, Color.WHITE, null);

        g2.setColor(Color.BLACK);
        g2.drawString(str, padding + 4 * barsize, (int) (padding + h + rect.getHeight() - barsize));

        return paddedImage;

    }

    /**
     * create byte stream holding data of QR code image, generated from @param invoiceData
     * copied from spayd implementation, because spayd implementation cannot be extended to another branding
     *
     * @param sizeParam   size of desired QR code, if null default is used @see Constants.DEF_QR_SIZE
     * @param invoiceData String with QR data
     * @param mode pure QR invoice or integrated with SPAYD
     * @param hasBranding    specify if QR image has branding and which type
     * @return byte stream with image
     * @throws ImageGenerationException in case encoding or any other IOException occurs
     */
    public BufferedImage getQRCode(Integer sizeParam, String invoiceData, IntegrationModeEnum mode, boolean hasBranding) throws ImageGenerationException {


        Integer size = sizeParam;
        if (size == null) {
            size = DEF_QR_SIZE;
        } else if (size < MIN_QR_SIZE) {
            size = MIN_QR_SIZE;
        } else if (size > MAX_QR_SIZE) {
            size = MAX_QR_SIZE;
        }

        BitMatrix matrix;
        int h = size;
        int w = size;
        int barsize;

        Writer writer = new MultiFormatWriter();
        // check if encoding is correct
        if (!Charset.forName("ISO-8859-1").newEncoder().canEncode(invoiceData)) {
            throw new ImageGenerationException("Invalid charset - only ISO-8859-1 characters must be used");
        }

        //"^([A-Z]{2,2}[0-9]{2,2}[A-Z0-9]+)(\\+([A-Z0-9]+))?(,([A-Z]{2,2}[0-9]+)(\\+([A-Z0-9]+))?)*$"

        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
            QRCode code = Encoder.encode(invoiceData, ErrorCorrectionLevel.M, hints);

            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            barsize = size / (code.getMatrix().getWidth() + 8);
            matrix = writer.encode(invoiceData, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);

            if (matrix == null || barsize < 0) {
                throw new ImageGenerationException("invalid format");
            }

            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            // we have some specific branding  QR invoice  - 0 or SPAYD - 1
            if (hasBranding) {

                image = createBar(barsize, size, h, w, mode, image);
            }

            return image;

        } catch (WriterException e) {
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
        StringBuilder working = new StringBuilder();


        for (int i = 0; i < originalString.length(); i++) {
            if (originalString.charAt(i) > 127) { // escape non-ascii characters
                working.append(URLEncoder.encode(Character.toString(originalString.charAt(i)), "UTF-8"));
            } else {
                if (originalString.charAt(i) == '*') { // star is a special character for the SPAYD format
                    working .append( "%2A");
                } else if (originalString.charAt(i) == '+') { // plus is a special character for URL encode
                    working .append( "%2B");
                } else if (originalString.charAt(i) == '%') { // percent is an escape character
                    working .append( "%25");
                } else {
                    working .append( originalString.charAt(i)); // ascii characters may be used as expected
                }
            }
        }
        return working.toString();
    }


    protected void addValue(StringBuilder buf, String constant, Object value, boolean transliterate) throws UnsupportedEncodingException {
        if (value != null) {

            if (value instanceof String) {
                String innerValue = (String) value;
                if (transliterate) {
                    innerValue = Junidecode.unidecode(innerValue.toUpperCase());
                }

                addValue(buf, constant, escapeDisallowedCharacters(innerValue));
            } else {

                addValue(buf, constant, value);
            }
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


    /**
     * Creates valid String holding data for QR code generation. Get fields from data holder. Method encode string parameter as needed, but does no validation as validation itself should be done by bean validation on holder object
     *
     * @param invoice       Invoice is DTO with setted parram
     * @param transliterate identifies if returned string has to have capitalized letters
     * @param mode pure QR invoice or integrated with SPAYD
     * @return String holding data for QR code generation
     * @throws UnsupportedEncodingException if String parameters cannot be encoded
     */
    public String getInvoiceString(InvoiceParamDomain invoice, boolean transliterate, IntegrationModeEnum mode) throws UnsupportedEncodingException {

        StringBuilder buf = new StringBuilder();

        // create secondary buffer for encoded spayd
        StringBuilder spaydBuf = new StringBuilder();

        if (IntegrationModeEnum.INVOICE_MODE == mode) {
            // start standard buffer with mode prefix
            buf.append(PREFIX);

        } else if (IntegrationModeEnum.SPAYD_MODE == mode) {
            // start standard buffer with mode prefix
            buf.append(SPAYD_PREFIX);

            // start special buffer for non spayd attributes
            spaydBuf.append(PREFIX);
            spaydBuf.append(VERSION);
            spaydBuf.append(ATTR_SEPARATOR);
        }

        buf.append(VERSION);

        buf.append(ATTR_SEPARATOR);

        Map<String, Object> attributes = invoice.getParamMap();

        for (Map.Entry<String, Object> entry : attributes.entrySet()) {

            if (IntegrationModeEnum.INVOICE_MODE.equals(mode)) {
                // standard mode put everything into standard buffer
                addValue(buf, entry.getKey(), entry.getValue(), transliterate);
            } else if (COMMON_KEYS.contains(entry.getKey())) {
                // spayd mode and special character - perform transformation
                if (VS.equals(entry.getKey())) {
                    // change to spayd key
                    addValue(buf, X_VS, entry.getValue(), transliterate);
                } else if (MSG_SPAYD.equals(entry.getKey())) {
                    // change to QR invoice key
                    addValue(buf, MSG, entry.getValue(), transliterate);
                } else if (X_URL_SPAYD.equals(entry.getKey())) {
                    // change to QR invoice key
                    addValue(buf, X_URL, entry.getValue(), transliterate);
                } else {
                    // key is same in QR invoice and spayd put into standard buffer
                    addValue(buf, entry.getKey(), entry.getValue(), transliterate);
                }
            } else {
                addValue(spaydBuf, entry.getKey(), entry.getValue(), transliterate);
            }
        }

        // add specially encoded invoice
        if (IntegrationModeEnum.SPAYD_MODE == mode) {
            buf.append(X_INV);
            buf.append(ATTR_EQ);
            buf.append(escapeDisallowedCharacters(spaydBuf.toString()));
        }

        // TODO add CRC32

        log.info("getInvoiceString:" + buf);
        return buf.toString();

    }

}