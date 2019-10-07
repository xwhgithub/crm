package cn.xwh.crm.crm.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:15
 * @Description:
 */
@Entity
@Data   //自动生成getter/setter方法
@Table(name = "sys_user")
public class SysUser {
    /**
     * 使用@Id指定主键. 使用代码@GeneratedValue(strategy=GenerationType.AUTO)
     * 指定主键的生成策略,mysql默认的是自增长。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usr_id")
    private Long usrId;
    @Column(name="usr_name")
    private String usrName;
    @Column(name="usr_password")
    private String usrPassword;
    @ManyToOne
    @JoinColumn(name="usr_role_id")
    private SysRole role;
    @Column(name="usr_flag")
    private Long usrFlag;
}