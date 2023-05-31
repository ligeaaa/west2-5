package com.west2_5.controller;

import com.west2_5.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Favorites)表控制层
 *
 * @author makejava
 * @since 2023-05-07 14:23:39
 */
@RestController
@RequestMapping("/favor")
public class FavoritesController{

    @Autowired
    private FavoritesService favoritesService;



}

