package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.service.RoleMenuService;
import com.west2_5.model.entity.RoleMenu;
import com.west2_5.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

