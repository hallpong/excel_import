package org.zxtc.entity;

import java.io.Serializable;
/**
 * 人员部门关系表；
 * @description:
 * @author: hao.peng
 * @date: 2018年1月3日 下午4:34:02
 */
public class UserDepRel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7186516550163745121L;

    private String id;
    private String userId;
    private String deptId;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDeptId() {
        return deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    @Override
    public String toString() {
        return "UserDepRel [id=" + id + ", userId=" + userId + ", deptId="
                + deptId + "]";
    }
    
}
