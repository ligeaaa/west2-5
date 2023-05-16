package com.west2_5.config.shiro;


import com.west2_5.model.entity.User;
import com.west2_5.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UserRealm extends AuthorizingRealm{

    @Autowired
    UserService userService;

    // 返回认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

        //获取前端的输入用户
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String inputPhone = token.getUsername(); //phone对应的参数是token的username

        // 找到数据库该用户的信息
        User user = userService.findUserByPhone(inputPhone);
        String dbPwd = user.getPassword(); //数据中的加密密码
        String salt = user.getUserSalt();

        return new SimpleAuthenticationInfo(user, dbPwd, ByteSource.Util.bytes(salt),"" );
    }

    // 认证成功即可授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
