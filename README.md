# SpringBoot+MybatisPlus项目骨架

　　Mall项目的单个微服务详细案例，详见代码：https://github.com/D2C-Cai/mall  
　　SpringBoot（2.0.5）+MybatisPlus（3.0.7）项目骨架，支持SpringSecurity+JWT权限验证，整合Redis+Elasticseach，EasyPoi的Excel导出，Elasticseach请求日志收集，Swagger2接口文档，工具包Lombok/Hutool/FastJson等<br>
　　作者QQ：[709931138]() 作者邮箱：[709931138@qq.com]()

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

```
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
```
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

```
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
分页插件 PaginationInterceptor，逻辑插件 ISqlInjector。

```
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
```
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
```
@Data
public abstract class BaseDelDO extends BaseDO {

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "逻辑删除标志")
    private Integer deleted;

}
```
```
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createDate = this.getFieldValByName(FieldConstant.CREATE_DATE, metaObject);
        if (null == createDate) {
            this.setFieldValByName(FieldConstant.CREATE_DATE, new Date(), metaObject);
        }
        Object createMan = this.getFieldValByName(FieldConstant.CREATE_MAN, metaObject);
        if (null == createMan) {
            this.setFieldValByName(FieldConstant.CREATE_MAN, LoginUserHolder.getUsername(), metaObject);
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
        if (null == modifyMan) {
            this.setFieldValByName(FieldConstant.MODIFY_MAN, LoginUserHolder.getUsername(), metaObject);
        }
    }

}
```
```
@Component
public class LoginUserHolder {

    public static UserDetails getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return null;
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return user;
    }

    public static String getUsername() {
        if (getLoginUser() == null) return null;
        return getLoginUser().getUsername();
    }

}
```
**解释：**
代码虽然繁琐，但逻辑很简单，BaseDO继承实现于com.baomidou.mybatisplus.extension.activerecord.Model，@TableXXX标签是主力，具体含义望文生义即可。表的默认字段经过配置，只要调用IService，均为自动填表，id默认分布式数形式，创建时间和修改时间均为当前时间，创建人和修改人由SpringSecurity（下面会讲）获取用户名赋值。

```
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
```
public interface UserMapper extends BaseMapper<UserDO> {

}
```
```
public interface UserService extends IService<UserDO> {

}
```
```
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
```
**解释：**
自己的业务代码找准这些类继承实现即可<br>
com.baomidou.mybatisplus.core.mapper.BaseMapper,<br> com.baomidou.mybatisplus.extension.service.IService, <br>com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<br>

```
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
```
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
```
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
最基本的增删改查实现，作者选用了包里提供的案例类继承实现于com.baomidou.mybatisplus.extension.api.IErrorCode，com.baomidou.mybatisplus.extension.api.R，如果不满意可以自己写response交互格式。<br>
**注意：**
**网上的例子大多到此为止，问题只解决了一半，裤子都脱了就给我看这个？<br>**
真正最能省时间的分页实现在下边给出。

```
@Target(FIELD)
@Retention(RUNTIME)
public @interface Condition {

    ConditionEnum condition() default ConditionEnum.EQ;

    String field() default "";

    String sql() default "";

}
```
```
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
```
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
```
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
```
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
```
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
这里的Page类继承实现于com.baomidou.mybatisplus.extension.plugins.pagination.Page, 如果不满意可以自行实现。
@Condition标签作用于查询类字段上，condition表示操作符，field表示数据库中的字段名，sql则为注入的查询语句。根据com.baomidou.mybatisplus.core.conditions.AbstractWrapper的代码特性，
QueryUtil类专门构造一个QueryWrapper作为多重查询条件使用。
**不是每个类暴露增删改查接口都是安全的，比如User这个随意增和改就不行，怎么办？**
请看下边的解决方案。<br>

