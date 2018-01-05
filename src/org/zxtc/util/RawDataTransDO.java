package org.zxtc.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.zxtc.constant.ExcelColNameConstant;
import org.zxtc.entity.ExcelRawData;
import org.zxtc.entity.HorizonUserDO;
import org.zxtc.entity.UserBaseDo;
import org.zxtc.entity.UserDepRel;
import org.zxtc.entity.UserPositionRel;
import org.zxtc.entity.UserPostEducation;

public class RawDataTransDO {

    private static final RawDataTransDO rdtd = new RawDataTransDO();
    
    private RawDataTransDO(){
        
    }
    
    public static RawDataTransDO getInstance(){
        return rdtd;
    }
    
    public List<UserBaseDo> rawData2UserBase(Set<ExcelRawData> erds) {
        List<UserBaseDo> ubds = new ArrayList<UserBaseDo>();
        if(erds == null || erds.isEmpty()){
            return ubds;
        }
        UserBaseDo bd = null;
        for(ExcelRawData data: erds){
            bd = new UserBaseDo();
            bd.setUserId(data.getUuid());
            ubds.add(bd);
        }
        return ubds;
    }
    
    public List<HorizonUserDO> rawData2HorizonUser(Set<ExcelRawData> erds) {
        List<HorizonUserDO> huds = new ArrayList<HorizonUserDO>();
        if(erds == null || erds.isEmpty()){
            return huds;
        }
        HorizonUserDO hd = null;
        for(ExcelRawData data: erds){
            hd = new HorizonUserDO();
            hd.setId(data.getUuid());
            hd.setName(data.getName());
            huds.add(hd);
        }
        return huds;
    }
    
    /**
     * 
     * @description:区分出需要新增和更新的对象；
     * @author: hao.peng
     * @date: 2017年12月28日 下午9:37:16 
     * @param：
     * @return：
     */
    public List<List<HorizonUserDO>> compareExcelDataWithDb(Set<ExcelRawData> erds, Map<String, String> names2Ids){
        List<List<HorizonUserDO>> result = new ArrayList<List<HorizonUserDO>>();
        List<HorizonUserDO> insertList = new ArrayList<HorizonUserDO>();
        List<HorizonUserDO> updateList = new ArrayList<HorizonUserDO>();
        for (ExcelRawData exd : erds) {
            HorizonUserDO hud = new HorizonUserDO();
            hud.setBirthDate(exd.getBirthDate());
            hud.setActive(1+"");
            hud.setSex(exd.getSex().equals("男") ? 0+"": 1+""); //男和女的值用数字表示
            hud.setPassword(ExcelColNameConstant.PASSWORD);//需要去设定固定值；
            hud.setPpassword(ExcelColNameConstant.PPASSWORD);
            hud.setPym(PinyinUtil.getPinYinHeadChar(exd.getName()));//设定值
            hud.setRegisterTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            hud.setUserStyle(ExcelColNameConstant.USER_STYLE);
            hud.setLoginName(PinyinUtil.getPingYin(exd.getName()));
            hud.setName(exd.getName());
            hud.setTelephone(exd.getContact());
            hud.setCertNo(exd.getIdCardCode());
            String name = exd.getName();
            if(names2Ids.keySet().contains(name)){//如果包含，则做更新操作；沿用之前的uuid 密码等；
                hud.setId(names2Ids.get(name));
                exd.setUuid(names2Ids.get(name));
                updateList.add(hud);
            }else{//属于需要插入的；沿用之前的uuid;
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                hud.setId(uuid);
                exd.setUuid(uuid);
                insertList.add(hud);
            }
        }
        result.add(insertList);
        result.add(updateList);
        return result;
    }
    
    /**
     * 
     * @description:
     * @author: hao.peng
     * @date: 2017年12月28日 下午9:43:08 
     * @param：huds--insertList; loginnames--db loginnames;
     * @return：insert horizonUserDO
     */
    public void setLoginNames(List<HorizonUserDO> huds, Set<String> loginnames){
        List<String> result = new ArrayList<String>();
        for(HorizonUserDO hud: huds){
            String loginName = hud.getLoginName();
            int i = 0;
            while(loginnames.contains(loginName) || result.contains(loginName)){
                i++;
                loginName = hud.getLoginName() + i;
            }
            hud.setLoginName(loginName);
            result.add(loginName);
        }
    }
    
