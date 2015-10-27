package com.arccode.web.dao;

import com.arccode.web.model.Resource;

public interface ResourceMapper {
    /**
     *  根据主键删除数据库的记录,resource
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     *  新写入数据库记录,resource
     *
     * @param record
     */
    int insert(Resource record);

    /**
     *  动态字段,写入数据库记录,resource
     *
     * @param record
     */
    int insertSelective(Resource record);

    /**
     *  根据指定主键获取一条数据库记录,resource
     *
     * @param id
     */
    Resource selectByPrimaryKey(Long id);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,resource
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Resource record);

    /**
     *  根据主键来更新符合条件的数据库记录,resource
     *
     * @param record
     */
    int updateByPrimaryKey(Resource record);
}