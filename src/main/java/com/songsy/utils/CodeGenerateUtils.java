package com.songsy.utils;


import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码自动生成
 * @author songshuiyang
 * @date 2018/4/14 19:07
 */
public class CodeGenerateUtils {

    private static final Logger logger = LoggerFactory.getLogger(CodeGenerateUtils.class);

    // 作者
    private final String AUTHOR = "songsy";
    // 日期(默认当前时间)
    private final String CURRENT_DATE = format(new Date(),"yyyy-MM-dd HH:mm:ss");
    // 新生的实体类是否覆盖原有
    private final boolean isOverlayEntityFile = false;
    // 新生的Dao文件是否覆盖原有
    private final boolean isOverlayDaoFile = false;
    // 新生的Mapper是否覆盖原有
    private final boolean isOverlayMapperFile = false;
    // 项目根路径
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    // java文件路径
    private static final String JAVA_PATH = "/src/main/java";
    // 资源文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";

    public static final String BASE_PACKAGE = "com.songsy"; //项目基础包名称，根据自己公司的项目修改
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";//Controller所在包
    public static final String DAO_PACKAGE = BASE_PACKAGE + ".dao";//entity 所在包
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".entity";//entity 所在包

    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径
    private static final String PACKAGE_PATH_MODEL = packageConvertPath(MODEL_PACKAGE);//生成的entity存放路径
    private static final String PACKAGE_PATH_MAPPER = "/src/main/resources/mapper/";//生成的mapper存放路径
    private static final String PACKAGE_PATH_DAO = packageConvertPath(DAO_PACKAGE);//生成的dao存放路径

    // Entity 忽略生成字段
    //private static final String [] entityIgnoreColumefield = {"id","created_date","created_by","last_modified_date","last_modified_by","remarks","status","enable"};
    private static final String [] entityIgnoreColumefield = {};

    // jdbc url
    private final String URL = "jdbc:mysql://localhost:3306/graduation-project";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DRIVER = "com.mysql.jdbc.Driver";


    public static void main(String[] args) throws Exception{
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        // 表名 + 实体类名
        codeGenerateUtils.generate("sys_user","User","用戶表");

    }
    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection connection= DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
    /**
     * 生成文件
     * @param tableName 表名
     * @param entityName 实体类名
     * @param classAnnotation 类名注释
     * @throws Exception
     */
    public void generate(String tableName,String entityName,String classAnnotation) throws Exception{
        try {
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null,"%", tableName,"%");
            // 生成实体类文件
            //generateEntityFile(resultSet, tableName, entityName, classAnnotation);
            // 生成mapper文件
            //generateMapperFile(resultSet, tableName, entityName, classAnnotation);
            // 生成dao文件
            generateDaoFile(resultSet, tableName, entityName, classAnnotation);
            // 生成service文件
            // 生成service impl 文件
            // 生成controller 文件

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{

        }
    }

