package demo.sa.price.service;

import demo.sa.price.exception.PriceNotFoundException;
import demo.sa.price.mapper.PriceMapper;
import demo.sa.price.model.PriceEntity;
import demo.sa.price.model.dto.Price;
import demo.sa.price.persistence.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    @Autowired
    public PriceService(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    public Price createPrice(Price price) {
        PriceEntity priceEntity = priceMapper.map(price, PriceEntity.class);
        priceRepository.save(priceEntity);
        return price;
    }

    public Price getPrice(String productId, String promotionId) {
        PriceEntity priceEntity = priceRepository.findByProductIdAndPromotionId(productId, promotionId)
        .orElseThrow(() -> new PriceNotFoundException(productId, promotionId));
        return priceMapper.map(priceEntity, Price.class);
    }

    public Price updatePrice(String productId, String promotionId, Price price) {
        PriceEntity priceEntity = priceRepository.findByProductIdAndPromotionId(productId, promotionId)
                .orElseThrow(() -> new PriceNotFoundException(productId, promotionId));
        PriceEntity toSave = priceMapper.map(price, PriceEntity.class);
        toSave.setPriceId(priceEntity.getPriceId());
        priceRepository.save(toSave);
        return price;
    }
}
