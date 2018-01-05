package org.zxtc.main;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.zxtc.dao.ImportDbDao;
import org.zxtc.entity.ExcelRawData;
import org.zxtc.entity.HorizonUserDO;
import org.zxtc.entity.UserBaseDo;
import org.zxtc.entity.UserDepRel;
import org.zxtc.entity.UserPositionRel;
import org.zxtc.entity.UserPostEducation;
import org.zxtc.util.ExcelImport;
import org.zxtc.util.RawDataTransDO;

public class ExcelDataToDbMain {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            // TODO
        }
        String filename = args[0];
        InputStream is = ExcelImport.getInstance().getExcelInput(filename);
        if (is == null) {
            // TODO
            return;
        }

        Set<ExcelRawData> erds = ExcelImport.getInstance().poiParseExcel(is, filename);
        if (erds == null || erds.isEmpty()) {
            // TODO
            return;
        }
        Map<String, String> name2Id = ImportDbDao.getInstance().queryHorizonUser();
        List<List<HorizonUserDO>> hLists = RawDataTransDO.getInstance().compareExcelDataWithDb(erds, name2Id);
        List<HorizonUserDO> insertHList = hLists.get(0);
        List<HorizonUserDO> updateHList = hLists.get(1);
        System.out.println("更新to_horizon_user数量：" + updateHList.size());
        System.out.println("增加to_horizon_user数量：" + insertHList.size());
        RawDataTransDO.getInstance().setLoginNames(insertHList, name2Id.keySet());
        List<String> uuids = ImportDbDao.getInstance().queryUserBase();
        List<List<UserBaseDo>> ubList = RawDataTransDO.getInstance().compareExcelDataWithDb(erds, uuids);
        List<UserBaseDo> insertList = ubList.get(0);
        List<UserBaseDo> updateList = ubList.get(1);
        System.out.println("更新t_user_base数量：" + updateList.size());
        System.out.println("新增t_user_base数量：" + insertList.size());
        System.out.println("更新t_user_base：" + updateList);
        boolean isUpdate = ImportDbDao.getInstance().batchUpdate(updateList, updateHList);
        if(isUpdate){
            System.out.println("更新成功");
        }else{
            System.out.println("更新失败");
        }
        boolean isInsert = ImportDbDao.getInstance().batchInsert(insertList, insertHList);
        if(isInsert){
            System.out.println("新增成功");
        }else{
            System.out.println("新增失败");
        }
        
        ImportDbDao.getInstance().openConnection();
        Map<String, String> deptName2Id = ImportDbDao.getInstance().queryDept();
        Map<String, String> posName2Id = ImportDbDao.getInstance().queryPosition();
        List<String> deptUserIds = ImportDbDao.getInstance().queryUserDept4UserIds();
        List<String> positionIds = ImportDbDao.getInstance().queryUserPosition4UserIds();
        List<String> postUserIds = ImportDbDao.getInstance().queryPostEducation();
        List<List<UserPostEducation>> upeLists = RawDataTransDO.getInstance().comparePostEducation(postUserIds, erds);
        List<UserPostEducation> insertUpes = upeLists.get(0);
        List<UserPostEducation> updateUpes = upeLists.get(1);
        ImportDbDao.getInstance().insertPostEducation(insertUpes);
        ImportDbDao.getInstance().updatePostEducation(updateUpes);
        System.out.println("insert post education: " + insertUpes);
        System.out.println("update post education: " + updateUpes);
        ImportDbDao.getInstance().closeConnection();
        List<UserDepRel> insertUserDept = RawDataTransDO.getInstance().getInsertUserDept(erds, deptUserIds, deptName2Id);
        List<UserPositionRel> insertUserPosition = RawDataTransDO.getInstance().getInsertUserPosition(erds, positionIds, posName2Id);
        System.out.println("insert User dept size:" + insertUserDept.size()
                + ", insert user position size : " + insertUserPosition.size());
        boolean result = ImportDbDao.getInstance().batchInsertDeptPosition(insertUserDept, insertUserPosition);
        if(result){
            System.out.println("新增成功");
        }else{
            System.out.println("新增失败");
        }
    }
    
    
}
