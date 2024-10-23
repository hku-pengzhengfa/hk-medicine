```shell script
#项目描述
1.技术选型:nacos,jdk11,mysql,redis,自定义线程池,redisson,rocketmq,gateway,swagger3,sentinel,nacos-config,web3j,aliyun oss
2.架构思想:通过业务模块进行拆分,网关整合限流框架进行动态化限流配置(可以针对接口或者服务进行灵活限流配置,防止黑客攻击),另外通过自定义线程池合理利用服务器资源
#需要改造的的点
1.分布式定时任务
2.时间发布机制使用disruptor框架,性能更高
3.多数据源使用框架dynamic-datasource-spring-boot-starter
4.开机自动启动,设置jvm参数
5.异步任务使用自定义线程池
6.http请求用RestTemplate

#代码编写规范
1.调用api的通用写法:private RedisTemplate redisTemplate;
2.entity的通用写法:@Data,@TableName(value = "name"),@TableField(value = "")
3.数据返回的的时候复用entity的字段
4.当访问数据库的字段和参数冲突时用@Transient标注
5.使用英文名称,驼峰,数据库字段使用下划线,返回字段使用驼峰,相同的代码做成公共方法,代码需要职责单一,代码清晰明了,分点,写注释,一个类一屏

#代码存在问题
1.@EnableTransactionManagement没有添加
2.使用@Transactional的时候,需要把异常抛出去
3.无特殊情况下,时间复杂度控制在O(1)
4.代码时间复杂度过高
5.spring自带的缓存不进行内存管理很容易出现内存溢出
6.索引调优
7.减少sql上的计算
8.分页查询采用统一的mybatis-plus分页查询
9.实体类中set和get方法用注解代替
10.操作redis使用RedisTemplate,应用广泛灵活
11.命令驼峰
12.jar包私有化处理,安全jar处理
13.安全处理:限流,ip封禁,数据加密,非网关服务的都关闭外网端口
14.@RequiredArgsConstructor构造器注解不要乱用,spring中的事件传播机制换成java分布式框架的事件机制
15.final不要乱用,可能会导致内存分配资源不足
16.jvm参数设置:-XX:+UseG1GC -XX:PretenureSizeThreshold=2M -XX:+UseCompressedOops -XX:+UseTLAB
17.分页功能没做
```