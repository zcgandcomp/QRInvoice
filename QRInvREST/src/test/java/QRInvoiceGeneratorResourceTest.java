import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.Test;
import org.qrinvoice.rest.QRApplication;
import org.qrinvoice.rest.QRInvoiceGeneratorResource;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by zcg on 15.7.2016.
 */
public class QRInvoiceGeneratorResourceTest {

    private int TEST_PORT = 8080;

    private String TEST_HOST = "http://localhost";

    static Logger log = Logger.getLogger(QRInvoiceGeneratorResourceTest.class.getName());

    TJWSEmbeddedJaxrsServer server = new TJWSEmbeddedJaxrsServer();


    // TJWSEmbeddedJaxrsServer is not supporting bean validation possible solution is https://github.com/GuntherRotsch/RestEasyCdiTJWS, using remote server
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


    public void stop() throws Exception {

        server.stop();

    }

    @Test
    public void generateInvoiceString() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();
        Response resp;

        ResteasyWebTarget target = resteasyClient.target(TEST_HOST + ":" + TEST_PORT + "/qrinvrestapp/invoiceservices/generator/string?id=1&AM=1&DD=20160101");
        resp = target.request().get();

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), resp.getStatus());
        log.info(resp.getStatus());
        String output = resp.readEntity(String.class);
        log.info(output);


    }
}
