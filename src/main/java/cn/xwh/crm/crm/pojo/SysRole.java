package cn.xwh.crm.crm.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:12
 * @Description:
 */
//角色持久化类
@Entity
@Data  //省略setter/getter方法
@Table(name = "sys_role")
public class SysRole {

    @Id
    @Column(name =  "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;  //角色ID

    @Column(name = "role_name")
    private String roleName;  //角色名称

    @Column(name = "role_desc")
    private String roleDesc;  //角色的描述

    @Column(name = "role_flag")
    private Long roleFlag;   //角色的标识，  0代表当前角色被禁用，  1代表当前角色可用。

    //一个角色有多个权限，一个权限可以分配给多个角色，这种关系叫多对多的关联关系。
    @ManyToMany(targetEntity = SysRight.class, cascade = CascadeType.REMOVE)
    //CascadeType.REMOVE 级联删除，在删除某个角色，会将当前这个角色下的所有权限一起删除
    @JoinTable(name = "sys_role_right",
            joinColumns = @JoinColumn(name = "rf_role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "rf_right_code", referencedColumnName = "right_code"))
    private List<SysRight> rights;
    //@JoinTable 设置两者之间的关联表,即中间表。 name属性值为中间表的表名
    //joinColumns = @JoinColumn(name = "rf_role_id", referencedColumnName = "role_id")
    //中间表中的rf_role_id外键是引用于角色表的主键role_id
    //joinColumns是主操作表的中间表列，而inverseJoinColumns是副操作表的中间表列，
    //多对多的关联关系是写到哪个实体类, 哪个实体类所对应的表为主操作表。
}