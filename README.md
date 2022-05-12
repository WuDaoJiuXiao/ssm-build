**该项目为 MyBatis、Spring、SpringMVC 三者整合的模板项目,下方为具体整合流程**

---

#### 1 项目基础环境搭建

1.创建测试用的数据库

```sql
create database `ssm`;

drop table if exists `books`;

create table `books`(
	`bookId` int(10) not null primary key comment '书籍编号',
    `bookName` varchar(100) not null comment '书名',
    `bookCount` int(12) not null comment '数量',
    `detail` varchar(200) not null comment '描述'
)engine=innodb default charset=utf8;
```

2. 建立一个普通的 Maven 项目，然后倒入项目所需的相关依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.jiuxiao</groupId>
    <artifactId>ssm-build</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
        <!--数据库连接池-->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.5</version>
        </dependency>
        <!--Servlet依赖-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <!--JSP依赖-->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.1</version>
        </dependency>
        <!--jstl依赖-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <!--Junit依赖-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
        <!--lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
        </dependency>
        <!--MyBatis依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <!--MyBatis-Spring依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.5</version>
        </dependency>
        <!--Spring依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.19</version>
        </dependency>
        <!--Spring-JDBC依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.3.17</version>
        </dependency>
    </dependencies>

    <!--在 build 中配置 resources，来防止资源导出失败问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>

    <!--解决配置文件不能有中文注释问题-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>
</project>
```

3. 完善项目结构：建立 pojo、mapper、service、controller 层等

![image-20220512104921801](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220512104921801.png)

4. 创建 pojo 层的实体类

```java
package com.jiuxiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 书籍实体类
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:34
 * @since: 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    private int bookId;
    private String bookName;
    private int bookCount;
    private String detail;
}
```

#### 2 整合 MyBatis 

1. 首先是 Mapper 层的常用增删改查接口

```java
package com.jiuxiao.mapper;

import com.jiuxiao.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书籍操作相关的接口
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:37
 * @since: 1.0.0
 */
public interface BookMapper {

    /**
     * 增加一本书
     *
     * @param books
     * @return
     */
    int addBook(Books books);

    /**
     * 根据 ID 删除一本书
     *
     * @param bookId
     * @return
     */
    int deleteBookById(@Param("bookId") int bookId);

    /**
     * 更新一本书
     *
     * @param books
     * @return
     */
    int updateBook(Books books);

    /**
     * 根据 ID 查询一本书
     *
     * @param bookId
     * @return
     */
    Books selectBookById(@Param("bookId") int bookId);

    /**
     * 查寻全部书籍
     *
     * @return
     */
    List<Books> selectAllBook();
}
```

2. 然后根据接口去 \*Mapper.xml 文件中配置增删改查

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.jiuxiao.mapper.BookMapper">
    <insert id="addBook" parameterType="Books">
        insert into ssm.books (bookId, bookName, bookCount, detail)
            value (#{bookId}, #{bookName}, #{bookCount}, #{detail})
    </insert>

    <delete id="deleteBookById" parameterType="_int">
        delete
        from ssm.books
        where bookId = #{bookId}
    </delete>

    <update id="updateBook" parameterType="Books">
        update ssm.books
        set bookName  = #{bookName},
            bookCount = #{bookCount},
            detail    = #{detail}
        where bookId = #{bookId}
    </update>

    <select id="selectBookById" parameterType="_int" resultType="Books">
        select *
        from ssm.books
        where bookId = #{bookId}
    </select>

    <select id="selectAllBook" resultType="Books">
        select *
        from ssm.books
    </select>
</mapper>
```

3. 数据库相关的配置文件 database.properties

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssm?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
jdbc.username=xxxx
jdbc.password=xxxx
```

4. 接下来就是 MyBtais 整合到 Spring 中，新建 mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <typeAliases>
        <package name="com.jiuxiao.pojo"/>
    </typeAliases>

    <mappers>
        <mapper class="com.jiuxiao.mapper.BookMapper"/>
    </mappers>
</configuration> 
```

5. 然后是 Service 层对应的接口

