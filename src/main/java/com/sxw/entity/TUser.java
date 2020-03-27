package com.sxw.entity;

import lombok.Data;

/**
 * @author 苏雄伟 [suxiongwei@kaoshixing.com]
 * @description
 * @date 2020/3/25 2:06 下午
 */
@Data
public class TUser {
    private Integer id, sex, positionId;
    private String userName, realName, mobile, email, note;
}
