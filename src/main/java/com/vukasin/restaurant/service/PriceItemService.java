package com.vukasin.restaurant.service;

import com.vukasin.restaurant.model.PriceItem;

import java.util.List;
import java.util.Optional;

public interface PriceItemService {

   // PriceItem create(PriceItem priceItem);

    PriceItem create(PriceItem priceItem, Long menuItemId);

    List<PriceItem> findAll();
    Optional<PriceItem> findById(Long id);
    PriceItem update(Long id, PriceItem priceItem, Long menuItemId);
    void delete(Long id);
}