```
@Api(description = "用户管理")
@RestController
@RequestMapping("/shop/user")
public class UserController extends BaseCtrl<UserDO, UserQuery> {

    @ApiOperation(value = "登录过期")
    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public R expired() {
        return Response.failed(ErrorCode.LOGIN_EXPIRED);
    }

    /**
     * 方法签名一致，可覆盖不安全的insert
     */
    @Override
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R insert(@RequestBody UserDO user) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean success = service.save(user);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(user, ErrorCode.SUCCESS);
    }

    /**
     * 方法签名一致，可覆盖不安全的update
     */
    @Override
    @ApiOperation(value = "用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody UserDO user) {
        Assert.notNull(ErrorCode.REQUEST_PARAM_NULL, user);
        user.setUsername(null);
        user.setPassword(null);
        boolean success = service.updateById(user);
        if (!success) {
            return Response.failed(ErrorCode.FAILED);
        }
        return Response.restResult(null, ErrorCode.SUCCESS);
    }

}
```
**解释：**
上边的代码只是一个例子，大家可以随意自己继承实现，到此为止MyBatis-Plus整合大致完成，怎么样，很酷吧!

## SpringSecurity+JWT整合
　　Spring Security是一个能够为基于Spring的企业应用系统提供声明式的安全访问控制解决方案的安全框架。JSON Web Token（JWT）是目前最流行的跨域身份验证解决方案。<br>
　　我们用SpringSecurity+JWT来解决管理端的权限问题，URL级和按钮级无非是对资源的定义不同而已，此方案能应对各种ajax前端框架。
```
        <!-- Spring MVC -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- Spring Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- JWT -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>                
```
```
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
```
@Data
@TableName("sys_role")
@ApiModel(description = "角色表")
public class RoleDO extends BaseDO {

    @ApiModelProperty(value = "ROLE_开头")
    private String code;
    @ApiModelProperty(value = "名称")
    private String name;

}
```
```
@Data
@TableName("sys_menu")
@ApiModel(description = "菜单表")
public class MenuDO extends BaseDO {

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "路径")
    private String path;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "父级ID")
    private Long parentId;
    @ApiModelProperty(value = "排序")
    private Integer sort;

    public enum TypeEnum {
        DIR, MENU, BUTTON
    }

}
```
```
@Data
@TableName("sys_user_role")
@ApiModel(description = "用户角色关系表")
public class UserRoleDO extends BaseDO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}
```
```
@Data
@TableName("sys_role_menu")
@ApiModel(description = "角色菜单关系表")
public class RoleMenuDO extends BaseDO {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
```
**解释：**
Spring Security包含认证（authentication）和授权（authorization）两大部分。权限逻辑最经典最简单的就是上边给出的例子，五表逻辑，User的Role的集合与Menu的Role的集合取交集，从而决定是否放行。

```
public class SecurityUserDetails extends UserDO implements UserDetails {

