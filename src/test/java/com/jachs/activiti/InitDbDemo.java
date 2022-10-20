package com.jachs.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;

/****
 * 
 * @author zhanchaohan
 *
 */
public class InitDbDemo {
	
	@Test
	public void t1() {
		ProcessEngineConfiguration configration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        //目标生成数据库表
        configration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti");
        configration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configration.setJdbcUsername("root");
        configration.setJdbcPassword("123456");
        //设置表的生成策略
        configration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = configration.buildProcessEngine();

        System.out.println(processEngine.getName());
	}
	
	@Test
	public void t2() {
		ProcessEngineConfiguration
	     .createProcessEngineConfigurationFromResourceDefault()
	     .setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_CREATE)
	     .buildProcessEngine();
	}
	
	@Test
	public void t3() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		System.out.println("processEngine:\t" + processEngine);
	}
}
