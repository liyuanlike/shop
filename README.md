# SpringBoot+MybatisPlus项目骨架

　　Mall项目的单个微服务详细案例，详见代码：https://github.com/D2C-Cai/mall  
　　SpringBoot（2.0.5）+MybatisPlus（3.0.7）项目骨架，支持SpringSecurity+JWT权限验证，整合Redis+Elasticseach，EasyPoi的Excel导出，Elasticseach请求日志收集，Swagger2接口文档，工具包Lombok/Hutool/FastJson等<br>
　　作者QQ：[709931138]()

## 背景介绍
　　**骨架项目的精髓：框架流行，版本要新，配置清晰，代码简洁，案例完整。依赖最小化，不拖泥带水，不自以为是。**<br>

　　在日常的开发过程中，相信大部分同志的痛点并不在业务开发上，而是在寻求一些比较舒适的开发框架，帮助自己提升开发效率。直接把开源框架拿来用，感觉还是有点卡手，直接用代码生成器自动生成代码，出来的代码既繁琐又比较笨重。
这里作者给出自己的一套认为比较简洁好用的方案，在下面的文档中，作者会比较详细的例举一些代码片段，并介绍给各位比较流行的开源框架，帮助大家理解流行框架的整合，帮助大家提高开发效率。

## 环境介绍
　　此项目适用于有一定开发基础的开发者使用，项目内使用的框架和中间件都是市面上非常流行的，如何搭建环境的教程不作详细介绍，请开发者自行搭建必要的环境。
这里只给出几点建议：Linux服务器作者选用CentOS版本7，JDK选用1.8，MySql数据库5.6建议直接安装在系统上。一些中间件不论单机或集群请务必安装启动：Redis, Elasticsearch。<br>
　　最后还会给出Docker容器中快捷安装的方案，注意容器时区，以及目录的映射，[命令只是建议，不要照抄]()！

## 版本说明
| 名称 | 版本 |
| ---- | ---- |
| SpringBoot | 2.0.5 |
| MybatisPlus | 3.0.7.1 |
| MySql | 5.6 |
| Redis | 3.2 |
| Elasticsearch | 5.6.8 |
| Hutool | 4.1.19 |
| Swagger | 2.7.0 |
| EasyPoi | 3.2.0 |
| JWT | 0.9.0 |
| Lombok | 1.16.20 |
| FastJson | 1.2.54 |
| Jasypt | 2.1.0 |

## MybatisPlus整合
　　MyBatis-Plus 荣获【2018年度开源中国最受欢迎的中国软件】TOP5，为简化开发而生。润物无声，效率至上，功能丰富。官网：https://mp.baomidou.com/

```$xslt
        <!-- MybatisPlus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.7.1</version>
        </dependency>
        <!-- Druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!-- Mysql Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.44</version>
        </dependency>
```
```$xslt
# spring
spring:
  datasource:
    url: jdbc:mysql://192.168.0.146:3306/shop?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: ENC(LQi0FkhtShKQ3PZFfBwj6w==)
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.jdbc.Driver
    
# mybatis-plus
mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
  typeAliasesPackage: com.d2c.shop.modules.*.model
  global-config:
    db-config:
      id-type: id_worker
      field-strategy: not_null
      logic-delete-value: 1
      logic-not-delete-value: 0
    sql-parser-cache: false
  configuration:
    auto-mapping-behavior: partial
    map-underscore-to-camel-case: true
    cache-enabled: false    
```
**解释：**
上边给的配置中庸科学。具体更详细的配置建议大家参考官网文档：https://mp.baomidou.com/guide/

```$xslt
@Configuration
@EnableTransactionManagement
@MapperScan("com.d2c.shop.modules.*.mapper")
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

}
```
**解释：**
分页插件 PaginationInterceptor，逻辑插件 ISqlInjector

