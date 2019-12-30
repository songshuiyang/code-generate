package com.songsy.v2;

import com.sun.xml.internal.ws.util.StringUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
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
 * 代码自动生成工具类 优化版本V2
 *
 * @author songsy
 * @date 2019/11/8 13:34
 */
public class CodeGenerateUtils {

    private static final Logger logger = LoggerFactory.getLogger(CodeGenerateUtils.class);
    // 作者
    private final String AUTHOR = "songsy";
    // 日期(默认当前时间)
    private final String CURRENT_DATE = format(new Date(), "yyyy-MM-dd HH:mm:ss");

    // 项目根路径
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    // 模板文件 src\main\resources\templates
    private static final String TEMPLATE_PATH = "/templates/v2";

    // 模块名
    private static final String ENTITY_MODULE_PATH = "/member-entity";
    private static final String SERVICE_MODULE_PATH = "/member-service";
    private static final String CONTROLLER_MODULE_PATH = "/member-admin";
    private static final String DAO_MODULE_PATH = "/member-mapper";

    // Java文件路径
    private static final String JAVA_PATH = "/src/main/java";
    // 资源文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";

    // 项目基础包名称，根据项目名称修改
    public static final String BASE_PACKAGE = "com.songsy.member";

    // entity 所在包
    public static final String ENTITY_PACKAGE = BASE_PACKAGE + ".entity.mysql";
    // Service所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";
    // ServiceImpl所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    // Controller所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".admin.web";
    // entity所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";


    // 生成的entity存放路径
    private static final String PACKAGE_PATH_ENTITY = packageConvertPath(ENTITY_PACKAGE);
    // 生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);
    // 生成的Service实现存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);
    // 生成的Controller存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);
    // 生成的mapper存放路径
    private static final String PACKAGE_PATH_MAPPER = "/src/main/resources/mapper/";
    // 生成的dao存放路径
    private static final String PACKAGE_PATH_DAO = packageConvertPath(MAPPER_PACKAGE);

    // Entity 忽略生成字段 排除BaseDO字段
    private static final String[] ignoreColumefield = {"id", "create_time", "create_by", "update_time", "update_by", "version", "enable"};
    private static final String[] allColumefield = {};

    // 数据库连接
    private final String URL = "jdbc:mysql://127.0.0.1:3306/member";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DRIVER = "com.mysql.jdbc.Driver";


    private final String JAVA_FILE_SUFFIX = ".java";
    private final String XML_FILE_SUFFIX = ".xml";

    // freemarker 模板文件路径
    private final String TEMPLATE_FILE_ENTITY = "/Entity.ftl";
    private final String TEMPLATE_FILE_DAO = "/Dao.ftl";
    private final String TEMPLATE_FILE_MAPPER = "/Mapper.ftl";
    private final String TEMPLATE_FILE_SERVICE = "/Service.ftl";
    private final String TEMPLATE_FILE_SERVICE_IMPL = "/ServiceImpl.ftl";
    private final String TEMPLATE_FILE_CONTROLLER = "/Controller.ftl";

    private FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate();


    // 生成的文件是否覆盖原有
    private final boolean isOverlayFile = false;
    private final boolean isOverlayEntityFile = false;
    private final boolean isOverlayMapperFile = false;

    public static void main(String[] args) throws Exception {
        CodeGenerateUtils codeGenerate = new CodeGenerateUtils();

        Map<String, Boolean> generateMap = new HashMap<>();
        // true false 控制生成哪些文件
        generateMap.put("entity",true);
        generateMap.put("mapper",true);
        generateMap.put("dao",true);
        generateMap.put("service",true);
        generateMap.put("serviceImpl",true);
        generateMap.put("controller",true);

        // 表名 + 实体类名 + 类注释 （实体类名为null的话取驼峰表名）
        codeGenerate.generate("overview_statistics_record", null, "概览统计记录", generateMap);

    }

