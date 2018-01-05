package org.zxtc.entity;

import java.io.Serializable;

/**
 * to_horizon_user表的POJO
 * @description:
 * @author: hao.peng
 * @date: 2017年12月27日 上午11:44:41
 */
public class HorizonUserDO implements Serializable {

    private static final long serialVersionUID = 1969441513310410725L;

    private String id;
    private String name;
    private String loginName;
//    private String email;
    private String active;
    private String orderNo;
    private String userStyle; //default
//    private String deleteTime;
    private String registerTime; //new Date();
    private String password;
    private String ppassword;
//    private String firstName;
//    private String givenName;
//    private String empNo;
//    private String certificate;
    private String sex;
    private String birthDate;
//    private String hasImage;
//    private String telephone;
//    private String callphone;
//    private String certType;
    private String certNo;//身份证号
//    private String postCode;
//    private String address;
//    private String ip;
//    private String comments;
//    private String secId;
//    private String engName;
//    private String position;
//    private String fax;
//    private String dn;
//    private String workDesc;
//    private String firstLetter;
//    private String fullPinyin;
//    private String politicalLandscape;
//    private String culturalLevel;
//    private String workStartdate;
//    private String nation;
//    private String telephoneHome;
//    private String otherPosition;
//    private String ldapUnid;
//    private String f1;
//    private String f2;
//    private String f3;
//    private String f4;
//    private String f5;
//    private String f6;
//    private String f7;
//    private String f8;
//    private String f9;
//    private String f10;
    private String pym;
    private String telephone;
    
    
    public String getCertNo() {
        return certNo;
    }
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getActive() {
        return active;
    }
    public void setActive(String active) {
        this.active = active;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getUserStyle() {
        return userStyle;
    }
    public void setUserStyle(String userStyle) {
        this.userStyle = userStyle;
    }
    public String getRegisterTime() {
        return registerTime;
    }
    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPpassword() {
        return ppassword;
    }
    public void setPpassword(String ppassword) {
        this.ppassword = ppassword;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getPym() {
        return pym;
    }
    public void setPym(String pym) {
        this.pym = pym;
    }
    @Override
    public String toString() {
        return "HorizonUserDO [id=" + id + ", name=" + name + ", loginName="
                + loginName + ", active=" + active + ", orderNo=" + orderNo
                + ", userStyle=" + userStyle + ", registerTime=" + registerTime
                + ", password=" + password + ", ppassword=" + ppassword
                + ", sex=" + sex + ", birthDate=" + birthDate + ", certNo="
                + certNo + ", pym=" + pym + ", telephone=" + telephone + "]";
    }
    
}