    public SecurityUserDetails(UserDO user) {
        if (user != null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<RoleDO> roles = this.getRoles();
        if (roles != null && roles.size() > 0) {
            roles.forEach(item -> {
                if (StrUtil.isNotBlank(item.getCode())) {
                    authorityList.add(new SimpleGrantedAuthority(item.getCode()));
                }
            });
        }
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus() == 1;
    }

}
```
```
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDO user = userService.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("该账号不存在，请重新确认");
        }
        return new SecurityUserDetails(user);
    }

}
```
```
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private RoleService roleService;

    @Override
    @Cacheable(value = "USER", key = "'findByUsername:'+#username", unless = "#result == null")
    public UserDO findByUsername(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        UserDO user = this.getOne(queryWrapper);
        if (user == null) return null;
        List<RoleDO> roles = roleService.findByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

}
```
**解释：**
这里继承实现于org.springframework.security.core.userdetails.UserDetails和org.springframework.security.core.userdetails.UserDetailsService,
通过组合类SecurityUserDetails把我们自己的User和SpringSecurity的UserDetails串起来，注意这里loadUserByUsername()方法返回的接口是包含了Role的集合的，并不是单单的UserDO，getAuthorities()方法需要Role的集合来填充返回值，
并且将这个数据存于Redis，为了提高鉴权性能，逻辑后续会解释。

```
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * 内存中存储权限表中所有操作请求权限
     */
    private static Map<String, Collection<ConfigAttribute>> map = null;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void loadDataSource() {
        map = new ConcurrentHashMap<>();
        List<MenuDO> menus = menuService.list();
        for (MenuDO menu : menus) {
            List<RoleDO> roles = roleService.findByMenuId(menu.getId());
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            if (roles != null && roles.size() > 0) {
                roles.forEach(item -> {
                    if (StrUtil.isNotBlank(item.getCode())) {
                        configAttributes.add(new SecurityConfig(item.getCode()));
                    }
                });
            }
            map.put(menu.getPath(), configAttributes);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (map == null) this.loadDataSource();
        String url = ((FilterInvocation) o).getRequestUrl();
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            if (pathMatcher.match(path, url)) {
                return map.get(path);
            }
        }
        // 未设置操作请求权限，返回空集合
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
```
```
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 未设置操作请求权限，返回空集合，则默认放行
        if (collection == null) return;
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute c = iterator.next();
            String needRole = c.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
```
```
@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private MySecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }

}
```
**解释：**
这里的代码可以说是授权部分最核心的逻辑了。<br>
　　MySecurityMetadataSource继承实现于org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource，
那我们定义了什么样的元数据结构呢？就是数据库表里所有Menu，并且每个Menu带着自己的Role的集合。这么一个大的数据量，这里为了提升性能，getAllConfigAttributes()并不实现，而是自己用一个ConcurrentHashMap，
启动时候从数据库取一次后在内存里存起来。getAttributes(Object o)就是返回这次请求的Menu的Role的集合，Menu虽然这么起名，可以表示目录、链接、按钮，就看你如何定义。<br>
　　MyAccessDecisionManager继承实现于com.d2c.shop.config.security.authorization.AccessDecisionManager，decide()方法就是我们上边提到的逻辑，User的Role的集合与Menu的Role的集合取交集，这里是匹配就放行。
还有一个问题不知大家注意没有，未设置操作请求权限，返回空集合，则默认放行。这样的设计有好处也有坏处，我们知道菜单和按钮等一些元件需要在Menu表定义，那势必数据量会比较多，当项目比较大的时候又要做到灵活，
定义会比较麻烦，采用这种方案表示，若Menu表里未定义的资源，只要在登录状态下，即认证后，就可以直接获得权限。换句话说就是我只要定义需要区分权限的部分，数据量会大大减小，不必每次增加需求时候又要忙着加Menu表中的资源，
至于有何坏处，读者自己想吧。<br>
　　MyFilterSecurityInterceptor继承实现于org.springframework.security.access.intercept.AbstractSecurityInterceptor，主要作用就是
把我们刚才定义的MySecurityMetadataSource，MyAccessDecisionManager注入进去，拦截器串起来完成我们的鉴权逻辑。

```
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            Response.out(response, Response.failed(ErrorCode.SERVER_EXCEPTION, "账号或密码错误"));
        } else if (exception instanceof DisabledException) {
            Response.out(response, Response.failed(ErrorCode.SERVER_EXCEPTION, "账号被禁用"));
        } else {
            Response.out(response, Response.failed(ErrorCode.SERVER_EXCEPTION));
        }
    }

}
```
```
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails loginUser = (UserDetails) authentication.getPrincipal();
        String username = loginUser.getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) loginUser.getAuthorities();
        List<String> list = new ArrayList<>();
        authorities.forEach(item -> list.add(item.getAuthority()));
        // JWT登录成功生成token
        String token = SecurityConstant.TOKEN_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(SecurityConstant.AUTHORITIES, JSONUtil.parse(list))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        Response.out(response, Response.restResult(token, ErrorCode.SUCCESS));
    }

}
```
```
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Response.out(httpServletResponse, Response.failed(ErrorCode.ACCESS_DENIED));
    }

}
```
```
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN);
        if (StrUtil.isNotBlank(accessToken)) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(accessToken, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String accessToken, HttpServletResponse response) {
        try {
            // JWT解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(accessToken.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();
            String username = claims.getSubject();
            if (StrUtil.isNotBlank(username)) {
                UserDO user = SpringUtil.getBean(UserServiceImpl.class).findByUsername(username);
                SecurityUserDetails securityUserDetail = new SecurityUserDetails(user);
                User principal = new User(username, "", securityUserDetail.getAuthorities());
                return new UsernamePasswordAuthenticationToken(principal, null, securityUserDetail.getAuthorities());
            }
        } catch (ExpiredJwtException e) {
            Response.failed(ErrorCode.LOGIN_EXPIRED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Response.failed(ErrorCode.SERVER_EXCEPTION, "accessToken解析错误");
        }
        return null;
    }

}
```
```
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_PREFIX = "D2C-";
    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = DigestUtil.md5Hex("shop");
    /**
     * token参数头
     */
    String ACCESS_TOKEN = "accessToken";
    /**
     * author参数头
     */
    String AUTHORITIES = "authorities";

}
```
**解释：**
XXXXHandler都是对于成功和失败的处理类，实现了方法以Json形式返回数据。继承实现于org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler,
org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler, org.springframework.security.web.access.AccessDeniedHandler<br>
注意这里我们引入了JWT作为Token处理的工具。AuthenticationSuccessHandler成功后组装一个accessToken返回给前端，以后前端每次请求head里都带上accessToken，由JWTAuthenticationFilter解析accessToken来过滤请求。<br>
　　JWTAuthenticationFilter继承实现于org.springframework.security.web.authentication.www.BasicAuthenticationFilter，这里我们首先会验证accessToken是否超时，若超时则按失败处理，若没有超时
则将accessToken中的用户名取值，然后去Redis里和数据库里获取该用户对应的权限数据，不管是否能取到值，直接新起一个SecurityUserDetails覆盖内存中的权限数据，鉴权结果直接丢给上边实现的SpringSecurity几个核心类去处理。
这里使用这种方式主要是为了当部署多个服务器时，用户权限数据由公共资源Redis和数据库持久化保存，在量级较小的情形下解决了同步性的问题。当然JWT还有种方案是直接将权限数据加密于accessToken内，
服务端直接根据accessToken来鉴权，完全依赖于客户端存储，并再生成一个refreshToken用户刷新accessToken，此种方案在权限数据同步上比较复杂，适合量级比较大的情形，读者可以自行选择实现。

```
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
```
```
# ignored-urls
ignored:
  urls:
  - /shop/user/expired
  - /shop/user/insert
  - /test/**
  - /druid/**
  - /swagger-ui.html
  - /swagger-resources/**
  - /swagger/**
  - /**/v2/api-docs
  - /**/*.js
  - /**/*.css
  - /**/*.png
  - /**/*.ico