    /**
     * 生成文件
     *
     * @param tableName       表名
     * @param customEntityName      实体类名
     * @param classAnnotation 类名注释
     * @throws Exception
     */
    public void generate(String tableName, String customEntityName, String classAnnotation, Map<String, Boolean> generateMap) throws Exception {
        if (customEntityName == null) {
            customEntityName = replaceUnderLineAndUpperCase(tableName) + "DO";
        }
        try {
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            // 每次需要重新获取
            ResultSet resultSet = databaseMetaData.getColumns(null, "%", tableName, "%");
            ResultSet resultSet1 = databaseMetaData.getColumns(null, "%", tableName, "%");
            ResultSet resultSet2 = databaseMetaData.getColumns(null, "%", tableName, "%");
            ResultSet resultSet3 = databaseMetaData.getColumns(null, "%", tableName, "%");
            ResultSet resultSet4 = databaseMetaData.getColumns(null, "%", tableName, "%");
            ResultSet resultSet5 = databaseMetaData.getColumns(null, "%", tableName, "%");
            // 生成实体类文件
            if (generateMap.get("entity")) {
                generateEntityFile(resultSet, tableName, customEntityName, classAnnotation);
            }
            // 生成mapper文件
            if (generateMap.get("mapper")) {
                generateMapperFile(resultSet1, tableName, customEntityName, classAnnotation);
            }
            // 生成dao文件
            if (generateMap.get("dao")) {
                generateDaoFile(resultSet2, tableName, customEntityName, classAnnotation);
            }
            // 生成service文件
            if (generateMap.get("service")) {
                generateServiceFile(resultSet3, tableName, customEntityName, classAnnotation);
            }
            // 生成service impl 文件
            if (generateMap.get("serviceImpl")) {
                generateServiceImplFile(resultSet4, tableName, customEntityName, classAnnotation);
            }

            // 生成controller 文件
            if (generateMap.get("controller")) {
                generateControllerFile(resultSet5, tableName, customEntityName, classAnnotation);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    /**
     * 生成实体类文件
     *
     * @param resultSet
     * @param tableName
     * @param customEntityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateEntityFile(ResultSet resultSet, String tableName, String customEntityName, String classAnnotation) throws Exception {
        // 实体类名
        String entityName = getEntityName(tableName, customEntityName);
        // 输出文件路径
        final String filePath = PROJECT_PATH + ENTITY_MODULE_PATH + JAVA_PATH + PACKAGE_PATH_ENTITY ;
        // 输出文件名
        final String fileName = entityName;
        // 输出文件完整路径
        File outputFile = getOutputFile(filePath, fileName, JAVA_FILE_SUFFIX, isOverlayEntityFile);
        // 表字段数据
        List<ColumnProperty> columnClassList = getColumnClassList(resultSet, ignoreColumefield);
        generateFileByTemplate(TEMPLATE_FILE_ENTITY, ENTITY_PACKAGE, tableName, entityName, classAnnotation, outputFile, columnClassList);
    }

    /**
     * 生成dao文件
     *
     * @param resultSet
     * @param tableName
     * @param customEntityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateDaoFile(ResultSet resultSet, String tableName, String customEntityName, String classAnnotation) throws Exception {
        // 实体类名
        String entityName = getEntityName(tableName, customEntityName);
        // 表名 转化为驼峰
        String tableNameHump = replaceUnderLineAndUpperCase(tableName);
        // 输出文件路径
        final String filePath = PROJECT_PATH + DAO_MODULE_PATH + JAVA_PATH + PACKAGE_PATH_DAO ;
        // 输出文件名
        final String fileName = tableNameHump + "Mapper";
        File outputFile = getOutputFile(filePath, fileName, JAVA_FILE_SUFFIX, isOverlayFile);
        // 表字段数据
        List<ColumnProperty> columnClassList = getColumnClassList(resultSet, allColumefield);
        // 包名
        generateFileByTemplate(TEMPLATE_FILE_DAO, MAPPER_PACKAGE, tableName, entityName, classAnnotation, outputFile, columnClassList);
    }

    /**
     * 生成mapper文件
     *
     * @param resultSet
     * @param tableName
     * @param customEntityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateMapperFile(ResultSet resultSet, String tableName, String customEntityName, String classAnnotation) throws Exception {
        // 实体类名
        String entityName = getEntityName(tableName, customEntityName);
        // 表名 转化为驼峰
        String tableNameHump = replaceUnderLineAndUpperCase(tableName);
        // 输出文件路径
        final String filePath = PROJECT_PATH + DAO_MODULE_PATH + JAVA_PATH + PACKAGE_PATH_DAO ;
        // 输出文件名
        final String fileName = tableNameHump + "Mapper";
        File outputFile = getOutputFile(filePath, fileName, XML_FILE_SUFFIX, isOverlayMapperFile);
        // 表字段数据
        List<ColumnProperty> columnClassList = getColumnClassList(resultSet, allColumefield);
        // 包名
        generateFileByTemplate(TEMPLATE_FILE_MAPPER, ENTITY_PACKAGE, tableName, entityName, classAnnotation, outputFile, columnClassList);
    }

    /**
     * 生成service文件
     *
     * @param resultSet
     * @param tableName
     * @param customEntityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateServiceFile(ResultSet resultSet, String tableName, String customEntityName, String classAnnotation) throws Exception {
        // 实体类名
        String entityName = getEntityName(tableName, customEntityName);
        // 表名 转化为驼峰
        String tableNameHump = replaceUnderLineAndUpperCase(tableName);
        // 输出文件路径
        final String filePath = PROJECT_PATH + SERVICE_MODULE_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE;
        // 输出文件名
        final String fileName = tableNameHump + "Service";
        File outputFile = getOutputFile(filePath, fileName, JAVA_FILE_SUFFIX, isOverlayFile);

        // 表字段数据
        List<ColumnProperty> columnClassList = getColumnClassList(resultSet, allColumefield);
        // 包名
        generateFileByTemplate(TEMPLATE_FILE_SERVICE, SERVICE_PACKAGE, tableName, entityName, classAnnotation, outputFile, columnClassList);
    }

    /**
     * 生成serviceImpl文件
     *
     * @param resultSet
     * @param tableName
     * @param customEntityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateServiceImplFile(ResultSet resultSet, String tableName, String customEntityName, String classAnnotation) throws Exception {
        // 实体类名
        String entityName = getEntityName(tableName, customEntityName);
        // 表名 转化为驼峰
        String tableNameHump = replaceUnderLineAndUpperCase(tableName);
        // 输出文件路径
        final String filePath = PROJECT_PATH + SERVICE_MODULE_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL;
        // 输出文件名
        final String fileName = tableNameHump + "ServiceImpl";
        // 输出文件完整路径
        File outputFile = getOutputFile(filePath, fileName, JAVA_FILE_SUFFIX, isOverlayFile);

        // 表字段数据
        List<ColumnProperty> columnClassList = getColumnClassList(resultSet, allColumefield);
        // 包名
        generateFileByTemplate(TEMPLATE_FILE_SERVICE_IMPL, SERVICE_IMPL_PACKAGE, tableName, entityName, classAnnotation, outputFile, columnClassList);
    }

    /**
     * 生成Controller文件
     *
     * @param resultSet
     * @param tableName
     * @param customEntityName
     * @param classAnnotation
     * @throws Exception
     */
    private void generateControllerFile(ResultSet resultSet, String tableName, String customEntityName, String classAnnotation) throws Exception {
        // 实体类名
        String entityName = getEntityName(tableName, customEntityName);
        // 表名 转化为驼峰
        String tableNameHump = replaceUnderLineAndUpperCase(tableName);
        // 输出文件路径
        final String filePath = PROJECT_PATH + CONTROLLER_MODULE_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER;
        // 输出文件名
        final String fileName = tableNameHump + "Controller";
        // 输出文件完整路径
        File outputFile = getOutputFile(filePath, fileName, JAVA_FILE_SUFFIX, isOverlayFile);
        // 表字段数据
        List<ColumnProperty> columnClassList = getColumnClassList(resultSet, allColumefield);
        // 包名
        generateFileByTemplate(TEMPLATE_FILE_CONTROLLER, CONTROLLER_PACKAGE, tableName, entityName, classAnnotation, outputFile, columnClassList);
    }

    /**
     * 根据模板生成文件
     *
     * @param templateName    模板文件名
     * @param packageName     包名
     * @param tableName       table名
     * @param entityName      实体类名
     * @param classAnnotation 类注释
     * @param file            file
     * @param columnClassList 表格字段数据
     * @throws Exception
     */
    private void generateFileByTemplate(String templateName, String packageName, String tableName, String entityName, String classAnnotation, File file, List<ColumnProperty> columnClassList) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        Template template = freeMarkerTemplate.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        /**
         * columnName       - 字段名
         * columnType       - 字段类型
         * changeColumnName - 实体类名
         * javaType         - 实体类类型
         * columnComment    - 字段注释
         */
        dataMap.put("entity_column", columnClassList);
        // 表名
        dataMap.put("table_name", tableName);
        // 表名 转化为驼峰
        dataMap.put("table_name_hump", replaceUnderLineAndUpperCase(tableName));
        // 实体类名
        dataMap.put("entity_name", entityName);
        // 作者
        dataMap.put("author", AUTHOR);
        // 时间
        dataMap.put("date", CURRENT_DATE);
        // mapper路径
        dataMap.put("mapper_packege", MAPPER_PACKAGE);
        // entity包路径
        dataMap.put("entity_packege", ENTITY_PACKAGE);
        // service包路徑
        dataMap.put("service_packege", SERVICE_PACKAGE);
        // serviceImpl包路徑
        dataMap.put("serviceImpl_packege", SERVICE_IMPL_PACKAGE);
        // controller包路径
        dataMap.put("controller_package", CONTROLLER_PACKAGE);

        // 包路径
        dataMap.put("package_name", packageName);
        // 类注释
        dataMap.put("table_annotation", classAnnotation);

        // 特殊字符
        dataMap.put("char_1", "#");
        // 主键id JAVA类型
        dataMap.put("id_java_type", getIdJavaType(columnClassList));
        // 主键id JDBC类型
        dataMap.put("id_jdbc_type", getIdJdbcType(columnClassList));
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
    }

    /**
     * 将类名转化为实体名
     *
     * @param str
     * @return
     */
    public String replaceUnderLineAndUpperCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return StringUtils.capitalize(result);
    }

    /**
     * 将包路径转换成文件路径
     * eg:
     * com.admin.generator.web => /com/admin/generator/web/
     *
     * @param packageName
     * @return
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
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
     *
     * @param columnClassList
     * @return
     */
    public static String getIdJavaType(List<ColumnProperty> columnClassList) {
        for (ColumnProperty columnProperty : columnClassList) {
            if (columnProperty.getColumnName().equals("id")) {
                return columnProperty.getJavaType();
            }
        }
        return "";
    }

    /**
     * 获取表主键id jdbc类型
     *
     * @param columnClassList
     * @return
     */
    public static String getIdJdbcType(List<ColumnProperty> columnClassList) {
        for (ColumnProperty columnProperty : columnClassList) {
            if (columnProperty.getColumnName().equals("id")) {
                return columnProperty.getColumnType();
            }
        }
        return "";
    }

    /**
     * 根据jdbc类型获取对应的Java类型
     *
     * @param jdbcType
     * @return
     */
    public static String getJavaTypeByJdbcType(String jdbcType) {
        if (jdbcType.equals("CHAR") || jdbcType.equals("VARCHAR") ||
                jdbcType.equals("LONGVARCHAR") || jdbcType.equals("TEXT") ||
                jdbcType.equals("CLOB") || jdbcType.equals("BLOB")) {
            return "String";
        }
        if (jdbcType.equals("DATE") || jdbcType.equals("TIME") ||
                jdbcType.equals("TIMESTAMP") || jdbcType.equals("DATETIME") ||
                jdbcType.equals("YARN")) {
            return "Date";
        }
        if (jdbcType.equals("BIT") || jdbcType.equals("BOOLEAN")) {
            return "Boolean";
        }
        if (jdbcType.equals("TINYINT")) {
            return "Byte";
        }
        if (jdbcType.equals("SMALLINT")) {
            return "Short";
        }
        if (jdbcType.equals("INTEGER") || jdbcType.equals("INT")) {
            return "Integer";
        }
        if (jdbcType.equals("BIGINT")) {
            return "Long";
        }
        if (jdbcType.equals("REAL")) {
            return "Float";
        }
        if (jdbcType.equals("DOUBLE") || jdbcType.equals("FLOAT")) {
            return "Double";
        }
        if (jdbcType.equals("BINARY") || jdbcType.equals("VARBINARY") || jdbcType.equals("LONGVARBINARY")) {
            return "byte[]";
        }
        if (jdbcType.equals("DECIMAL")) {
            return "BigDecimal";
        }
        return "";
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }


    /**
     * 获取实体类名 可指定，不指定的话就取表名（下划线转驼峰命名）
     * @param tableName
     * @return
     */
    private String getEntityName (String tableName, String customEntityName) {
        if (customEntityName == null) {
            return replaceUnderLineAndUpperCase(tableName);
        }
        return customEntityName;
    }

    /**
     * 获取File文件 根据 {@link CodeGenerateUtils#isOverlayFile} 决定是否覆盖原有文件
     * @param filePath
     * @param fileName
     * @param fileSuffix
     * @return
     */
    private File getOutputFile (String filePath, String fileName, String  fileSuffix, boolean isOverlayFile) {
        final String fileFullPath = filePath + fileName + fileSuffix;
        logger.info("生成文件路径: " + fileFullPath);
        File targetFile = new File(filePath + fileName + fileSuffix);
        if (!targetFile.getParentFile().exists()) {
            // 创建文件夹
            targetFile.getParentFile().mkdirs();
        } else {
            // 如果文件夹存在 判断文件是否存在
            if (targetFile.exists()) {
                // 是否覆盖文件
                if (!isOverlayFile) {
                    fileName = fileName + System.currentTimeMillis();
                    logger.info("已有同名文件，不覆盖原有文件, 生成新文件名: " + fileName + ".java");
                    targetFile = new File(filePath + fileName + fileSuffix);
                } else {
                    logger.info("已有同名文件，覆盖原有文件");
                }
            }
        }
        return targetFile;
    }

    /**
     * 获取表格字段数据
     */
    private List<ColumnProperty> getColumnClassList (ResultSet resultSet, String[] ignoreColumeField) throws Exception{
        List<ColumnProperty> columnClassList = new ArrayList<>();
        ColumnProperty columnClass = null;
        while (resultSet.next()) {
            // 共有字段忽略
            if (Arrays.asList(ignoreColumeField).contains(resultSet.getString("COLUMN_NAME"))) {
                continue;
            }
            columnClass = new ColumnProperty();
            // 获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));

            // TODO 获取字段类型 需完善
            String typeName = resultSet.getString("TYPE_NAME");
            if ("DATETIME".equals(typeName)) {
                columnClass.setColumnType("TIMESTAMP");
            } else if ("INT".equals(typeName)){
                columnClass.setColumnType("INTEGER");
            } else {
                columnClass.setColumnType(typeName);
            }

            // 获取Java类型
            columnClass.setJavaType(getJavaTypeByJdbcType(resultSet.getString("TYPE_NAME")));
            // 数据库字段首字母小写且去掉下划线字符串
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            // 字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            logger.info("{}列信息: {}", columnClass.getColumnName() ,columnClass);
            columnClassList.add(columnClass);
        }
        return columnClassList;
    }

    /**
     * freeMarker配置类
     */
    class FreeMarkerTemplate {

        private final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

        public FreeMarkerTemplate() {
            //这里比较重要，用来指定加载模板所在的路径
            CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplate.class, TEMPLATE_PATH));
            CONFIGURATION.setDefaultEncoding("UTF-8");
            CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
        }

        public Template getTemplate(String templateName) throws IOException {
            try {
                return CONFIGURATION.getTemplate(templateName);
            } catch (IOException e) {
                throw e;
            }
        }

        public void clearCache() {
            CONFIGURATION.clearTemplateCache();
        }
    }
}
