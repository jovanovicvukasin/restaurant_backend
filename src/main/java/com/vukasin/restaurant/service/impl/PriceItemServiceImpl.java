package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.model.MenuItem;
import com.vukasin.restaurant.model.PriceItem;
import com.vukasin.restaurant.repository.MenuItemRepository;
import com.vukasin.restaurant.repository.PriceItemRepository;
import com.vukasin.restaurant.service.PriceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceItemServiceImpl implements PriceItemService {

    @Autowired
    private PriceItemRepository priceItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public PriceItem create(PriceItem priceItem, Long menuItemId) {

        if(priceItem.getPrice() == null || priceItem.getPrice() <= 0) {
            throw new RuntimeException("Price must be provided and greater than 0");
        }


        if (priceItem.getValidFrom() == null) {
            priceItem.setValidFrom(LocalDate.now());
        }

        if (priceItem.getValidTo() == null) {
            priceItem.setValidTo(priceItem.getValidFrom().plusMonths(6));
        }

//        if (priceItem.getValidTo().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("ValidTo is before current date");
//        }

        if(priceItem.getValidTo().isBefore(priceItem.getValidFrom())) {
            throw new RuntimeException("ValidTo is before ValidFrom");
        }

        //overlapping
        List<PriceItem> overlaps =priceItemRepository.findOverlappingPrices(menuItemId, priceItem.getValidFrom(), priceItem.getValidTo());

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Overlapping! Price for this menu item already exists for the given period!");
        }

        if (menuItemId != null) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + menuItemId));
            priceItem.setMenuItem(menuItem);
        } else {
            throw new RuntimeException("MenuItemId must be provided");
        }


        return priceItemRepository.save(priceItem);
    }

    @Override
    public List<PriceItem> findAll() {
        return priceItemRepository.findAll();
    }

    @Override
    public Optional<PriceItem> findById(Long id) {
        return priceItemRepository.findById(id);
    }

    @Override
    public PriceItem update(Long id, PriceItem priceItem, Long menuItemId) {

        PriceItem existing = priceItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceItem not found with id: " +id));

        existing.setCurrency(priceItem.getCurrency());

        if(priceItem.getPrice() == null || priceItem.getPrice() <= 0) {
            throw new RuntimeException("Price must be provided and greater than 0");
        }

        existing.setPrice(priceItem.getPrice());

        if(priceItem.getValidFrom() != null) {
            existing.setValidFrom(priceItem.getValidFrom());
        } else if (existing.getValidFrom() == null) {
            existing.setValidFrom(LocalDate.now());
        }

        if(priceItem.getValidTo() != null) {
            existing.setValidTo(priceItem.getValidTo());
        } else if (existing.getValidTo() == null) {
            existing.setValidTo(existing.getValidFrom().plusMonths(6));
        }

        if (existing.getValidTo().isBefore(existing.getValidFrom())) {
            throw new RuntimeException("ValidTo is before ValidFrom");
        }

        if (menuItemId != null) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " +menuItemId));
            existing.setMenuItem(menuItem);
        } else {
            throw new RuntimeException("MenuItemId must be provided");
        }


        return priceItemRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        priceItemRepository.deleteById(id);
    }
}
