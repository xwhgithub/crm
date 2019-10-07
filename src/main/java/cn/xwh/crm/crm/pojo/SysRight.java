package cn.xwh.crm.crm.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:15
 * @Description:
 */
@Entity
@Data
public class SysRight {

    @Id
    @Column(name="right_code")
    private String rightCode;
    @Column(name="right_parent_code")
    private String rightParentCode;
    @Column(name="right_type")
    private String rightType;
    @Column(name="right_text")
    private String rightText;
    @Column(name="right_url")
    private String rightUrl;
    @Column(name="right_tip")
    private String rightTip;
}