    /**
     * 
     * @description:区别新增和更新的数据；
     * @author: hao.peng
     * @date: 2017年12月28日 下午10:07:59 
     * @param：
     * @return：
     */
    public List<List<UserBaseDo>> compareExcelDataWithDb(Set<ExcelRawData> erds, List<String> uuids){
        List<List<UserBaseDo>> result = new ArrayList<List<UserBaseDo>>();
        List<UserBaseDo> insertList = new ArrayList<UserBaseDo>();
        List<UserBaseDo> updateList = new ArrayList<UserBaseDo>();
        for (ExcelRawData erd : erds) {
            UserBaseDo ubd = new UserBaseDo();
            if(uuids.contains(erd.getUuid())){
                updateList.add(ubd);
            }else{
                insertList.add(ubd);
            }
            ubd.setAppraisalLevel(erd.getIdGrade());
            ubd.setAppraisalSubject(erd.getIdSubject());
            ubd.setCadrePersonnel(erd.getIdentity().equals("干部") ? 0+"" : 1+"");
            ubd.setEducation(getEducation(erd.getEducation()));
            ubd.setHighSpeedRail(erd.getHighrailReserve());
            ubd.setPoliticalVisage(getPolitical(erd.getPoliticalVisage()));
            ubd.setInRailwayTime(erd.getJoinTime());
            ubd.setNation(erd.getRace());
            ubd.setPersonnelCategory(erd.getJobStatus().equals("在岗") ? 0+"": 1+"");
            ubd.setPostName(erd.getJobTitle());
            ubd.setWorkCode(erd.getJobNum());
            ubd.setWorkStartTime(erd.getStartWorkTime());
            ubd.setJoinPartyTime(erd.getJoinPartyTime());
            ubd.setUserId(erd.getUuid());
            ubd.setPartyBranch(erd.getAffiliatedParty());
            ubd.setPostProperty(erd.getPostNature());
            ubd.setFullTimeDegree(erd.getFullTime());
        }
        result.add(insertList);
        result.add(updateList);
        return result;
    }

    private String getPolitical(String politicalVisage) {
        String result = "";
        switch (politicalVisage) {
        case "党员":
            result = 0+"";
            break;
        case "团员":
            result = 1+"";
            break;
        case "群众":
            result = 2+"";
            break;
        default:
            result = 3+"";
            break;
        }
        return result;
    }
    
    private String getEducation(String education){
        String result = "";
        switch (education) {
        case "博士":
            result = 0+"";
            break;
            
        case "硕士":
            result = 1+"";
            break;
        case "本科":
            result = 2+"";
            break;
        case "大专":
            result = 3+"";
            break;
        case "中专":
            result = 4+"";
            break;
        case "高中":
            result = 5+"";
            break;
        case "小学":
            result = 6+"";
            break;
        default:
            result = 7+"";
            break;
        }
        return result;
    }
    
    public List<UserDepRel> getInsertUserDept(Set<ExcelRawData> erds, List<String> userIds, Map<String, String> deptName2deptId){
        List<UserDepRel> result = new ArrayList<UserDepRel>();
        for (ExcelRawData data: erds) {
            if(!userIds.contains(data.getUuid())){
                String deptId = deptName2deptId.get(data.getDept());
                if(deptId !=null){
                    UserDepRel udr = new UserDepRel();
                    udr.setDeptId(deptId);
                    udr.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    udr.setUserId(data.getUuid());
                    result.add(udr);
                }else{
                    System.out.println(data.getName() + "---此人部门--"+data.getDept()+"不合规");
                }
            }
        }
        return result;
    }
    
    public List<UserPositionRel> getInsertUserPosition(Set<ExcelRawData> erds, List<String> userIds, Map<String, String> pName2pId){
        List<UserPositionRel> result = new ArrayList<UserPositionRel>();
        for (ExcelRawData data: erds) {
            if(!userIds.contains(data.getUuid())){
                String positionId = pName2pId.get(data.getJobTitle());
                if(positionId !=null){
                    UserPositionRel upr = new UserPositionRel();
                    upr.setPositionId(positionId);
                    upr.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    upr.setUserId(data.getUuid());
                    result.add(upr);
                }else{
                    System.out.println(data.getName() + "---此人岗位---"+ data.getJobTitle()+"不合规");
                }
            }
        }
        return result;
    }
    
    public List<List<UserPostEducation>> comparePostEducation(List<String> userIds, Set<ExcelRawData> erds){
        List<List<UserPostEducation>> result = new ArrayList<List<UserPostEducation>>();
        List<UserPostEducation> updates = new ArrayList<UserPostEducation>();
        List<UserPostEducation> inserts = new ArrayList<UserPostEducation>();
        for (ExcelRawData data : erds) {
            String user_id = data.getUuid();
            UserPostEducation upe = new UserPostEducation();
            upe.setUserId(user_id);
            upe.setCertifyDate(data.getTrainingCertifyTime());
            upe.setCertifyNo(data.getTrainingCertifyCode());
            upe.setTechnicalFileNumber(data.getTechFileCode());
            if(userIds.contains(user_id)){
                updates.add(upe);
            }else{
                inserts.add(upe);
            }
        }
        result.add(inserts);
        result.add(updates);
        return result;
    }
}
