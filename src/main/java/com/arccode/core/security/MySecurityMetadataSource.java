package com.arccode.core.security;

import com.arccode.web.dao.RoleMapper;
import com.arccode.web.dao.UserMapper;
import com.arccode.web.model.Resource;
import com.arccode.web.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * MySecurityMetadataSource:  资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 *
 * @author http://arccode.net
 * @since 2015-03-20 17:53
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger logger = LoggerFactory.getLogger(MySecurityMetadataSource.class);

    private RoleMapper roleMapper;

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /* 保存资源和权限的对应关系  key-资源url  value-权限 */
    private static Map<String,Collection<ConfigAttribute>> resourceMap = null;   
    private AntPathMatcher urlMatcher = new AntPathMatcher();

    public MySecurityMetadataSource() {
    }

    public MySecurityMetadataSource(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
        loadResourcesDefine();  
    }  
      
    @Override  
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        return null;  
    }  
  
    private void loadResourcesDefine(){  
        resourceMap = new HashMap<String,Collection<ConfigAttribute>>();  
//      Collection<ConfigAttribute> configAttributes1 = new ArrayList<ConfigAttribute>() ;
//      ConfigAttribute configAttribute1 = new SecurityConfig("ROLE_ADMIN");
//      configAttributes1.add(configAttribute1);
//      resourceMap.put("/leftmenu.action", configAttributes1);
          
        logger.info("MySecurityMetadataSource.loadResourcesDefine()--------------开始加载资源列表数据--------");  
        List<Role> roles = roleMapper.findAllRoles();
        for(Role role : roles){
            List<Resource> resources = role.getResources();
            for(Resource resource : resources){
                Collection<ConfigAttribute> configAttributes = null;  
                ConfigAttribute configAttribute = new SecurityConfig(resource.getResourceName());  
                if(resourceMap.containsKey(resource.getUrl())){  
                    configAttributes = resourceMap.get(resource.getUrl());  
                    configAttributes.add(configAttribute);  
                }else{  
                    configAttributes = new ArrayList<ConfigAttribute>() ;  
                    configAttributes.add(configAttribute);  
                    resourceMap.put(resource.getUrl(), configAttributes);  
                }  
            }  
        }

        Set<String> set = resourceMap.keySet();
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
        	String s = it.next();
        	logger.info("key:"+s+"|value:"+resourceMap.get(s));
        }
    }  
    /*  
     * 根据请求的资源地址，获取它所拥有的权限 
     */  
    @Override  
    public Collection<ConfigAttribute> getAttributes(Object obj)  
            throws IllegalArgumentException {  
        //获取请求的url地址  
        String url = ((FilterInvocation)obj).getRequestUrl();  
        logger.info("MySecurityMetadataSource:getAttributes()---------------请求地址为："+url);  
        Iterator<String> it = resourceMap.keySet().iterator();  
        while(it.hasNext()){  
            String _url = it.next();  
            if(_url.indexOf("?")!=-1){  
                _url = _url.substring(0, _url.indexOf("?"));  
            }  
            if(urlMatcher.match(_url,url)){
            	logger.info("MySecurityMetadataSource:getAttributes()---------------需要的权限是："+resourceMap.get(_url));  
                return resourceMap.get(_url);  
            }
            	
        }
        Collection<ConfigAttribute> nouse = new ArrayList<ConfigAttribute>();
        nouse.add(new SecurityConfig("无相应权限"));
        return nouse;
    }  
  
    @Override  
    public boolean supports(Class<?> arg0) {  
        logger.info("MySecurityMetadataSource.supports()---------------------");  
        return true;  
    }  
      
}  