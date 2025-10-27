package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.converter.PriceItemConverter;
import com.vukasin.restaurant.dto.PriceItemDTO;
import com.vukasin.restaurant.model.PriceItem;
import com.vukasin.restaurant.service.PriceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/price-items")
public class PriceItemController {

    @Autowired
    private PriceItemService priceItemService;

    @Autowired
    private PriceItemConverter priceItemConverter;

    @GetMapping
    public ResponseEntity<List<PriceItemDTO>> getAllPriceItems() {

        List<PriceItem> priceItems = priceItemService.findAll();
        List<PriceItemDTO> priceItemsDTO = new ArrayList<>();

        for (PriceItem priceItem : priceItems) {
            priceItemsDTO.add(priceItemConverter.toDTO(priceItem));
        }

        return ResponseEntity.ok(priceItemsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceItemDTO> getPriceItemById(@PathVariable long id) {
        Optional<PriceItem> priceItem = priceItemService.findById(id);

        if (priceItem.isPresent()) {
            PriceItemDTO priceItemDTO = priceItemConverter.toDTO(priceItem.get());
            return ResponseEntity.ok(priceItemDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @PostMapping
    public ResponseEntity<PriceItemDTO> createPriceItem(@RequestBody PriceItemDTO priceItemDTO) {

        PriceItem priceItem = priceItemConverter.toEntity(priceItemDTO);

        PriceItem created = priceItemService.create(priceItem, priceItemDTO.getMenuItemId());

        if (created != null) {
            //entity to dto
            PriceItemDTO createdDTO = priceItemConverter.toDTO(created);
            return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceItemDTO> update(@PathVariable Long id, @RequestBody PriceItemDTO dto) {
        PriceItem priceItem = priceItemConverter.toEntity(dto);
        PriceItem updated = priceItemService.update(id, priceItem, dto.getMenuItemId());
        if (updated != null) {
            PriceItemDTO updatedDTO = priceItemConverter.toDTO(updated);
            return ResponseEntity.ok(updatedDTO); //200
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (priceItemService.findById(id).isPresent()) {
            priceItemService.delete(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); //404
        }
    }

}
