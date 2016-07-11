package org.qrinvoice.rest;

import com.google.zxing.WriterException;
import org.apache.log4j.Logger;
import org.qrinvoice.core.*;
import org.qrinvoice.gui.InvoiceModel;
import org.qrinvoice.helper.InvoiceMapper;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by zcg on 12.6.2016.
 */

@Path("/generator")

public class QRInvoiceGeneratorResource {

    public static final String FILE_MEDIA_TYPE = "application/x-shortinvoicedescriptor";
    public static final String IMAGE_MEDIA_TYPE = "image/png";


    static Logger log = Logger.getLogger(QRInvoiceGeneratorResource.class.getName());

    /**
     * @param model
     * @param transliterate
     * @return
     */
    @GET
    @Path("string")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String generateInvoiceString(@BeanParam @Valid InvoiceModel model, @QueryParam("transliterate") @DefaultValue("true") Boolean transliterate) {

        try {

            InvoiceParamDomain param = InvoiceMapper.INSTANCE.invoiceModelToInvoiceParam(model);
            Generator generator = new Generator();
            return generator.getInvoiceString(param, transliterate);

        } catch (UnsupportedEncodingException ex) {
            // TODO throw business exception
            log.fatal("unable to unmarshall params", ex);
            return null;
        }
    }

    @GET
    @Path("string")
    @Produces(MediaType.TEXT_PLAIN)
    public String validateInvoiceString(@BeanParam @Valid InvoiceModel model) {

        AccountNumberValidator validator = new AccountNumberValidator();

        if (validator.isValid(model.getAccountNumber(),null)) {
            return Response.Status.OK.getReasonPhrase();
        } else {
            throw new ValidationException("číslo účtu není ve validním formátu");

        }

    }


    @GET
    @Path("image")
    @Produces(IMAGE_MEDIA_TYPE)
    public Response generateInvoiceQR(@BeanParam @Valid InvoiceModel model, @FormParam("transliterate") @DefaultValue("true") Boolean transliterate) {

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
