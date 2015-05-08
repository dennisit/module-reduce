# module-reduce
module-reduce component

[module-reduce SQL]

		CREATE TABLE `tb_module_reduce` (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `module_name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '服务模块',
              `module_switch` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否降级,1降级0不降级',
              `module_depict` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '业务描述',
              `module_token` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '认证key',
              PRIMARY KEY (`id`),
              UNIQUE KEY `module_name` (`module_name`)
        ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


[How to Use]
[1、bean config]
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
	default-autowire="byName">

	<description>datasource configure</description>

	<bean id="parentDataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="maxWait" value="3000" />
		<property name="validationQuery" value="SELECT 1" />
		<property name="testOnBorrow" value="false" />
		<property name="testWhileIdle" value="true" />
		<property name="initialSize" value="100" />
		<property name="maxActive" value="200"/>
		<property name="minIdle" value="10"/>
		<property name="maxIdle" value="50"/>
		<property name="removeAbandoned" value="true"/>
		<property name="removeAbandonedTimeout" value="30"/>
		<property name="timeBetweenEvictionRunsMillis" value="30000" />
		<property name="driverClassName" value="com.mysql.jdbc.Driver" />
	</bean>


	<bean id="dataSource" parent="parentDataSource">
		<property name="url" value="jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&amp;characterEncoding=UTF-8" />
		<property name="username" value="root" />
		<property name="password" value="root" />
	</bean>

	<bean id="moduleTable" class="com.plugin.server.reduce.object.ModuleTable">
		<property name="tableName" value="tb_module_reduce" />
		<property name="primaryKey" value="id" />
		<property name="moduleName" value="module_name" />
		<property name="moduleSwitch" value="module_switch" />
		<property name="moduleDepict" value="module_depict" />
		<property name="moduleToken" value="module_token" />
	</bean>

	<bean id="moduleReduceClient" class="com.plugin.server.reduce.ModuleReduceFacotyBean">
		<property name="dataSource" ref="dataSource" />
		<property name="moduleTable" ref="moduleTable" />
	</bean>

</beans>

[2、bean use]
public class ModuleReduceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-config-datasource.xml");

    private ModuleReduceClient moduleReduceClient;

    @Before
    public void init(){
        moduleReduceClient = (ModuleReduceClient) context.getBean("moduleReduceClient");
        if(null != moduleReduceClient ){
            System.out.println("init moduleReduceClient finish!");
        }
    }

    @Test
    public void buildSQL(){
        ModuleObject moduleObject3 = new ModuleObject();
        moduleObject3.setPrimaryKey(1L);
        moduleObject3.setModuleSwitch(1);
        moduleObject3.setModuleDepict("服务模块描述");
        moduleObject3.setModuleName("服务名称");
        System.out.println(">> " + SQLBuilder.buildQueryPager(moduleReduceClient.getModuleTable(), moduleObject3));
        System.out.println(">> " + SQLBuilder.buildQueryTotal(moduleReduceClient.getModuleTable(),moduleObject3));
    }

    @Test
    public void insertModule(){
        ModuleObject moduleObject = new ModuleObject();
        moduleObject.setModuleName("module-name-test");
        moduleObject.setModuleSwitch(Constants.SwitchStatus.OPEN.getValue());
        moduleObject.setModuleToken("module-name-token");
        moduleObject.setModuleDepict("测试数据");
        boolean result = moduleReduceClient.insertModule(moduleObject);
        System.out.println("添加新的服务开关:" + result);
    }

    @Test
    public void switchModuleClose(){
        String moduleName = "module-name-test";
        String moduleToken = "module-name-token";
        boolean resultDown = moduleReduceClient.closeModule(moduleName, moduleToken);
        System.out.println("服务降级:" + resultDown);
    }

    @Test
    public void switchModuleOpen(){
        String moduleName = "module-name-test";
        String moduleToken = "module-name-token";
        boolean resultDown = moduleReduceClient.openModule(moduleName, moduleToken);
        System.out.println("服务开启:" + resultDown);
    }

    @Test
    public void queryModuleSwitch(){
        String moduleName = "module-name-test";
        String moduleToken = "module-name-token";
        Result result = moduleReduceClient.queryModuleSwitch(moduleName, moduleToken);
        System.out.println("====" + new Gson().toJson(result));
    }

    @Test
    public void queryModules(){
        Page<ModuleObject> pages = moduleReduceClient.queryModules(new ModuleObject(),3,5);
        System.out.println("所有记录:" + new Gson().toJson(pages));
    }

}