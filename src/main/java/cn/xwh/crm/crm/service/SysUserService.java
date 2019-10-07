package cn.xwh.crm.crm.service;

import cn.xwh.crm.crm.pojo.SysUser;

/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:22
 * @Description:
 */
public interface SysUserService {

    //根据用户名+密码查询
    SysUser login(String usrName, String usrPassword);
    // 仅通过用户名获取用户数据
    SysUser login(String usrName);
}
