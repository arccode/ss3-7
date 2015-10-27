package com.arccode.web.model;

import java.util.List;

public class Role {


    /**
     *  主键id,所属表字段为role.id
     */
    private Long id;

    /**
     *  角色名,所属表字段为role.role_name
     */
    private String roleName;

    /**
     * 资源集合
     */
    private List<Resource> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}