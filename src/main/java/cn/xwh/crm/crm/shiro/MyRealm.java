package cn.xwh.crm.crm.shiro;


import cn.xwh.crm.crm.pojo.SysUser;
import cn.xwh.crm.crm.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    //权限控制
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑.....");
//        //给资源进行授权
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //获取当前登录用户
//        Subject subject = SecurityUtils.getSubject();
//        String username = (String)subject.getPrincipal();  //获取登录用户名
//        SysUser user = sysUserService.queryByName(username);  //通过用户名查询用户信息。*/
//        //遍历当前用户的所有权限，将将数据库中的权限标签right_perms_code填入集合中
//        for (SysRight right : user.getRole().getRights()) {
//            if (!StringUtils.isEmpty(right.getRightPermsCode())) {
//                info.addStringPermission(right.getRightPermsCode());
//            }
//        }
        return null;
    }

    //用户登录认证
    @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.把AuthenticationToken转换为UseramePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //2.从UsernamePasswordToken中来获取username对应的用户记录。
        String username = upToken.getUsername();
        //3.调用数据库的方法，从数据库中查询username对应的用户记录
        SysUser user = sysUserService.login(username);
        //4.若用户不存在，则抛出AuthenticationException异常类的子类：UnknownAccountException。
        if (user == null) {  //即用户名不存在
            throw new UnknownAccountException("未知的用户名异常!");
        }
        // 将user对象放入session中
        SecurityUtils.getSubject().getSession().setAttribute("sessionUser", user);

        //5.构建AuthenticationInfo对象并进行返回。
            //注：使用AuthenticationInfo接口的实现类SimpleAuthenticationInfo来实现密码的比对。
        //参数一：为认证的实体类信息，可以是username，也可以是数据表对应的用户的实体对象。
        //参数二：从数据库中查询出来的【用户密码】
        //参数三：当前Realm对象的name。调用父类的getName()方法即可。
        SimpleAuthenticationInfo info ; //new SimpleAuthenticationInfo(username, user.getUsrPassword(),getName());
        ByteSource salt = ByteSource.Util.bytes(username); //我们一般是使用唯一的字符串做为这个盐,用户名是唯一的
        info = new SimpleAuthenticationInfo(username, user.getUsrPassword(), salt ,getName());
        return info;
    }
}
