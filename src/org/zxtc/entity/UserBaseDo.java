package org.zxtc.entity;

import java.io.Serializable;

/**
 * t_user_base表的Pojo
 * @description:
 * @author: hao.peng
 * @date: 2017年12月27日 上午11:43:28
 */
public class UserBaseDo implements Serializable {

    private static final long serialVersionUID = 732484234818542124L;

    private String userId; //uuid
//    private String neReturn; //98返回辨识
    private String politicalVisage;//政治面貌
//    private String nativePlace; //籍贯
    private String nation; //民族
//    private String birthPlace; //出生地
//    private String homeAddress; //现家庭住址
//    private String registeredAddress; //户口所在地
//    private String registeredNature; //户口性质
//    private String maritalStatus; //婚姻状况
    private String personnelCategory; //人员类别 在岗和非在岗
    private String workCode; //工作证号
//    private String payRollCode;//工资编号
//    private String workProvince; //工作地所在省
    private String workStartTime; //参加工作时间
//    private String team;//班组
//    private String teamLeaderType;//班组长类型
//    private String remarks;//备注
//    private String deptAwardCode; //部颁岗位代码
//    private String contractStartTime; //劳动合同开始时间
//    private String contractEndTime; //劳动合同结束时间
//    private String secondPercode;//二线人员标识
//    private String productionTeam;//生产组
//    private String redundantPersonnel;//富余人员标识
    private String cadrePersonnel; //干部工人标识
    private String postName;//职务或职名
//    private String economicType;//岗位按公民经济分
    private String highSpeedRail; //高铁人员标识
//    private String fileCode; //档案编号 
    private String joinPartyTime; //加入党派日期
//    private String workType; //工种
//    private String laborSystem; //劳动班制
//    private String isUnemployment;//是否参加失业保险
//    private String isInjuryJob;//是否参加工伤保险
//    private String isAnnualBonus;//是否参加企业年金
//    private String isSupptaryEndowment;//是否参加补充养老保险
//    private String isEndowment; //是否参加养老保险
//    private String isMedicalCare;//是否参加医疗保险
//    private String isBirth; //是否参加生育保险
//    private String workTypeStartTime; //从事本工种起始时间
    private String inRailwayTime;//入路工作时间
    private String education; //现文化程度
//    private String academy; //所在院校
//    private String major; //专业
//    private String isTechnicalStandards;//是否技术达标
//    private String isFitbill;//工作经历是否符合上岗资格
    private String appraisalLevel; //鉴定等级
    private String appraisalSubject; //鉴定科目
//    private String isCultural; //是否文化达标
//    private String appraisalCode; //鉴定证书号
//    private String technicalQualitionDept; //现专业技术资格科目
//    private String technicalQualitionLevel;//现专业技术资格等级
//    private String grading;//是否定级定职
//    private String firstTechnicalerTime; //首席技师称号
    private String partyBranch;//所属党支部
    private String fullTimeDegree; //全日制；
    private String postProperty; //岗位性质；
    
    public String getFullTimeDegree() {
        return fullTimeDegree;
    }

    public void setFullTimeDegree(String fullTimeDegree) {
        this.fullTimeDegree = fullTimeDegree;
    }

    public String getPostProperty() {
        return postProperty;
    }

    public void setPostProperty(String postProperty) {
        this.postProperty = postProperty;
    }

    public String getPartyBranch() {
        return partyBranch;
    }

    public void setPartyBranch(String partyBranch) {
        this.partyBranch = partyBranch;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoliticalVisage() {
        return politicalVisage;
    }

    public void setPoliticalVisage(String politicalVisage) {
        this.politicalVisage = politicalVisage;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }


    public String getPersonnelCategory() {
        return personnelCategory;
    }

    public void setPersonnelCategory(String personnelCategory) {
        this.personnelCategory = personnelCategory;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getCadrePersonnel() {
        return cadrePersonnel;
    }

    public void setCadrePersonnel(String cadrePersonnel) {
        this.cadrePersonnel = cadrePersonnel;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getHighSpeedRail() {
        return highSpeedRail;
    }

    public void setHighSpeedRail(String highSpeedRail) {
        this.highSpeedRail = highSpeedRail;
    }

    public String getJoinPartyTime() {
        return joinPartyTime;
    }

    public void setJoinPartyTime(String joinPartyTime) {
        this.joinPartyTime = joinPartyTime;
    }


    public String getInRailwayTime() {
        return inRailwayTime;
    }


    public void setInRailwayTime(String inRailwayTime) {
        this.inRailwayTime = inRailwayTime;
    }


    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAppraisalLevel() {
        return appraisalLevel;
    }

    public void setAppraisalLevel(String appraisalLevel) {
        this.appraisalLevel = appraisalLevel;
    }

    public String getAppraisalSubject() {
        return appraisalSubject;
    }

    public void setAppraisalSubject(String appraisalSubject) {
        this.appraisalSubject = appraisalSubject;
    }

    @Override
    public String toString() {
        return "UserBaseDo [userId=" + userId + ", politicalVisage="
                + politicalVisage + ", nation=" + nation
                + ", personnelCategory=" + personnelCategory + ", workCode="
                + workCode + ", workStartTime=" + workStartTime
                + ", cadrePersonnel=" + cadrePersonnel + ", postName="
                + postName + ", highSpeedRail=" + highSpeedRail
                + ", joinPartyTime=" + joinPartyTime + ", inRailwayTime="
                + inRailwayTime + ", education=" + education
                + ", appraisalLevel=" + appraisalLevel + ", appraisalSubject="
                + appraisalSubject + ", partyBranch=" + partyBranch + "]";
    }

}
