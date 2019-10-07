package cn.xwh.crm.crm.dao;

import cn.xwh.crm.crm.pojo.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:17
 * @Description:
 */
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    /**
     *
     * @Description: 通过用户名,密码查询用户信息
     *
     * @param: 用户名,密码
     * @return:
     * @auther: xwh
     * @date: 2019/10/6 11:20
     */
    public List<SysUser> findByUsrNameAndUsrPassword(String usrName,String usrPassword);

    /**
     *
     * @Description: 通过用户名查询用户用户信息
     *
     * @param: 用户名
     * @return:
     * @auther: xwh
     * @date: 2019/10/6 11:21
     */
    public List<SysUser> findByUsrName(String usrName);
}
