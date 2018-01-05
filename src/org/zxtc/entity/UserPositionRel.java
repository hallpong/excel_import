package org.zxtc.entity;

import java.io.Serializable;

/**
 * 人员岗位表；
 * @description:
 * @author: hao.peng
 * @date: 2018年1月3日 下午4:35:08
 */
public class UserPositionRel implements Serializable {

    private static final long serialVersionUID = -8134350775723693293L;

    private String id;
    private String userId;
    private String positionId;
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
    public String getPositionId() {
        return positionId;
    }
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
