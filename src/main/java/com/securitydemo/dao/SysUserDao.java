package com.securitydemo.dao;

import com.securitydemo.domain.SysUser;

public interface SysUserDao {
    SysUser findByUserName(String username);
}
