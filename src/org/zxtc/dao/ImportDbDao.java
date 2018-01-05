package org.zxtc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zxtc.entity.HorizonUserDO;
import org.zxtc.entity.UserBaseDo;
import org.zxtc.entity.UserDepRel;
import org.zxtc.entity.UserPositionRel;
import org.zxtc.entity.UserPostEducation;


public class ImportDbDao {

    public static final ImportDbDao itd = new ImportDbDao();
    
    private String db_url;
    private String db_user;
    private String db_pwd;
    private Connection conn;
    
    private ImportDbDao(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //TODO 
            System.out.println("成功加载mysql驱动");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getDbInfo();
    }
    
    private void getDbInfo(){
        InputStream is = null;
        Properties property = null;
        try {
            is = new FileInputStream(new File("db.properties"));
            property = new Properties();
            property.load(is);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db_url = property.getProperty("jdbcUrl");
        db_user = property.getProperty("username");
        db_pwd = property.getProperty("password");
    }
    
    public void openConnection(){
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_pwd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public static ImportDbDao getInstance(){
        return itd;
    }
    
    /**
     * 
     * @description:获取t_user_base表的uuid的集合；
     * @author: hao.peng
     * @date: 2017年12月28日 下午9:16:14 
     * @param：
     * @return：
     */
    public List<String> queryUserBase(){
        List<String> result = new ArrayList<String>();
        String sql = "SELECT u.USER_ID FROM t_user_base AS u";
        openConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String uuid = rs.getString("USER_ID");
                result.add(uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection();
        return result;
    }
    
    /**
     * 
     * @description:获取to_horizon_user表的name和uuid
     * @author: hao.peng
     * @date: 2017年12月28日 下午9:17:38 
     * @param：
     * @return：key--name, value--userid
     */
    public Map<String, String> queryHorizonUser(){
        Map<String, String> result = new HashMap<String, String>();
        String sql = "SELECT u.ID, u.NAME FROM to_horizon_user AS u";
        openConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String uuid = rs.getString("ID");
                String name = rs.getString("NAME");
                result.put(name, uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection();
        return result;
    }
    
    /**
     * 
     * @description:进行更新的集合；
     * @author: hao.peng
     * @date: 2017年12月28日 下午6:25:07 
     * @param：
     * @return：
     */
    public boolean batchUpdate(List<UserBaseDo> ubds, List<HorizonUserDO> huds){
        boolean result = true;
        if(ubds == null || huds == null){
            return false;
        }
        openConnection();
        if(conn == null){
            System.out.println("未获取到连接对象");
            return false;
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        PreparedStatement ups = null;
        PreparedStatement hps = null;
        String uSql = "UPDATE t_user_base SET NATION=?, WORK_STARTTIME=?, POLITICAL_VISAGE=?, EDUCATION=?, POST_NAME=?, "
                    + "HIGHSPEED_RAIL=?, PERSONNEL_CATEGORY=?, CADRE_PERSONNEL=?, APPRAISAL_SUBJECT=?, APPRAISAL_LEVEL=?, "
                    + "JOINPARTY_TIME=?, WORK_CODE=?, INRAILWAY_TIME=?, PARTY_BRANCH=?, FULL_TIME_DEGREE=?, POSTPROPERTIES=? "
                    + "WHERE USER_ID=?";
        String hSql = "UPDATE to_horizon_user SET ACTIVE=?, BIRTH_DATE=?, CERT_NO=?, SEX=?, TELEPHONE=? WHERE ID=?";
        try {
            ups = conn.prepareStatement(uSql);
            hps = conn.prepareStatement(hSql);
            for (UserBaseDo udo : ubds) {
                ups.setString(1, udo.getNation());
                ups.setString(2, udo.getWorkStartTime());
                ups.setString(3, udo.getPoliticalVisage());
                ups.setString(4, udo.getEducation());
                ups.setString(5, udo.getPostName());
                ups.setString(6, udo.getHighSpeedRail());
                ups.setString(7, udo.getPersonnelCategory());
                ups.setString(8, udo.getCadrePersonnel());
                ups.setString(9, udo.getAppraisalSubject());
                ups.setString(10, udo.getAppraisalLevel());
                ups.setString(11, udo.getJoinPartyTime());
                ups.setString(12, udo.getWorkCode());
                ups.setString(13, udo.getInRailwayTime());
                ups.setString(14, udo.getPartyBranch());
                ups.setString(15, udo.getFullTimeDegree());
                ups.setString(16, udo.getPostProperty());
                ups.setString(17, udo.getUserId());
                ups.addBatch();
            }
            ups.executeBatch();
            for (HorizonUserDO hdo : huds) {
                hps.setString(1, hdo.getActive());
                hps.setString(2, hdo.getBirthDate());
                hps.setString(3, hdo.getCertNo());
                hps.setString(4, hdo.getSex());
                hps.setString(5, hdo.getTelephone());
                hps.setString(6, hdo.getId());
                hps.addBatch();
            }
            hps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            result = false;
        }
        closeConnection();
        return result;
    }
    
    public boolean batchInsert(List<UserBaseDo> ubds, List<HorizonUserDO> huds) {// 批量插入的接口
        boolean result = true;
        if(ubds == null || huds == null){
            return false;
        }
        openConnection();
        if(conn == null){
            System.out.println("未获取到连接对象");
            return false;
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        PreparedStatement ups = null;
        PreparedStatement hps = null;
        String uSql = "INSERT INTO t_user_base(USER_ID, APPRAISAL_LEVEL, APPRAISAL_SUBJECT, CADRE_PERSONNEL, EDUCATION, "
                    + "HIGHSPEED_RAIL, INRAILWAY_TIME, JOINPARTY_TIME, NATION, PERSONNEL_CATEGORY, POLITICAL_VISAGE, POST_NAME, "
                    + "WORK_CODE, WORK_STARTTIME, PARTY_BRANCH) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String hSql = "INSERT INTO to_horizon_user(ID, NAME, ACTIVE, BIRTH_DATE, LOGIN_NAME, ORDER_NO, PASSWORD, PPASSWORD, "
                    + "pym, REGISTER_TIME, SEX, TELEPHONE, USER_STYLE, CERT_NO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ups = conn.prepareStatement(uSql);
            hps = conn.prepareStatement(hSql);
            for (UserBaseDo udo : ubds) {
                ups.setString(1, udo.getUserId());
                ups.setString(2, udo.getAppraisalLevel());
                ups.setString(3, udo.getAppraisalSubject());
                ups.setString(4, udo.getCadrePersonnel());
                ups.setString(5, udo.getEducation());
                ups.setString(6, udo.getHighSpeedRail());
                ups.setString(7, udo.getInRailwayTime());
                ups.setString(8, udo.getJoinPartyTime());
                ups.setString(9, udo.getNation());
                ups.setString(10, udo.getPersonnelCategory());
                ups.setString(11, udo.getPoliticalVisage());
                ups.setString(12, udo.getPostName());
                ups.setString(13, udo.getWorkCode());
                ups.setString(14, udo.getWorkStartTime());
                ups.setString(15, udo.getPartyBranch());
                ups.addBatch();
            }
            ups.executeBatch();
            for (HorizonUserDO hdo : huds) {
                hps.setString(1, hdo.getId());
                hps.setString(2, hdo.getName());
                hps.setString(3, hdo.getActive());
                hps.setString(4, hdo.getBirthDate());
                hps.setString(5, hdo.getLoginName());
                hps.setString(6, hdo.getOrderNo());
                hps.setString(7, hdo.getPassword());
                hps.setString(8, hdo.getPpassword());
                hps.setString(9, hdo.getPym());
                hps.setString(10, hdo.getRegisterTime());
                hps.setString(11, hdo.getSex());
                hps.setString(12, hdo.getTelephone());
                hps.setString(13, hdo.getUserStyle());
                hps.setString(14, hdo.getCertNo());
                hps.addBatch();
            }
            hps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            result = false;
        }
        closeConnection();
        return result;
    }
    
    /**
     * 
     * @description:
     * @author: hao.peng
     * @date: 2018年1月3日 下午4:44:42 
     * @param：
     * @return：
     */
    public List<String> queryUserDept4UserIds(){
        List<String> result = new ArrayList<String>();
        if(conn == null){
            System.out.println("未获取到connection");
            return result;
        }
        String sql = "SELECT t.USER_ID FROM tor_horizon_user_dept AS t";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String user_id = rs.getString("USER_ID");
                result.add(user_id);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * @description:
     * @author: hao.peng
     * @date: 2018年1月3日 下午4:44:46 
     * @param：
     * @return：
     */
    public List<String> queryUserPosition4UserIds(){
        List<String> result = new ArrayList<String>();
        if(conn == null){
            System.out.println("未获取到connection");
            return result;
        }
        String sql = "SELECT t.USER_ID FROM tor_horizon_user_position AS t";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String user_id = rs.getString("USER_ID");
                result.add(user_id);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * @description:
     * @author: hao.peng
     * @date: 2018年1月3日 下午4:45:33 
     * @param：
     * @return：
     */
    public Map<String, String> queryDept(){
        Map<String, String> result = new HashMap<String, String>();
        if(conn == null){
            System.out.println("未获取到connection");
            return result;
        }
        String sql = "SELECT t.DEPT_NAME, t.ID FROM to_horizon_dept AS t";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String deptName = rs.getString("DEPT_NAME");
                String deptId = rs.getString("ID");
                result.put(deptName, deptId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public Map<String, String> queryPosition(){
        Map<String, String> result = new HashMap<String, String>();
        if(conn == null){
            System.out.println("未获取到connection");
            return result;
        }
        String sql = "SELECT t.POSITION_NAME, t.ID FROM to_horizon_position AS t";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String positionName = rs.getString("POSITION_NAME");
                String positionId = rs.getString("ID");
                result.put(positionName, positionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public boolean batchInsertDeptPosition(List<UserDepRel> udrs, List<UserPositionRel> uprs ){
        boolean result = true;
        if(udrs == null || uprs == null){
            return false;
        }
        openConnection();
        if(conn == null){
            System.out.println("未获取到连接对象");
            return false;
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        PreparedStatement ups = null;
        PreparedStatement hps = null;
        String uSql = "INSERT INTO tor_horizon_user_dept(ID, USER_ID, DEPT_ID) VALUES(?, ?, ?)";
        String hSql = "INSERT INTO tor_horizon_user_position(ID, USER_ID, POSITION_ID) VALUES(?, ?, ?)";
        try {
            ups = conn.prepareStatement(uSql);
            hps = conn.prepareStatement(hSql);
            for(UserDepRel udept: udrs){
                ups.setString(1, udept.getId());
                ups.setString(2, udept.getUserId());
                ups.setString(3, udept.getDeptId());
                ups.addBatch();
            }
            ups.executeBatch();
            for(UserPositionRel upo: uprs){
                hps.setString(1, upo.getId());
                hps.setString(2, upo.getUserId());
                hps.setString(3, upo.getPositionId());
                hps.addBatch();
            }
            hps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            result = false;
        }
        closeConnection();
        return result;
    }
    
    public List<String> queryPostEducation(){
        List<String> result = new ArrayList<String>();
        String sql = "SELECT t.user_id FROM t_user_posteducation AS t";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String userId = rs.getString("user_id");
                result.add(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public boolean insertPostEducation(List<UserPostEducation> upes){
        boolean result = true;
        if(conn==null){
            System.out.println("未能获取到数据库连接");
            return false;
        }
        String sql = "INSERT INTO t_user_posteducation(user_id, position_training_certificate_number, "
                + "post_training_certificate_issuing_date, technical_file_number) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (UserPostEducation pe : upes) {
                ps.setString(2, pe.getCertifyNo());
                ps.setString(3, pe.getCertifyDate());
                ps.setString(4, pe.getTechnicalFileNumber());
                ps.setString(1, pe.getUserId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public boolean updatePostEducation(List<UserPostEducation> upes){
        boolean result = true;
        if(conn==null){
            System.out.println("未能获取到数据库连接");
            return false;
        }
        String sql = "UPDATE t_user_posteducation SET position_training_certificate_number=?, post_training_certificate_issuing_date=?, "
                + "technical_file_number=? WHERE user_id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (UserPostEducation pe : upes) {
                ps.setString(1, pe.getCertifyNo());
                ps.setString(2, pe.getCertifyDate());
                ps.setString(3, pe.getTechnicalFileNumber());
                ps.setString(4, pe.getUserId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
