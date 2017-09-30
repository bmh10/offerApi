package util;

import org.offer.model.Offer;
import org.offer.model.dto.OfferDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class OfferTestHelper {

    public static List<OfferDTO> createOfferDTOs(int n) {
        List<OfferDTO> dtos = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            dtos.add(createOfferDTO(i));
        }

        return dtos;
    }

    public static List<Offer> createOffers(int n) {
        List<Offer> offers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            offers.add(createOffer(i));
        }

        return offers;
    }

    public static OfferDTO createOfferDTO() {
        return createOfferDTO(1);
    }

    public static OfferDTO createOfferDTO(int id) {
        OfferDTO dto = new OfferDTO();
        dto.setId(id);
        dto.setName(String.format("Special Offer %s", id));
        dto.setDescription(String.format("A very special offer %s", id));
        dto.setMerchant(String.format("Merchant %s", id));
        dto.setCurrency("GBP");
        dto.setPrice(BigDecimal.valueOf(id * 100 + 0.5));
        return dto;
    }

    public static Offer createOffer() {
        return createOffer(1);
    }

    public static Offer createOffer(int id) {
        Offer offer = new Offer();
        offer.setId(id);
        offer.setName(String.format("Special Offer %s", id));
        offer.setDescription(String.format("A very special offer %s", id));
        offer.setMerchant(String.format("Merchant %s", id));
        offer.setCurrency(Currency.getInstance("GBP"));
        offer.setPrice(BigDecimal.valueOf(id * 100 + 0.5));
        offer.setCreatedDate(new Date());
        return offer;
    }
}
