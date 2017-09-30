package unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.offer.model.Offer;
import org.offer.model.dto.OfferDTO;
import org.offer.transformer.OfferTransformer;
import org.offer.transformer.OfferTransformerImpl;
import util.OfferTestHelper;

import java.util.Currency;

public class OfferTransformerTest {

    private OfferTransformer offerTransformer;

    @Before
    public void before() {
        offerTransformer = new OfferTransformerImpl();
    }

    @Test
    public void fromDAO_checkConversion() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        Offer offer = offerTransformer.fromDTO(dto);

        Assert.assertEquals((int)dto.getId(), offer.getId());
        Assert.assertEquals(dto.getName(), offer.getName());
        Assert.assertEquals(dto.getDescription(), offer.getDescription());
        Assert.assertEquals(dto.getMerchant(), offer.getMerchant());
        Assert.assertEquals(Currency.getInstance(dto.getCurrency()), offer.getCurrency());
        Assert.assertEquals(dto.getPrice(), offer.getPrice());
    }

    @Test
    public void toDAO_checkConversion() {
        Offer offer = OfferTestHelper.createOffer();
        OfferDTO dto = offerTransformer.toDTO(offer);

        Assert.assertEquals(offer.getId(), (int)dto.getId());
        Assert.assertEquals(offer.getName(), dto.getName());
        Assert.assertEquals(offer.getDescription(), dto.getDescription());
        Assert.assertEquals(offer.getMerchant(), dto.getMerchant());
        Assert.assertEquals(offer.getCurrency(), Currency.getInstance(dto.getCurrency()));
        Assert.assertEquals(offer.getPrice(), dto.getPrice());
    }

    @Test
    public void convertThenConvertBack() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        Offer offer = offerTransformer.fromDTO(dto);
        OfferDTO dto2 = offerTransformer.toDTO(offer);
        Assert.assertEquals(dto, dto2);
    }
}