```
```
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        for (String url : ignoreUrlsConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        registry.and()
                // 表单登录方式
                .formLogin()
                .loginPage("/shop/user/expired")
                .loginProcessingUrl("/shop/user/login")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                // 默认登出方式
                .and()
                .logout()
                .permitAll()
                // 任何请求需要身份认证
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 关闭跨站请求防护
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                // 自定义权限拦截器JWT过滤器
                .and()
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

}
```
**解释：**
最后我们一气呵成，把我们上边做的所有工作，都注入到WebSecurityConfig中，BCryptPasswordEncoder()实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码，动态加盐每次加密的结果都不同。
IgnoreUrlsConfig自定义一些不需要鉴权的url，例如我们的文档swagger路径，和默认登录过期地址/shop/user/expired和用户注册地址/shop/user/insert，到此为止我们权限的工作基本已完成，
还差登陆后动态菜单，和几个表数据的增删改查，这些就是写个业务，这里不做演示了，怎么样，很酷吧!

## EasyPoi极简Excel工具整合

　　EasyPoi功能如同名字easy，主打的功能就是容易，让一个没见接触过poi的人员
就可以方便的写出Excel导出，Excel模板导出，Excel导入，Word模板导出，通过简单的注解和模板
语言，完成以前复杂的写法。

```
        <!-- EasyPoi Excel -->
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-base</artifactId>
            <version>3.2.0</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-web</artifactId>
            <version>3.2.0</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-annotation</artifactId>
            <version>3.2.0</version>
        </dependency>