    /**
     * 生成实体类文件
     * @param resultSet
     * @param tableName
     * @param entityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateEntityFile(ResultSet resultSet,String tableName, String entityName, String classAnnotation) throws Exception{
        logger.info("----------------------- generate entity.java start -------------------");
        // 实体类名
        String entityNameStr;
        if (entityName == null) {
            entityNameStr =  replaceUnderLineAndUpperCase(tableName);
        } else {
            entityNameStr = entityName;
        }
        // 输出文件路径
        String path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MODEL + entityNameStr + ".java";
        logger.info("项目路径:  " + PROJECT_PATH);
        logger.info("Java路径:  " + JAVA_PATH);
        logger.info("entity路径:  " + PACKAGE_PATH_MODEL);
        logger.info("完整路径: "+ path);
        // 模板文件
        String templateName = "Entity.ftl";
        File mapperFile = new File(path);
        // 如果文件夹不存在
        if (!mapperFile.getParentFile().exists()) {
            mapperFile.getParentFile().mkdirs();
        } else { // 如果文件夹存在
            // 判断文件是否存在
            if (mapperFile.exists()) {
                // 是否覆盖文件
                if (!isOverlayEntityFile) {
                    entityNameStr = entityNameStr + System.currentTimeMillis();
                    logger.info("已有同名文件,生成备用文件, 文件名: " + entityNameStr + ".java");
                    mapperFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MODEL + entityNameStr + ".java");
                }
            }
        }
        // 表字段数据
        List<ColumnProperty> columnClassList = new ArrayList<>();
        ColumnProperty columnClass = null;
        while(resultSet.next()){
            // 共有字段忽略
            if(Arrays.asList(entityIgnoreColumefield).contains(resultSet.getString("COLUMN_NAME"))) {
                continue;
            }
            columnClass = new ColumnProperty();
            // 获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            // 获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            // 获取Java类型
            columnClass.setJavaType(getJavaTypeByJdbcType(resultSet.getString("TYPE_NAME")));
            // 数据库字段首字母小写且去掉下划线字符串
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            // 字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }

        generateFileByTemplate(templateName,MODEL_PACKAGE,tableName,entityName,classAnnotation,mapperFile,columnClassList);
        logger.info("----------------------- generate entity.java end -------------------");
    }



    /**
     * 生成dao文件
     * @param resultSet
     * @param tableName
     * @param entityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateDaoFile(ResultSet resultSet,String tableName, String entityName, String classAnnotation) throws Exception{
        logger.info("----------------------- generate mapper.java start -------------------");
        // 实体类名
        String entityNameStr;
        if (entityName == null) {
            entityNameStr =  replaceUnderLineAndUpperCase(tableName);
        } else {
            entityNameStr = entityName;
        }
        // 文件输出路径
        String path = PROJECT_PATH +  JAVA_PATH + PACKAGE_PATH_DAO + entityNameStr + "Mapper"+ ".java";
        logger.info("项目路径:  " + PROJECT_PATH);
        logger.info("dao路径:  " + PACKAGE_PATH_DAO);
        logger.info("完整路径: "+ path);
        // 模板文件名
        String templateName = "Dao.ftl";
        File mapperFile = new File(path);
        // 如果文件夹不存在
        if (!mapperFile.getParentFile().exists()) {
            mapperFile.getParentFile().mkdirs();
        } else { // 如果文件夹存在
            // 判断文件是否存在
            if (mapperFile.exists()) {
                // 是否覆盖文件
                if (!isOverlayDaoFile) {
                    entityNameStr = entityNameStr + System.currentTimeMillis();
                    logger.info("已有同名文件,生成备用文件, 文件名: " + entityNameStr + "Mapper"+ ".java");
                    mapperFile = new File(PROJECT_PATH +  JAVA_PATH + PACKAGE_PATH_DAO + entityNameStr + "Mapper"+ ".java");
                }
            }
        }
        // 表字段数据
        List<ColumnProperty> columnClassList = new ArrayList<>();
        ColumnProperty columnProperty = null;
        while(resultSet.next()){
            // 共有字段忽略 id是必须
            if(Arrays.asList(entityIgnoreColumefield).contains(resultSet.getString("COLUMN_NAME"))) {
                continue;
            }
            columnProperty = new ColumnProperty();
            // 获取字段名称
            columnProperty.setColumnName(resultSet.getString("COLUMN_NAME"));
            // 获取字段类型
            columnProperty.setColumnType(resultSet.getString("TYPE_NAME"));
            // 获取Java类型
            columnProperty.setJavaType(getJavaTypeByJdbcType(resultSet.getString("TYPE_NAME")));
            // 数据库字段首字母小写且去掉下划线字符串
            columnProperty.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            // 字段在数据库的注释
            columnProperty.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnProperty);
        }
        // 包名
        generateFileByTemplate(templateName,DAO_PACKAGE,tableName,entityName,classAnnotation,mapperFile,columnClassList);
        logger.info("----------------------- generate mapper.java end -------------------");
    }

    /**
     * 生成mapper文件
     * @param resultSet
     * @param tableName
     * @param entityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateMapperFile(ResultSet resultSet,String tableName, String entityName,String classAnnotation) throws Exception{
        logger.info("----------------------- generate mapper.xml start -------------------");
        // 实体类名
        String entityNameStr;
        if (entityName == null) {
            entityNameStr =  replaceUnderLineAndUpperCase(tableName);
        } else {
            entityNameStr = entityName;
        }
        // 文件输出路径
        String path = PROJECT_PATH + PACKAGE_PATH_MAPPER + entityNameStr + "Mapper"+ ".xml";
        logger.info("项目路径:  " + PROJECT_PATH);
        logger.info("mapper路径:  " + PACKAGE_PATH_MAPPER);
        logger.info("完整路径: "+ path);
        // 模板文件名
        String templateName = "Mapper.ftl";
        File mapperFile = new File(path);
        // 如果文件夹不存在
        if (!mapperFile.getParentFile().exists()) {
            mapperFile.getParentFile().mkdirs();
        } else { // 如果文件夹存在
            // 判断文件是否存在
            if (mapperFile.exists()) {
                // 是否覆盖文件
                if (!isOverlayMapperFile) {
                    entityNameStr = entityNameStr + System.currentTimeMillis();
                    logger.info("已有同名文件,生成备用文件, 文件名: " + entityNameStr + "Mapper"+ ".xml");
                    mapperFile = new File(PROJECT_PATH + PACKAGE_PATH_MAPPER + entityNameStr + "Mapper"+ ".xml");
                }
            }
        }
        // 表字段数据
        List<ColumnProperty> columnClassList = new ArrayList<>();
        ColumnProperty columnProperty = null;
        while(resultSet.next()){
            // 共有字段忽略 id是必须
            if(Arrays.asList(entityIgnoreColumefield).contains(resultSet.getString("COLUMN_NAME"))) {
                continue;
            }
            columnProperty = new ColumnProperty();
            // 获取字段名称
            columnProperty.setColumnName(resultSet.getString("COLUMN_NAME"));
            // 获取字段类型
            columnProperty.setColumnType(resultSet.getString("TYPE_NAME"));
            // 获取Java类型
            columnProperty.setJavaType(getJavaTypeByJdbcType(resultSet.getString("TYPE_NAME")));
            // 数据库字段首字母小写且去掉下划线字符串
            columnProperty.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            // 字段在数据库的注释
            columnProperty.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnProperty);
        }
        // 包名
        generateFileByTemplate(templateName,MODEL_PACKAGE,tableName,entityName,classAnnotation,mapperFile,columnClassList);
        logger.info("----------------------- generate mapper.xml end -------------------");
    }
    /**
     * 根据模板生成文件
     * @param templateName 模板文件名
     * @param packageName 包名
     * @param tableName table名
     * @param entityName 实体类名
     * @param classAnnotation 类注释
     * @param file file
     * @param columnClassList 表格字段数据
     * @throws Exception
     */
    private void generateFileByTemplate(String templateName,String packageName,String tableName,String entityName,String classAnnotation,File file,List<ColumnProperty> columnClassList) throws Exception{
        Map<String,Object> dataMap = new HashMap<>();
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        /**
         * columnName       - 字段名
         * columnType       - 字段类型
         * changeColumnName - 实体类名
         * javaType         - 实体类类型
         * columnComment    - 字段注释
         */
        dataMap.put("entity_column",columnClassList);
        // 表名
        dataMap.put("table_name",tableName);
        // 实体类名
        dataMap.put("entity_name",entityName);
        // 作者
        dataMap.put("author",AUTHOR);
        // 时间
        dataMap.put("date",CURRENT_DATE);
        // mapper路径
        dataMap.put("mapper_packege",MODEL_PACKAGE);
        // entity包路径
        dataMap.put("entity_packege",MODEL_PACKAGE);
        // 包路径
        dataMap.put("package_name",packageName);
        // 类注释
        dataMap.put("table_annotation",classAnnotation);

        // 特殊字符
        dataMap.put("char_1","#");
        // 主键id JAVA类型
        dataMap.put("id_java_type",getIdJavaType(columnClassList));
        // 主键id JDBC类型
        dataMap.put("id_jdbc_type",getIdJdbcType(columnClassList));
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        template.process(dataMap,out);
    }

