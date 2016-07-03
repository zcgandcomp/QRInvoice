package org.qrinvoice.rest;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.Form;
import org.qrinvoice.core.Generator;
import org.qrinvoice.domain.InvoiceParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;

/**
 * Created by zcg on 12.6.2016.
 */

@Path("/generator")
public class QRInvoiceGeneratorResource {


    static Logger log = Logger.getLogger(QRInvoiceGeneratorResource.class.getName());

    @POST
    @Path("test")
//    @Produces("application/x-shortinvoicedescriptor")
    public String getTest(@Form testDTO test) {

        return test.getId() + ":" + test.getCislo() + ":" + test.getDatum();

    }


    @POST
    @Path("string")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces("application/x-shortinvoicedescriptor")
    public String generateInvoiceString(@Form InvoiceParam invoiceParam, @FormParam("transliterate") @DefaultValue("true") Boolean transliterate) {

        Generator generator = new Generator();
        try {

            return generator.getInvoiceString(invoiceParam, transliterate);
        } catch (UnsupportedEncodingException ex) {
            // TODO throw business exception
            log.fatal("unable to unmarshall params", ex);
            return null;

        } catch (Exception ex) {
            log.fatal("general error", ex);
            return null;
        }

    }


    @GET
    @Path("teststring")
    //@Produces("application/x-shortinvoicedescriptor")
    public String getString(@QueryParam("test") String test) {

        return test.toString();

    }

}
