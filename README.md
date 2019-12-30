# code-generate
## Java 代码自动生成工具 

### 前言 
由于之前代码生成使用的是Mybatis-generator插件，但该插件生成的文件不能满足实际项目的需要，具有如下缺点
* 生成的文件都是固定格式，不能自定义定制，比如某个类需要继承某个抽象类，实现某个接口，用这个Mybatis-generator插件不能解决这些问题
* 实体类字段没有生成将表字段注释，需要自己手动添加
* mapper.xml文件 
```sql
没有 title !='' 字符串判空判断, 只有判断是否是null
<if test="title != null" >
    
</if>
```
* 生成的文件只有`entity.java、 mapper.java、 mapper.xml` 这些文件, 但实际项目中还需要 `service.java serviceImpl.java controller.java` 这些文件，甚至应该有`jsp vue`这些文件

### 项目描述

> 针对以上问题编写这个代码自动生成工具

#### 初步想法
1. 利用原生Jdbc获取数据库表元数据
2. 获取到表元数据之后利用freemaker模板框架自动生成Dao Entity Service Controller mapper等文件
3. 后期有时间可以支持jsp模板


#### v2版本

* `com.songsy.v2`包下的代码实现适合多模块下`Maven`项目，可直接将包下的这两个类直接copy嵌入到项目中

* 可以自己修改 `resources\templates` 目录下的模板文件

* `maven`依赖

```sql
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.37</version>
        <scope>runtime</scope>
    </dependency>

    <!--freemarker-->
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.23</version>
    </dependency>

</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <compilerArguments>
                    <bootclasspath>${JAVA_HOME}/jre/lib/rt.jar</bootclasspath>
                </compilerArguments>
            </configuration>
        </plugin>
    </plugins>
</build>
```


