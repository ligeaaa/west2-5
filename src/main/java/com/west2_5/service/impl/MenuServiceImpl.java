package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.model.entity.Menu;
import com.west2_5.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import com.west2_5.service.MenuService;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:05
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}

