package org.offer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.offer.api.OfferApi;
import org.offer.api.OfferApiImpl;
import org.offer.dao.OfferDAO;
import org.offer.dao.OfferDAOImpl;
import org.offer.model.dto.OfferDTO;
import org.offer.service.OfferService;
import org.offer.service.OfferServiceImpl;
import org.offer.transformer.OfferTransformer;
import org.offer.transformer.OfferTransformerImpl;
import org.offer.validator.OfferValidatorImpl;
import org.offer.validator.Validator;

public class AppServer {

    private static final String API_PACKAGE = "org.offer.api";
    private Server server;

    public AppServer(int port) {
        configureServer(port);
    }

    public void runServer() throws Exception {
        try {
            server.start();
            server.join();
        }
        finally {
            server.destroy();
        }
    }

    public void runTestServer() throws Exception {
        server.start();
    }

    public void stopTestServer() throws Exception {
        server.stop();
    }

    private void configureServer(int port) {
        OfferApi offerApi = createApi();
        ServletHolder servlet = configureServlet(offerApi);
        server = configureServer(servlet, port);
    }

    private OfferApi createApi() {
        Validator<OfferDTO> offerValidator = new OfferValidatorImpl();
        OfferTransformer transformer = new OfferTransformerImpl();
        OfferDAO dao = new OfferDAOImpl();
        OfferService service = new OfferServiceImpl(offerValidator, transformer, dao);
        return new OfferApiImpl(service);
    }

    private ServletHolder configureServlet(OfferApi offerApi) {
        ResourceConfig config = new ResourceConfig();
        config.packages(API_PACKAGE);
        config.register(offerApi);
       return new ServletHolder(new ServletContainer(config));
    }

    private org.eclipse.jetty.server.Server configureServer(ServletHolder servlet, int port) {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");
        return server;
    }
}
