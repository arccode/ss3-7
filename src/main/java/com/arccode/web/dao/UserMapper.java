package com.arccode.web.dao;

import com.arccode.web.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     *  根据主键删除数据库的记录,user
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     *  新写入数据库记录,user
     *
     * @param record
     */
    int insert(User record);

    /**
     *  动态字段,写入数据库记录,user
     *
     * @param record
     */
    int insertSelective(User record);

    /**
     *  根据指定主键获取一条数据库记录,user
     *
     * @param id
     */
    User selectByPrimaryKey(Long id);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,user
     *
     * @param record
     */
    int updateByPrimaryKeySelective(User record);

    /**
     *  根据主键来更新符合条件的数据库记录,user
     *
     * @param record
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据username查询user
     * @param username
     * @return
     */
    User selectByUsername(@Param("username") String username);
}