```$xslt
public interface FieldConstant {

    /**
     * 唯一主键ID
     */
    String ID = "id";
    /**
     * 创建时间
     */
    String CREATE_DATE = "createDate";
    /**
     * 创建用户
     */
    String CREATE_MAN = "createMan";
    /**
     * 修改时间
     */
    String MODIFY_DATE = "modifyDate";
    /**
     * 修改用户
     */
    String MODIFY_MAN = "modifyMan";
    /**
     * 逻辑删除标志
     */
    String DELETED = "deleted";

}
```
```$xslt
@Data
public abstract class BaseDO extends Model {

    @TableId(value = "id", type = IdType.ID_WORKER)
    @ApiModelProperty(value = "唯一主键ID")
    private Long id;
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @TableField(value = "create_man", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建用户")
    private String createMan;
    @TableField(value = "modify_date", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifyDate;
    @TableField(value = "modify_man", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改用户")
    private String modifyMan;

}
```
```$xslt
@Data
public abstract class BaseDelDO extends BaseDO {

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "逻辑删除标志")
    private Integer deleted;

}
```
```$xslt
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createDate = this.getFieldValByName(FieldConstant.CREATE_DATE, metaObject);
        if (null == createDate) {
            this.setFieldValByName(FieldConstant.CREATE_DATE, new Date(), metaObject);
        }
        Object createMan = this.getFieldValByName(FieldConstant.CREATE_MAN, metaObject);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == createMan && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            this.setFieldValByName(FieldConstant.CREATE_MAN, user.getUsername(), metaObject);
        }
        Object deleted = this.getFieldValByName(FieldConstant.DELETED, metaObject);
        if (null == deleted) {
            this.setFieldValByName(FieldConstant.DELETED, new Integer(0), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifyDate = this.getFieldValByName(FieldConstant.MODIFY_DATE, metaObject);
        this.setFieldValByName(FieldConstant.MODIFY_DATE, new Date(), metaObject);
        Object modifyMan = this.getFieldValByName(FieldConstant.MODIFY_MAN, metaObject);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == modifyMan && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            this.setFieldValByName(FieldConstant.MODIFY_MAN, user.getUsername(), metaObject);
        }
    }

}
```
**解释：**
代码虽然繁琐，但逻辑很简单，BaseDO继承于com.baomidou.mybatisplus.extension.activerecord.Model，@TableXXX标签是主力，具体含义望文生义即可。表的默认字段经过配置，只要调用IService，均为自动填表，id默认分布式数形式，创建时间和修改时间均为当前时间，创建人和修改人由SpringSecurity（下面会讲）获取用户名赋值

```$xslt
@Data
@TableName("sys_user")
@ApiModel(description = "用户表")
public class UserDO extends BaseDelDO {

    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @TableField(exist = false)
    @ApiModelProperty(value = "用户拥有的角色")
    private List<RoleDO> roles = new ArrayList<>();

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
```
```$xslt
public interface UserMapper extends BaseMapper<UserDO> {

}
```
```$xslt
public interface UserService extends IService<UserDO> {

}
```
```$xslt
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
```
**解释：**
自己的业务代码找准这些类继承即可<br>
com.baomidou.mybatisplus.core.mapper.BaseMapper,<br> com.baomidou.mybatisplus.extension.service.IService, <br>com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<br>

```$xslt
public abstract class BaseCtrl<E extends BaseDO, Q extends BaseQuery> {

    @Autowired
    public IService<E> service;

    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R insert(@RequestBody E entity) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, entity);
        boolean success = service.saveOrUpdate(entity);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(entity, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID获取数据")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public R select(@PathVariable Long id) {
        E entity = service.getById(id);
        if (entity == null) {
            return Response.failed(ErrorCode.RESPONSE_DATA_NULL);
        }
        return Response.restResult(entity, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID更新数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody E entity) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, entity);
        boolean success = service.updateById(entity);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(null, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "通过ID删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Long[] ids) {
        boolean success = service.removeByIds(CollUtil.toList(ids));
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(null, ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "分页查询数据")
    @RequestMapping(value = "/select/page", method = RequestMethod.POST)
    public R selectPage(PageModel page, Q query) {
        Page<E> pager = (Page<E>) service.page(page, QueryUtil.buildWrapper(query));
        return Response.restResult(pager, ErrorCode.SUCCESS);
    }

}
```
```$xslt
public enum ErrorCode implements IErrorCode {
    //
    SUCCESS(1, "操作成功"),
    FAILED(-1, "操作失败"),
    LOGIN_EXPIRED(-401, "登录已经过期"),
    ACCESS_DENIED(-403, "没有访问权限"),
    SERVER_EXCEPTION(-500, "服务端异常"),
    REQUEST_PARAM_NULL(-501, "请求参数为空"),
    RESPONSE_DATA_NULL(-502, "返回数据为空");
    //
    private long code;
    private String msg;

    private ErrorCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
```
```$xslt
@Slf4j
public class Response extends R {

    public static R failed(IErrorCode errorCode, String msg) {
        R result = failed(errorCode);
        result.setMsg(msg);
        return result;
    }

    public static void out(ServletResponse response, R result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONUtil.parse(result).toJSONString(0));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
```
**解释：**
最基本的增删改查实现，作者选用了包里提供的案例类继承，如果不满意可以自己写response交互格式<br>
com.baomidou.mybatisplus.extension.api.IErrorCode,<br>
com.baomidou.mybatisplus.extension.api.R<br>
**注意：
网上的例子大多到此为止，问题只解决了一半，裤子都脱了就给我看这个？<br>
真正最能省时间的分页实现在下边给出。**

