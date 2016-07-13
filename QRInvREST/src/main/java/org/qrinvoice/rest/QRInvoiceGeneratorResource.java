package org.qrinvoice.rest;

import org.apache.log4j.Logger;
import org.qrinvoice.core.*;
import org.qrinvoice.model.InvoiceMapper;
import org.qrinvoice.model.InvoiceModel;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
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
     * @param transliterate identifies if returned string has to have capitalized string parameters
     * @return string with QR code data
     */
    @GET
    @Path("string")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String generateInvoiceString(@BeanParam @Valid InvoiceModel model, @QueryParam("transliterate") @DefaultValue("true") Boolean transliterate) {

        log.info("generateInvoiceString" + model);
        try {

            InvoiceParamDomain param = InvoiceMapper.INSTANCE.invoiceModelToInvoiceParam(model);
            Generator generator = new Generator();


            if (param.getIBAN() == null) {

                AccountNumberImpl accNum = new AccountNumberImpl(model.getAccountNumber().getAccountPrefix(), model.getAccountNumber().getAccountBase(), model.getAccountNumber().getBankCode());

                param.setIBAN(accNum.computeIBAN());

            }
            return generator.getInvoiceString(param, transliterate);

        } catch (
                UnsupportedEncodingException ex)

        {

            log.fatal("unable to encode params", ex);
            throw new ValidationException("{org.qrinvoice.core.UnsupportedEncodingException}");
        } catch (
                AccountNotValidException e)

        {
            throw new ValidationException("{org.qrinvoice.core.AccountNotValidException}");
        }

    }

    /**
     * @param model
     * @return
     */
    @GET
    @Path("string")
    @Produces(MediaType.TEXT_PLAIN)
    public String validateInvoiceString(@BeanParam @Valid InvoiceModel model) {

        log.info("validateInvoiceString" + model);
        AccountNumberValidator validator = new AccountNumberValidator();

        if (validator.isValid(model.getAccountNumber(), null)) {
            return Response.Status.OK.getReasonPhrase();
        } else {
            log.fatal("account not valid:" + model.getAccountNumber());
            throw new ValidationException("{org.qrinvoice.core.AccountNotValidException}");

        }

    }


    @GET
    @Path("image")
    @Produces(IMAGE_MEDIA_TYPE)
    public Response generateInvoiceQR(@BeanParam @Valid InvoiceModel model, @FormParam("transliterate") @DefaultValue("true") Boolean transliterate) {

        log.info("generateInvoiceQR" + model);
        InvoiceParamDomain param = InvoiceMapper.INSTANCE.invoiceModelToInvoiceParam(model);

        Generator generator = new Generator();
        Response.ResponseBuilder response;
        try {

            String qrstring = generator.getInvoiceString(param, transliterate);

            ByteArrayOutputStream qrCode = generator.getQRCode(null, qrstring);

            response = Response.ok()
                    .entity(qrCode.toByteArray());

            // allow use in others web pages
            response.header("Access-Control-Allow-Origin", "*");

            return response.build();
        } catch (UnsupportedEncodingException ex) {
            log.fatal("unable to unmarshall params", ex);
            response = Response.status(Response.Status.BAD_REQUEST);
            return response.build();
        } catch (ImageGenerationException ex) {
            log.fatal("unable to generate image", ex);
            response = Response.status(Response.Status.BAD_REQUEST);
            return response.build();
        }

    }

}
