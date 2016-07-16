package org.qrinvoice.rest;

import org.apache.log4j.Logger;
import org.qrinvoice.core.*;
import org.qrinvoice.model.InvoiceMapper;
import org.qrinvoice.model.InvoiceModel;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by zcg on 12.6.2016.
 * Resource for QR invoice REST API
 */

@Path("/generator")
public class QRInvoiceGeneratorResource {

    /**
     * content type for string representing QR payment - in case when downloaded as file
     */
    public static final String FILE_MEDIA_TYPE = "application/x-shortinvoicedescriptor";
    public static final String IMAGE_MEDIA_TYPE = "image/png";


    static Logger log = Logger.getLogger(QRInvoiceGeneratorResource.class.getName());

    /**
     * Returns String representing QR payment, make basic validation of input params
     *
     * @param model         bean representing query params
     * @param transliterate identifies if returned string has to have capitalized letters
     * @param mode          indicates which mode of QR should be used, pure QR invoice or integrated with SPAYD
     * @return string with QR code data
     */
    @GET
    @Path("string")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String generateInvoiceString(@BeanParam @Valid InvoiceModel model, @QueryParam("transliterate") @DefaultValue("true") Boolean transliterate, @QueryParam("mode") @DefaultValue("INVOICE_MODE") IntegrationModeEnum mode) {

        log.info("generateInvoiceString, model:" + model);


        log.info("transliterate:" + transliterate);

        log.info("mode:" + mode);

        validateInvoiceString(model, mode);

        try {

            InvoiceParamDomain param = InvoiceMapper.INSTANCE.invoiceModelToInvoiceParam(model);
            Generator generator = new Generator();


            if (param.getIBAN() == null) {

                AccountNumberImpl accNum = new AccountNumberImpl(model.getAccountNumber().getAccountPrefix(), model.getAccountNumber().getAccountBase(), model.getAccountNumber().getBankCode());

                param.setIBAN(accNum.computeIBAN());

            }
            return generator.getInvoiceString(param, transliterate, mode);

        } catch (UnsupportedEncodingException ex) {
            log.fatal("unable to encode params", ex);
            throw new ValidationException("{org.qrinvoice.core.UnsupportedEncodingException}");
        } catch (AccountNotValidException e) {
            log.fatal("invalid account number", e);
            throw new ValidationException("{org.qrinvoice.core.AccountNotValidException}");
        }

    }

    /**
     * validate specialities which are not handled by bean validation
     * TODO create validation for InvoiceModel itself
     *
     * @param model         bean representing query params
     * @param mode          indicates which mode of QR should be used, pure QR invoice or integrated with SPAYD
     * @return
     */
    @GET
    @Path("validator")
    @Produces(MediaType.TEXT_PLAIN)
    public String validateInvoiceString(@BeanParam @Valid InvoiceModel model, @QueryParam("mode") @DefaultValue("INVOICE_MODE") IntegrationModeEnum mode) {

        log.info("validateInvoiceString" + model);
        AccountNumberValidator validator = new AccountNumberValidator();

        if (!validator.isValid(model.getAccountNumber(), null)) {

            log.fatal("account not valid:" + model.getAccountNumber());
            throw new ValidationException("{org.qrinvoice.core.AccountNotValidException}");

        }

        // if we fill IBAN parameter account number should be empty
        if (!(model.getIBAN() == null || model.getAccountNumber().isEmpty())) {
            try {
                // check if iban is same as computed iban otherwise raise validation exceptipn
                AccountNumberImpl accNum = new AccountNumberImpl(model.getAccountNumber().getAccountPrefix(), model.getAccountNumber().getAccountBase(), model.getAccountNumber().getBankCode());
                String IBAN = accNum.computeIBAN();
                if (!model.getIBAN().equalsIgnoreCase(IBAN)) {

                    throw new ValidationException("{org.qrinvoice.core.TooMuchAccountParamException}");
                }

            } catch (AccountNotValidException e) {
                log.fatal("invalid account number", e);
                throw new ValidationException("{org.qrinvoice.core.AccountNotValidException}");
            }
        }


        return Response.Status.OK.getReasonPhrase();


    }

    /**
     * generate QR code based on invoice data
     *
     * @param model         data for invoice
     * @param transliterate identifies if returned string has to have capitalized letters
     * @param mode          indicates which mode of QR should be used, pure QR invoice or integrated with SPAYD
     * @param hasBranding   id resulted image has to have branding labels
     * @return png image
     */
    @GET
    @Path("image")
    @Produces(IMAGE_MEDIA_TYPE)
    public Response generateInvoiceQR(@BeanParam @Valid InvoiceModel model, @QueryParam("transliterate") @DefaultValue("true") Boolean transliterate, @QueryParam("mode") @DefaultValue("INVOICE_MODE") IntegrationModeEnum mode, @QueryParam("branding") @DefaultValue("true") Boolean hasBranding) {

        log.info("generateInvoiceQR, mode:" + model);

        log.info("transliterate:" + transliterate);

        log.info("mode:" + mode);

        log.info("has branding:" + hasBranding);

        InvoiceParamDomain param = InvoiceMapper.INSTANCE.invoiceModelToInvoiceParam(model);

        Generator generator = new Generator();
        Response.ResponseBuilder response;
        try {

            String qrstring = generator.getInvoiceString(param, transliterate, mode);


            BufferedImage qrCode = generator.getQRCode(null, qrstring, mode, hasBranding);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCode, "png", outputStream);

            response = Response.ok()
                    .entity(outputStream.toByteArray());

            // allow use in others web pages
            response.header("Access-Control-Allow-Origin", "*");

            return response.build();
        } catch (UnsupportedEncodingException ex) {
            log.fatal("unable to unmarshall params", ex);
            response = Response.status(Response.Status.BAD_REQUEST);
            return response.build();
        } catch (ImageGenerationException | IOException ex) {
            log.fatal("unable to generate image", ex);
            response = Response.status(Response.Status.BAD_REQUEST);
            return response.build();
        }

    }

}
