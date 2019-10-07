package cn.xwh.crm.crm.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     *  配置一个密码比对器，用于从登录页面传递过来的密码和从数据库中查询出来的密码做比对。
     *  实现的效果： 会将从前台页面传递过来的密码进行加密。
      */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
        // 设置存储格式，base64为false，hex为true
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        // 设置两次迭代，即指定加密的次数，如果想要更安全可以加密次数设置为1024
        credentialsMatcher.setHashIterations(5);
        return credentialsMatcher;
    }

    // Realm对象
    @Bean
    public Realm myShiroRealm(CredentialsMatcher credentialsMatcher) {
        MyRealm realm = new MyRealm();
        //使用构造注入的方式，将上面定义的密码比对器赋值给Realm类的credentialsMatcher对象属性。
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    // 安全管理器
    @Bean
    public SecurityManager shiroSecurityManager(Realm myShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager shiroSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(shiroSecurityManager);
        //如果不设置默认会自动寻找Web工程根目录下的 "/login.jsp" 页面，但是我们没有所以要设置。
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403.html");
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/localcss/**", "anon");
        //异常页面不拦截
        filterChainDefinitionMap.put("/error/**", "anon");
        //登录退出页面不拦截
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/logout.html", "logout");

        //授权拦截器，即要访问/user/add.html就要拥有user:add授权字符串。
        filterChainDefinitionMap.put("/user/add.html", "perms[user:add]");
        filterChainDefinitionMap.put("/user/delete.html", "perms[user:delete]");
        filterChainDefinitionMap.put("/user/edit.html", "perms[user:update]");
        //过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 :这是一个坑呢，一不小心代码就不好使了;
        // ① authc:所有url都必须认证通过才可以访问; ② anon:所有url都都可以匿名访问
        // 读取所有权限控制信息
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}

