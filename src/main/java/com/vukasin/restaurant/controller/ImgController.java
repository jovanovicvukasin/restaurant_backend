package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.model.MenuItem;
import com.vukasin.restaurant.repository.MenuItemRepository;
import com.vukasin.restaurant.service.impl.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImgController {

    private final ImgService imgService;
    private final MenuItemRepository menuItemRepository;

    @PostMapping("/upload/{menuItemId}")
    public ResponseEntity<String> upload(@PathVariable Long menuItemId, @RequestParam("file") MultipartFile file) {
        String filePath = imgService.saveFile(file);

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuItem.setImageUrl(filePath);
        menuItemRepository.save(menuItem);

        return filePath != null ? ResponseEntity.ok("Image uploaded " + filePath) : ResponseEntity.notFound().build();
    }

}