```java
package com.jiuxiao.service;

import com.jiuxiao.pojo.Books;

import java.util.List;

/**
 * 书籍相关操作业务层接口
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:58
 * @since: 1.0.0
 */
public interface BookService {

    /**
     * 增加一本书
     *
     * @param books
     * @return
     */
    int addBook(Books books);

    /**
     * 根据 ID 删除一本书
     *
     * @param bookId
     * @return
     */
    int deleteBookById(int bookId);

    /**
     * 更新一本书
     *
     * @param books
     * @return
     */
    int updateBook(Books books);

    /**
     * 根据 ID 查询一本书
     *
     * @param bookId
     * @return
     */
    Books selectBookById(int bookId);

    /**
     * 查寻全部书籍
     *
     * @return
     */
    List<Books> selectAllBook();
}
```

6. Service 层对应的接口实现类

```java
package com.jiuxiao.service.impl;

import com.jiuxiao.mapper.BookMapper;
import com.jiuxiao.pojo.Books;
import com.jiuxiao.service.BookService;
import lombok.Setter;

import java.util.List;

/**
 * 书籍相关操作业务层接口实现类
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:59
 * @since: 1.0.0
 */
public class BookServiceImpl implements BookService {

    @Setter
    private BookMapper bookMapper;

    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    public int deleteBookById(int bookId) {
        return bookMapper.deleteBookById(bookId);
    }

    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    public Books selectBookById(int bookId) {
        return bookMapper.selectBookById(bookId);
    }

    public List<Books> selectAllBook() {
        return bookMapper.selectAllBook();
    }
}
```

7. 至此，项目结构如下

![image-20220512105643541](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220512105643541.png)

#### 3 整合 Spring 

1. 首先是 Spring 整合 Mapper 层

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!--关联数据库配置文件-->
    <context:property-placeholder location="classpath:database.properties"/>

    <!--数据库连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!--c3p0连接池私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--关闭连接后不自动Commit-->
        <property name="autoCommitOnClose" value="false"/>
        <!--连接超时时间-->
        <property name="checkoutTimeout" value="10000"/>
        <!--连接失败后重试次数-->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--SqlSessionFactory配置-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--绑定MyBatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--配置Mapper接口扫描包，动态实现 Mapper 层接口注入到 Spring 容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入 SqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--要扫描的 Mapper 包路径-->
        <property name="basePackage" value="com.jiuxiao.mapper"/>
    </bean>
</beans>
```

2. 然后是 Spring 整合 Service 层

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd 
       http://www.springframework.org/schema/context 
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!--扫描 Service 层的包-->
    <context:component-scan base-package="com.jiuxiao.service"/>
    
    <!--将所有业务类注入到 bean-->
    <bean id="BookServiceImpl" class="com.jiuxiao.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>
    
    <!--声明式事务配置-->
    <bean id="TransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
</beans>
```

3. 最后将 Mapper 层、Service 层的配置文件都关联到总配置文件 applicationContext.xml 中

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="classpath:spring-mapper.xml"/>
    <import resource="classpath:spring-service.xml"/>
    <import resource="classpath:springMvc.xml"/>
</beans>
```

#### 4 整合 SpringMVC

1. 添加 web 支持，将项目变为一个 Web 项目

![image-20220512111022570](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220512111022570.png)

![image-20220512111050410](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220512111050410.png)

2. 成为一个 Web 项目之后，首先需要去配置的就是 web.xml，该文件中要配置的有：DispatcherServlet、Session 过期时间、编码过滤等

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--配置DispatcherServlet-->
    <servlet>
        <servlet-name>spring-mvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>spring-mvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    
    <!--配置SpringMVC编码过滤器-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    
    <!--配置默认Session过期时间-->
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
</web-app>
```

3. 接下来就要去 spring-mvc.xml 中配置 SpringMVC 对应的：注解驱动、静态资源过滤、视图解析器、包扫描等功能，记得在 WEB-INF 下创建 jsp 包

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--包扫描-->
    <context:component-scan base-package="com.jiuxiao.controller"/>
    <!--注解支持-->
    <mvc:annotation-driven/>
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

4. 至此，MyBatis、Spring、SpringMVC 三个框架的整合完毕，最终的项目结构大致如下

![image-20220512112610619](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220512112610619.png)







