import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.qrinvoice.rest.QRApplication;
import org.qrinvoice.rest.QRInvoiceGeneratorResource;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 15.7.2016.
 */
public class QRInvoiceGeneratorResourceTest {

    private static final int TEST_PORT = 1234;

    private static final String TEST_HOST = "http://localhost";

    //private static final String TEST_PATH =;

    static Logger log = Logger.getLogger(QRInvoiceGeneratorResourceTest.class.getName());

    TJWSEmbeddedJaxrsServer server = new TJWSEmbeddedJaxrsServer();


    // TJWSEmbeddedJaxrsServer is not supporting bean validation possible solution is https://github.com/GuntherRotsch/RestEasyCdiTJWS, using remote server
    @Before
    public void start() throws Exception {


        server.setPort(TEST_PORT);

        QRInvoiceGeneratorResource resource = new QRInvoiceGeneratorResource();

        server.getDeployment().setApplication(new QRApplication());

        server.getDeployment().getResources().add(resource);

        // server.getDeployment().setApplicationClass(QRApplication.class.getName());
        //  server.getDeployment().getProviderClasses().add(QRInvoiceGeneratorResource.class.getName());

        // server.getDeployment().getRegistry().addPerRequestResource(QRInvoiceGeneratorResource.class);

        log.info(server.getDeployment().getProviderClasses());


        // server.setRootResourcePath(rootResourcePath);
        server.start();

        log.info(server.getDeployment().getActualProviderClasses());

    }

    @After
    public void stop() throws Exception {

        server.stop();

    }

    private Response createTarget(String path) {

        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = resteasyClient.target(TEST_HOST + ":" + TEST_PORT + path);
        Response resp = target.request().get();
        log.info(resp.getStatus());
        return resp;
    }


    //  "/generator/string?id=1&AM=1&DD=20160101&bankCode=3030&VS=1234567890&MSG=message&CC=CZK&X-URL=xxx"
    @Test
    public void generateInvoiceString() {

        Response resp = createTarget("/generator/string?id=1&AM=1&DD=20160101&accountBase=1007001&bankCode=3030&VS=1234567890&MSG=message&CC=CZK&X-URL=xxx");
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        String output = resp.readEntity(String.class);
        log.info(output);


    }

    @Test
    public void generateSpaydInvoiceString() {

        Response resp = createTarget("/generator/string?id=1&AM=1&DD=20160101&DT=20160202&accountBase=1007001&bankCode=3030&VS=1234567890&MSG=message&CC=CZK&X-URL=xxx&ON=555&mode=SPAYD_MODE");
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        log.info(resp.getStatus());
        String output = resp.readEntity(String.class);
        log.info(output);


    }

    @Test
    public void generateSpaydInvoiceStringFail() {

        Response resp = createTarget("/generator/string?id=1&AM=1&DD=20160101&DT=20160202&mode=SPAYD_MODE");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), resp.getStatus());
        log.info(resp.getStatus());
        String output = resp.readEntity(String.class);
        log.info(output);


    }

    @Test
    public void generateSpaydInvoiceStringIBANAndAccountFail() {

        Response resp = createTarget("/generator/string?id=1&AM=1&DD=20160101&DT=20160202&IBAN=AAA&bankCode=3030&accountBase=1007001");
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        log.info(resp.getStatus());
        String output = resp.readEntity(String.class);
        log.info(output);


    }

}
