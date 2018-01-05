package org.zxtc.entity;

/**
 * t_user_posteducation表；
 * @description:
 * @author: hao.peng
 * @date: 2018年1月3日 下午5:53:42
 */
public class UserPostEducation {

    private String userId;
    private String certifyNo;
    private String certifyDate;
    private String technicalFileNumber;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCertifyNo() {
        return certifyNo;
    }
    public void setCertifyNo(String certifyNo) {
        this.certifyNo = certifyNo;
    }
    public String getCertifyDate() {
        return certifyDate;
    }
    public void setCertifyDate(String certifyDate) {
        this.certifyDate = certifyDate;
    }
    public String getTechnicalFileNumber() {
        return technicalFileNumber;
    }
    public void setTechnicalFileNumber(String technicalFileNumber) {
        this.technicalFileNumber = technicalFileNumber;
    }
    @Override
    public String toString() {
        return "UserPostEducation [userId=" + userId + ", certifyNo="
                + certifyNo + ", certifyDate=" + certifyDate
                + ", technicalFileNumber=" + technicalFileNumber + "]";
    }
    
}