```$xslt
@Target(FIELD)
@Retention(RUNTIME)
public @interface Condition {

    ConditionEnum condition() default ConditionEnum.EQ;

    String field() default "";

    String sql() default "";

}
```
```$xslt
public enum ConditionEnum {
    //
    EQ("等于="),
    NE("不等于<>"),
    GT("大于>"),
    GE("大于等于>="),
    LT("小于<"),
    LE("小于等于<="),
    LIKE("模糊查询 LIKE"),
    NOT_LIKE("模糊查询 NOT LIKE"),
    IN("IN 查询"),
    NOT_IN("NOT IN 查询"),
    IS_NULL("NULL 值查询"),
    IS_NOT_NULL("IS NOT NULL"),
    EXIST("EXISTS 条件语句"),
    NOT_EXIST("NOT EXISTS 条件语句");
    //
    private String description;

    ConditionEnum(String description) {
        this.description = description;
    }
}

```
```$xslt
public class QueryUtil {

    // 构建QueryWrapper
    public static <T extends BaseQuery> QueryWrapper buildWrapper(T query) {
        QueryWrapper<Object> queryWrapper = new QueryWrapper();
        for (Field field : getAllFields(query.getClass())) {
            // 查询条件标签
            Condition annotation = field.getAnnotation(Condition.class);
            // 数据库查询字段
            String key = annotation.field();
            if (StrUtil.isBlank(key)) {
                key = camelToUnderline(field.getName());
            }
            // 数据库查询的值
            Object value = null;
            try {
                value = field.get(query);
            } catch (IllegalAccessException e) {
                break;
            }
            Object[] array = new Object[]{};
            if (value != null && value.getClass().isArray()) {
                array = (Object[]) value;
            }
            // 数据库语句片段
            String sql = annotation.sql();
            // 当搜索条件有值时
            switch (annotation.condition()) {
                case EQ:
                    if (value != null) {
                        queryWrapper.eq(key, value);
                    }
                    break;
                case NE:
                    if (value != null) {
                        queryWrapper.ne(key, value);
                    }
                    break;
                case GE:
                    if (value != null) {
                        queryWrapper.ge(key, value);
                    }
                    break;
                case GT:
                    if (value != null) {
                        queryWrapper.gt(key, value);
                    }
                    break;
                case LE:
                    if (value != null) {
                        queryWrapper.le(key, value);
                    }
                    break;
                case LT:
                    if (value != null) {
                        queryWrapper.lt(key, value);
                    }
                    break;
                case LIKE:
                    if (value != null) {
                        queryWrapper.like(key, value);
                    }
                    break;
                case NOT_LIKE:
                    if (value != null) {
                        queryWrapper.notLike(key, value);
                    }
                    break;
                case IN:
                    if (array != null && array.length > 0) {
                        queryWrapper.in(key, array);
                    }
                    break;
                case NOT_IN:
                    if (array != null && array.length > 0) {
                        queryWrapper.notIn(key, array);
                    }
                    break;
                case IS_NULL:
                    if (value != null && (int) value == 1) {
                        queryWrapper.isNull(key);
                    }
                    break;
                case IS_NOT_NULL:
                    if (value != null && (int) value == 1) {
                        queryWrapper.isNotNull(key);
                    }
                    break;
                case EXIST:
                    if (StrUtil.isNotBlank(sql)) {
                        queryWrapper.exists(sql);
                    }
                    break;
                case NOT_EXIST:
                    if (StrUtil.isNotBlank(sql)) {
                        queryWrapper.notExists(sql);
                    }
                    break;
                default:
                    break;
            }
        }
        return queryWrapper;
    }

    // 获取基类和父类所有的字段
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    // 字段名称驼峰转下划线
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
```
```$xslt
@Data
public class PageModel extends Page {

    @ApiModelProperty(value = "页码")
    private long p;
    @ApiModelProperty(value = "页长")
    private long ps;
    // 最大页长
    public static final long MAX_SIZE = 1000L;

    public PageModel() {
        super();
    }

    public void setP(long p) {
        this.p = p;
        this.setCurrent(p);
    }

    public void setPs(long ps) {
        this.ps = ps;
        this.setSize(ps);
    }

}
```
```$xslt
@Data
public abstract class BaseQuery implements Serializable {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "唯一主键ID")
    public Long id;
    @Condition(condition = ConditionEnum.GE, field = "create_date")
    @ApiModelProperty(value = "创建时间起")
    public Date createDateL;
    @Condition(condition = ConditionEnum.LE, field = "create_date")
    @ApiModelProperty(value = "创建时间止")
    public Date createDateR;
    @Condition(condition = ConditionEnum.GE, field = "modify_date")
    @ApiModelProperty(value = "修改时间起")
    public Date modifyDateL;
    @Condition(condition = ConditionEnum.LE, field = "modify_date")
    @ApiModelProperty(value = "修改时间止")
    public Date modifyDateR;

}
```
```$xslt
@Data
public class UserQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.LIKE)
    @ApiModelProperty(value = "账号")
    public String username;
    @Condition(condition = ConditionEnum.IN)
    @ApiModelProperty(value = "状态")
    public Integer[] status;

}
```
**解释：**
这里的Page类继承于com.baomidou.mybatisplus.extension.plugins.pagination.Page, 如果不满意可以自行实现。
@Condition标签作用于查询类字段上，condition表示操作符，field表示数据库中的字段名，sql则为注入的查询语句。根据com.baomidou.mybatisplus.core.conditions.AbstractWrapper的代码特性，
QueryUtil类专门构造一个QueryWrapper作为多重查询条件使用。<br>
　　上边的代码只是一个例子，大家可以随意自己继承实现，到此为止MyBatis-Plus整合大致完成，怎么样，很酷吧!

## SpringSecurity+JWT整合
代码都是完整的，文档待续......
