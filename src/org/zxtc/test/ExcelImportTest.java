package org.zxtc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zxtc.dao.ImportDbDao;
import org.zxtc.util.ExcelImport;
import org.zxtc.util.PinyinUtil;

/**
 *
 * @description: junit测试类；
 * @author: hao.peng
 * @date: 2017年12月27日 上午10:07:56
 */
public class ExcelImportTest {

    private ExcelImport ei = null;
    private ImportDbDao idd = null;
    
    @Before
    public void before(){
        ei = ExcelImport.getInstance();
        idd = ImportDbDao.getInstance();
    }
    
    @After
    public void after(){
        
    }
    
    @Test
    public void testIs(){
        String property = System.getProperty("user.dir");
        InputStream is = ei.getExcelInput(property + "/excel/test.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String line = br.readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testQuery(){
        Map<String, String> map = idd.queryHorizonUser();
        System.out.println(map.size());
        System.out.println(map);
    }
    
    @Test
    public void testUQuery(){
        List<String> uuids = idd.queryUserBase();
        System.out.println(uuids);
        System.out.println(uuids.size());
    }
    
    @Test
    public void testHanzi(){
        String a = "我们都是中国人（）";
        String result1= PinyinUtil.getPinYinHeadChar(a);
        System.out.println(result1);
        String b = "人人都有一段不堪回首";
        System.out.println(PinyinUtil.getPinYinHeadChar(b));
    }
    
    @Test
    public void testQueryDeptUsers(){
        idd.openConnection();
        List<String> result = idd.queryUserDept4UserIds();
        System.out.println(result.size());
        idd.closeConnection();
    }
    
    @Test
    public void testQueryDept(){
        idd.openConnection();
        Map<String, String> result = idd.queryDept();
        System.out.println(result);
        idd.closeConnection();
    }
    
    @Test
    public void testQueryPostEducation(){
        idd.openConnection();
        List<String> queryPostEducation = idd.queryPostEducation();
        System.out.println(queryPostEducation);
        idd.closeConnection();
    }
}
