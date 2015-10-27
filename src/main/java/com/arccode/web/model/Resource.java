package com.arccode.web.model;

public class Resource {
    /**
     *  主键id,所属表字段为resource.id
     */
    private Long id;

    /**
     *  资源名称, ,所属表字段为resource.resource_name
     */
    private String resourceName;

    /**
     *  权限对应的url地址,所属表字段为resource.url
     */
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}