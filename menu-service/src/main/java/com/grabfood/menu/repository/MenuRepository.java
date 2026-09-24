package com.grabfood.menu.repository;

import com.grabfood.menu.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** In-memory DB giả lập (đủ cho demo cache). */
@Repository
public class MenuRepository {

    private final Map<Long, Menu> store = new ConcurrentHashMap<>();

    public MenuRepository() {
        store.put(1L, new Menu(1L, "Phở Bò", 55000));
    }

    public Optional<Menu> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Menu save(Menu menu) {
        store.put(menu.getId(), menu);
        return menu;
    }
}
