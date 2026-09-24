package com.grabfood.menu.service;

import com.grabfood.menu.model.Menu;
import com.grabfood.menu.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cache-Aside:
 * - Đọc: @Cacheable
 * - Ghi: DB trước, rồi @CacheEvict (KHÔNG dùng @CachePut để tránh race condition)
 */
@Service
public class MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Cacheable(value = "menuCache", key = "#id")
    public Menu getMenu(Long id) {
        log.info(">>> Query DB menu id={}", id);
        return menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: " + id));
    }

    /**
     * Bước 2: save DB trước.
     * Bước 3: @CacheEvict xóa cache theo menu.id.
     */
    @CacheEvict(value = "menuCache", key = "#menu.id")
    public Menu updateMenu(Menu menu) {
        log.info(">>> Update DB menu id={} price={}", menu.getId(), menu.getPrice());
        // Cập nhật database TRƯỚC — annotation evict chạy quanh method (sau khi method thành công)
        return menuRepository.save(menu);
    }
}
