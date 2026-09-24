package com.grabfood.menu.controller;

import com.grabfood.menu.model.Menu;
import com.grabfood.menu.service.MenuService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable Long id) {
        return menuService.getMenu(id);
    }

    @PutMapping
    public Menu update(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }
}
