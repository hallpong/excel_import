package org.zxtc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zxtc.constant.ExcelColNameConstant;
import org.zxtc.entity.ExcelRawData;

/**
 * 
 * @description: 导入Excel的工具类；
 * @author: hao.peng
 * @date: 2017年12月27日 上午9:43:55
 */
public class ExcelImport {
    private static final ExcelImport excelImport = new ExcelImport();
    
    private ExcelImport(){
        
    }
    
    public static ExcelImport getInstance(){
        return excelImport;
    }
    
    /**
     * 解析excel输入流为数据；
     * @description:
     * @author: hao.peng
     * @date: 2017年12月27日 上午9:56:57 
     * @param：is excel的输入流; filename Excel文件名；
     * @return： 
     */
    public Set<ExcelRawData> poiParseExcel(InputStream is, String filename){
        Set<ExcelRawData> result = new HashSet<ExcelRawData>();
        Workbook wb = null;
        try{
            if(filename.endsWith(".xls")){
                wb = new HSSFWorkbook(is);
            }else if(filename.endsWith(".xlsx")){
                wb = new XSSFWorkbook(is);
            }else{
                System.out.println("文件名异常");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        if(wb == null){
            return null;
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet == null){
            System.out.println("第一个sheet为空");
        }
        int firstRowNum = sheet.getFirstRowNum();//第一行是列名；
        Row firstRow = sheet.getRow(firstRowNum);
        if(firstRow == null){//empty
            //TODO
            return result;
        }
        Map<String, Integer> colName2Idx = new HashMap<String, Integer>();
        for (Cell cell : firstRow) {
            String value = cell.getStringCellValue();//先暂时只处理String 类型；
            int index = cell.getColumnIndex();
            colName2Idx.put(value, index);
        }
        if(colName2Idx.get(ExcelColNameConstant.NAME) == null){//不存在姓名列，则如何；
            //TODO
            System.out.println("不存在姓名列!");
            return result;
        }
        
        int lastRowNum = sheet.getLastRowNum();
        //TODO
        System.out.println("firstRowNum is " + firstRowNum + "lastRowNum is " + lastRowNum);
        for(int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++){
            Row row = sheet.getRow(rowNum);
            if(row == null){
                continue;
            }
            ExcelRawData erd = buildRawData(row, colName2Idx);
            if(erd == null){
                System.out.println("[losses] 丢弃第" + row.getRowNum()+1 + "行数据");
                continue;
            }
           if(result.contains(erd)){
                System.out.println("该行有重复名字，该行被舍弃，行号为：" + row.getRowNum());
            }else if(erd!=null){
                result.add(erd);
            }
        }
        System.out.println("Excel导入"+ result.size() +"条数据");
        System.out.println(result);
        return result;
    }
    
    /**
     * 
     * @description:将Excel的每一行构建为一个ExcelRawData对象，为解析Excel的核心部分；
     * @author: hao.peng
     * @date: 2017年12月28日 下午5:16:54 
     * @param：
     * @return：
     */
    private ExcelRawData buildRawData(Row row, Map<String, Integer> colName2Idx ) {
        ExcelRawData erd = new ExcelRawData();
        for(String colName: colName2Idx.keySet()){
            int colIdx = colName2Idx.get(colName);
            Cell cell = row.getCell(colIdx);
            if(cell == null){
                continue;
            }
            String value = null;
            try{
                value = getCellValue(cell);
            }catch(Exception e){
                //TODO
                e.printStackTrace();
            }
            
            switch (colName) {
            case ExcelColNameConstant.NAME:
                erd.setName(value);
                break;
            case ExcelColNameConstant.AFFILIATE_PARTY:
                erd.setAffiliatedParty(value);
                break;
            case ExcelColNameConstant.AGE:
                erd.setAge(value);
                break;
            case ExcelColNameConstant.BACKUP_WORK_LEADER:
                erd.setBackup_workUnitLeader(value);
                break;
            case ExcelColNameConstant.BIRTY_DATE:
                erd.setBirthDate(value);
                break;
            case ExcelColNameConstant.DEPT:
                erd.setDept(value);
                break;
            case ExcelColNameConstant.EDUCATION:
                erd.setEducation(value);
                break;
            case ExcelColNameConstant.FULL_TIME:
                erd.setFullTime(value);
                break;
            case ExcelColNameConstant.HIGH_RAIL_RESERVE:
                erd.setHighrailReserve(value);
                break;
            case ExcelColNameConstant.ID_CARD_CODE:
                erd.setIdCardCode(value);
                break;
            case ExcelColNameConstant.ID_GRADE:
                erd.setIdGrade(value);
                break;
            case ExcelColNameConstant.ID_SUBJECT:
                erd.setIdSubject(value);
                break;
            case ExcelColNameConstant.IDENTITY:
                erd.setIdentity(value);
                break;
            case ExcelColNameConstant.JOB_NUM:
                erd.setJobNum(value);
                break;
            case ExcelColNameConstant.JOB_STATUS:
                erd.setJobStatus(value);
                break;
            case ExcelColNameConstant.JOB_TITLE:
                erd.setJobTitle(value);
                break;
            case ExcelColNameConstant.JOIN_PARTY_TIME:
                erd.setJoinPartyTime(value);
                break;
            case ExcelColNameConstant.JOIN_TIME:
                erd.setJoinTime(value);
                break;
            case ExcelColNameConstant.OFFICE:
                erd.setOffice(value);
                break;
            case ExcelColNameConstant.POLITICAL_VISAGE:
                erd.setPoliticalVisage(value);
                break;
            case ExcelColNameConstant.POST_NATURE:
                erd.setPostNature(value);
                break;
            case ExcelColNameConstant.PROFESSIONAL_TITLE:
                erd.setProfessionalTitle(value);
                break;
            case ExcelColNameConstant.RACE:
                erd.setRace(value);
                break;
            case ExcelColNameConstant.SEX:
                erd.setSex(value);
                break;
            case ExcelColNameConstant.START_WORK_TIME:
                if(value.length()>20){
                    value = value.substring(0, 10);
                }
                erd.setStartWorkTime(value);
                break;
            case ExcelColNameConstant.TRAINING_CERTIFY_CODE:
                erd.setTrainingCertifyCode(value);
                break;
            case ExcelColNameConstant.TRAINING_CERTIFY_TIME:
                erd.setTrainingCertifyTime(value);
                break;
            case ExcelColNameConstant.WORK_LEADER:
                erd.setWorkUnitLeader(value);
                break;
            case ExcelColNameConstant.WORK_YEARS:
                erd.setWorkYears(value);
                break;
            case ExcelColNameConstant.TECHNICAL_FILE_CODE:
                erd.setTechFileCode(value);
                break;
            case ExcelColNameConstant.TELEPHONE:
                erd.setContact(value);
                break;
            default:
                break;
            }
        }
        if(erd.getName()==null || "".equals(erd.getName().trim())){
            //TODO
            System.out.println("姓名为空，丢掉该行数据，行号：" + (row.getRowNum()+1));
            return null;
        }
        return erd;
    }
    
    private String getCellValue(Cell cell){
        String result = null;
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            result = cell.getRichStringCellValue().getString();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                result = sdf.format(date);
            } else {
                double value = cell.getNumericCellValue();
                result = new BigDecimal(value).toPlainString();
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            System.out.println(cell.getRowIndex()+1 + "行；" + (cell.getColumnIndex()+1) + "列；该单元格是布尔值");
            break;
        case Cell.CELL_TYPE_FORMULA:
            System.out.println(cell.getRowIndex()+1 + "行；" + (cell.getColumnIndex()+1)+ "列；该单元格是公式");
            break;
        case Cell.CELL_TYPE_BLANK:
            System.out.println(cell.getRowIndex()+1 + "行；" + (cell.getColumnIndex()+1)+ "列；该单元格是空值");
            break;
        default:
            System.out.println(cell.getRowIndex()+1 + "行；" + (cell.getColumnIndex()+1)+ "列；未知格式");
           break;
        }
        return result;
    }
    

    /**
     * 获取Excel的输入流；先这样使用，如果集成到项目当中，该方法可以被替换；
     * @description:
     * @author: hao.peng
     * @date: 2017年12月27日 上午10:00:30 
     * @param：
     * @return：
     */
    public InputStream getExcelInput(String filename){
        String pathname = System.getProperty("user.dir") + File.separator + filename;
        File file = new File(pathname);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            //TODO 暂时这样处理，后续加入log4j;
            e.printStackTrace();
        }
        return is;
    }
}
