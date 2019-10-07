package cn.xwh.crm.crm.service.impl;

import cn.xwh.crm.crm.dao.SysUserRepository;
import cn.xwh.crm.crm.pojo.SysUser;
import cn.xwh.crm.crm.service.SysUserService;
import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:23
 * @Description:
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser login(String usrName, String usrPassword) {
        List<SysUser> users=sysUserRepository.findByUsrNameAndUsrPassword(usrName,usrPassword);
        if (users != null && users.size()==1) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public SysUser login(String usrName) {
        List<SysUser> users=sysUserRepository.findByUsrName(usrName);
        if (users != null && users.size()==1) {
            SysUser sysUser=users.get(0);
            Hibernate.initialize(sysUser.getRole().getRights());
            // 初始化权限数据，将单个用户所拥有的角色权限给查询出来。
            return sysUser;
        }
        return null;
    }
}