    /**
     * 将类名转化为实体名
     * @param str
     * @return
     */
    public String replaceUnderLineAndUpperCase(String str){
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while(count!=0){
            int num = sb.indexOf("_",count);
            count = num + 1;
            if(num != -1){
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count , count + 1,ia + "");
            }
        }
        String result = sb.toString().replaceAll("_","");
        return StringUtils.capitalize(result);
    }
    /**
     * 将包路径转换成文件路径
     * eg:
     *  com.ecut.admin.generator.web => /com/ecut/admin/generator/web/
     * @param packageName
     * @return
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 获取表主键id Java类型
     * @param columnClassList
     * @return
     */
    public static String getIdJavaType(List<ColumnProperty> columnClassList) {
        for(ColumnProperty columnProperty : columnClassList) {
            if (columnProperty.getColumnName().equals("id")) {
                return columnProperty.getJavaType();
            }
        }
        return "";
    }
    /**
     * 获取表主键id jdbc类型
     * @param columnClassList
     * @return
     */
    public static String getIdJdbcType(List<ColumnProperty> columnClassList) {
        for(ColumnProperty columnProperty : columnClassList) {
            if (columnProperty.getColumnName().equals("id")) {
                return columnProperty.getColumnType();
            }
        }
        return "";
    }

    /**
     * 根据jdbc类型获取对应的Java类型
     * @param jdbcType
     * @return
     */
    public static String getJavaTypeByJdbcType(String jdbcType) {
        if (jdbcType.equals("CHAR") || jdbcType.equals("VARCHAR") ||
                jdbcType.equals("LONGVARCHAR") || jdbcType.equals("TEXT") ||
                jdbcType.equals("CLOB") || jdbcType.equals("BLOB")) {
            return "String";
        }
        if (jdbcType.equals("DATE") ||  jdbcType.equals("TIME")  ||
                jdbcType.equals("TIMESTAMP") || jdbcType.equals("DATETIME")  ||
                jdbcType.equals("YARN")) {
            return  "Date";
        }
        if (jdbcType.equals("BIT") ||  jdbcType.equals("BOOLEAN")) {
            return  "Boolean";
        }
        if (jdbcType.equals("TINYINT")) {
            return  "Byte";
        }
        if (jdbcType.equals("SMALLINT")) {
            return  "Short";
        }
        if (jdbcType.equals("INTEGER") || jdbcType.equals("INT")) {
            return  "Integer";
        }
        if (jdbcType.equals("BIGINT")) {
            return  "Long";
        }
        if (jdbcType.equals("REAL")) {
            return  "Float";
        }
        if (jdbcType.equals("DOUBLE") ||  jdbcType.equals("FLOAT")) {
            return  "Double";
        }
        if (jdbcType.equals("BINARY") ||  jdbcType.equals("VARBINARY") ||  jdbcType.equals("LONGVARBINARY") ) {
            return  "byte[]";
        }
        return "";
    }
}