```
**解释：**
EasyPoi这个工具功能非常强大，这里只做每个列表控制器自动附带一个导出功能，具体还是请参考官网：http://easypoi.mydoc.io/

```
@Data
public abstract class BaseDO extends Model {

    @ApiModelProperty(value = "唯一主键ID")
    private Long id;
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "创建用户")
    private String createMan;
    @ApiModelProperty(value = "修改时间")
    private Date modifyDate;
    @ApiModelProperty(value = "修改用户")
    private String modifyMan;

}
```
```
@Data
@TableName("sys_user")
@ApiModel(description = "用户表")
public class UserDO extends BaseDelDO {

    @Excel(name = "账号")
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @Excel(name = "状态", replace = {"正常_1", "禁用_0"})
    @ApiModelProperty(value = "状态")
    private Integer status;
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
**解释：**
在这里，大家主要关注 @Excel的用法就行，format是导出时间格式设置，replace表示数据值和输出值转换，当然有更多更复杂的用法参考官方文档。

```
public abstract class BaseExcelCtrl<E extends BaseDO, Q extends BaseQuery> extends BaseCtrl<E, Q> implements IExcelExportServer {

    @Autowired
    public BaseExcelCtrl<E, Q> excelExportServer;

    @Override
    public List<Object> selectListForExcelExport(Object o, int i) {
        Q query = (Q) o;
        Page page = new Page(i, PageModel.MAX_SIZE, false);
        List<E> list = service.page(page, QueryUtil.buildWrapper(query)).getRecords();
        List<Object> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    @ApiOperation(value = "分页导出数据")
    @RequestMapping(value = "/excel/page", method = RequestMethod.GET)
    public R excelPage(PageModel page, Q query, ModelMap map, HttpServletRequest request,
                       HttpServletResponse response) {
        ExportParams params = new ExportParams("excel数据表", "sheet", ExcelType.XSSF);
        map.put(BigExcelConstants.CLASS, ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        map.put(BigExcelConstants.PARAMS, params);
        map.put(BigExcelConstants.DATA_PARAMS, query);
        map.put(BigExcelConstants.DATA_INTER, excelExportServer);
        PoiBaseView.render(map, request, response, BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW);
        return Response.restResult(null, ErrorCode.SUCCESS);
    }

}
```
**解释：**
这里扩展一下我们原先的BaseCtrl，附加一个分页导出数据的方法，解释下具体的代码含义。
继承实现cn.afterturn.easypoi.handler.inter.IExcelExportServer的selectListForExcelExport(Object var1, int var2)方法，其实就是让我们实现分页拉取数据的方法，
第一个参数表示查询参数的封装，第二个字段则表示页码。注意在用MybatisPlus的时候分页这个定义 Page page = new Page(i, PageModel.MAX_SIZE, false)的false，这个非常重要，表示不必每页拉数据的时候count全表，大大提升分页拉取数据的效率。
ExportParams第一个参数表示表头名（不是文件名），第二个表示sheet表名。ModelMap里丢的几个参数，BigExcelConstants.CLASS表示具体每行对应的POJO对象，这里我们用DO对象，
BigExcelConstants.PARAMS就是ExportParams，BigExcelConstants.DATA_PARAMS这个是Object型的数据查询参数，对应到selectListForExcelExport第一个参数，这里我们是封装的Query对象，
BigExcelConstants.DATA_INTER这个是具体实现IExcelExportServer类的对象。至此，只要继承了BaseExcelCtrl的控制器，自动附带一个分页导出Excel的请求功能，怎么样，很酷吧!

## ElasticSearch收集请求日志整合

代码是完整，文档待续......