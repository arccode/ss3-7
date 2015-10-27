package com.arccode.web.dao;

import com.arccode.web.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    /**
     *  根据主键删除数据库的记录,role
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     *  新写入数据库记录,role
     *
     * @param record
     */
    int insert(Role record);

    /**
     *  动态字段,写入数据库记录,role
     *
     * @param record
     */
    int insertSelective(Role record);

    /**
     *  根据指定主键获取一条数据库记录,role
     *
     * @param id
     */
    Role selectByPrimaryKey(Long id);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,role
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     *  根据主键来更新符合条件的数据库记录,role
     *
     * @param record
     */
    int updateByPrimaryKey(Role record);

    /**
     * 查找table role中所有的记录及其关联的资源list
     * @return
     */
    List<Role> findAllRoles();

    /**
     * 查找角色, 带resource集合
     * @param roleName
     * @return
     */
    Role selectByRoleName(@Param(value = "roleName") String roleName);
}