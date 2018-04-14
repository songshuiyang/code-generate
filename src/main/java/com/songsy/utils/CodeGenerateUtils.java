package com.songsy.utils;


import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

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
    // 作者
    private final String AUTHOR = "";
    // 日期
    private final String CURRENT_DATE = format(new Date(),"yyyy-MM-dd HH:mm:ss");


    private final boolean isOverlayModelFile = false;
    // 包名
    private final String packageName = "com.songsy";

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
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".entity";//model 所在包
    public static final String DAO_PACKAGE = BASE_PACKAGE + ".dao";//model 所在包

    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径
    private static final String PACKAGE_PATH_MODEL = packageConvertPath(MODEL_PACKAGE);//生成的model存放路径

    // Model 忽略生成字段
    private static final String [] modelIgnoreColumefield = {"id","created_date","created_by","last_modified_date","last_modified_by","remarks","status","enable"};

    // jdbc url
    private final String URL = "jdbc:mysql://localhost:3306/graduation-project";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DRIVER = "com.mysql.jdbc.Driver";

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

    public static void main(String[] args) throws Exception{
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        // 表名 + 实体类名
        codeGenerateUtils.generate("sys_user","User","用戶表");

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
            generateModelFile(resultSet, tableName, entityName, classAnnotation);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{

        }
    }

    /**
     * 生成实体类
     * @param resultSet
     * @param tableName
     * @param entityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateModelFile(ResultSet resultSet,String tableName, String entityName, String classAnnotation) throws Exception{
        String entityNameStr;
        if (entityName == null) {
            entityNameStr =  replaceUnderLineAndUpperCase(tableName);
        } else {
            entityNameStr = entityName;
        }

        final String path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MODEL + entityNameStr + ".java";
        System.out.println("PROJECT_PATH:  "+ PROJECT_PATH);
        System.out.println("JAVA_PATH:  "+ JAVA_PATH);
        System.out.println("PACKAGE_PATH_CONTROLLER:  "+ PACKAGE_PATH_MODEL);
        System.out.println(path);

        final String templateName = "Entity.ftl";
        File mapperFile = new File(path);
        // 如果文件夹不存在
        if (!mapperFile.getParentFile().exists()) {
            mapperFile.getParentFile().mkdirs();
        } else { // 如果文件夹存在
            // 判断文件是否存在
            if (mapperFile.exists()) {
                // 是否覆盖文件
                if (!isOverlayModelFile) {
                    entityNameStr = entityNameStr + System.currentTimeMillis();
                    mapperFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MODEL + entityNameStr + ".java");
                }
            }
        }
        List<ColumnProperty> columnClassList = new ArrayList<>();
        ColumnProperty columnClass = null;
        while(resultSet.next()){
            // 共有字段忽略
            if(Arrays.asList(modelIgnoreColumefield).contains(resultSet.getString("COLUMN_NAME"))) {
                continue;
            }
            columnClass = new ColumnProperty();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));

            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("model_column",columnClassList);
        generateFileByTemplate(templateName,tableName,entityName,classAnnotation,mapperFile,dataMap);

    }

    /**
     * 根据模板生成文件
     * @param templateName
     * @param tableName
     * @param entityName
     * @param file
     * @param dataMap
     * @throws Exception
     */
    private void generateFileByTemplate(final String templateName,String tableName,String entityName,String classAnnotation,File file,Map<String,Object> dataMap) throws Exception{
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("table_name",tableName);
        dataMap.put("model_name",entityName);
        dataMap.put("author",AUTHOR);
        dataMap.put("date",CURRENT_DATE);
        dataMap.put("package_name",packageName);
        dataMap.put("table_annotation",classAnnotation);
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
        System.out.println("转化前"+ packageName);
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
}
