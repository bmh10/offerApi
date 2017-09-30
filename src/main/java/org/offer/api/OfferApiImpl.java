package org.offer.api;

import org.offer.exception.InvalidParameterException;
import org.offer.exception.OfferNotFoundException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.dto.OfferDTO;
import org.offer.service.OfferService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("api/v1/offer")
public class OfferApiImpl extends AbstractApi implements OfferApi {

    private OfferService offerService;

    public OfferApiImpl(OfferService offerService) {
        this.offerService = offerService;
    }

    /**
     * Gets all offers
     * @return Response object containing collection of all offers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffers() {
        Collection<OfferDTO> offers = offerService.getAllOffers();
        return ok(offers);
    }

    /**
     * Gets offer with specified ID
     * @param id The offer ID
     * @return Response object containing the offer
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("id") int id) {
        try {
            OfferDTO offer = offerService.getOffer(id);
            return ok(offer);
        }
        catch (OfferNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    /**
     * Creates offer from provided offer DTO
     * @param offerDTO The offer DTO
     * @return Response object containing the created offer
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOffer(OfferDTO offerDTO) {
        try {
            OfferDTO offer = offerService.createOffer(offerDTO);
            return ok(offer);
        }
        catch (RequiredParameterException | InvalidParameterException e) {
            return badRequest(e.getMessage());
        }
    }

    /**
     * Updates offer with specified ID using provided offer DTO
     * @param id The offer ID
     * @param offerDTO The offer DTO
     * @return Response object containing the updated offer
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOffer(@PathParam("id") int id, OfferDTO offerDTO) {
        try {
            offerDTO.setId(id);
            OfferDTO offer = offerService.updateOffer(offerDTO);
            return ok(offer);
        }
        catch (RequiredParameterException | InvalidParameterException e) {
            return badRequest(e.getMessage());
        }
        catch (OfferNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    /**
     * Deletes offer with specified ID
     * @param id The offer ID
     * @return Response object
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOffer(@PathParam("id") int id) {
        try {
            offerService.deleteOffer(id);
            return ok();
        }
        catch (OfferNotFoundException e) {
            return notFound(e.getMessage());
        }
    }
}
