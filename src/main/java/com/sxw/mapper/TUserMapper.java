package com.sxw.mapper;

import com.sxw.entity.TUser;

/**
 * @author 苏雄伟 [suxiongwei@kaoshixing.com]
 * @description
 * @date 2020/3/25 2:06 下午
 */
public interface TUserMapper {
    TUser selectByPrimaryKey(int id);
}
