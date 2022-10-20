package com.jachs.activiti.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

/***
	管理流程定义文件xml及静态资源服务
	对特定流程的暂停和激活
	流程定义启动权限管理
	部署文件构造器DeploymentBuilder
	部署文件查询器DeploymentQuery
	流程定义文件查询对象ProcessDefinitionQuery
	流程部署文件对象Deployment
	流程定义文件对象ProcessDefinition
	流程定义的Java格式BpmnModel
 * @author zhanchaohan
 *
 */
public class RepositoryServiceDemo {
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	RepositoryService repositoryService = engine.getRepositoryService();
	
	@Test
	public void t1() {
		DeploymentBuilder deploymentBuilder= repositoryService.createDeployment();
		
		deploymentBuilder.addClasspathResource(null);//添加classpath下的资源文件 。
		deploymentBuilder.addInputStream(null, null);//添加输入流资源。
		deploymentBuilder.addZipInputStream(null);//添加zip压缩包资源。
		deploymentBuilder.addBpmnModel(null, null);//解析BPMN模型对象，并作为资源保存。
		
		Deployment deployment=deploymentBuilder.deploy();
		
	}
	
	@Test
	public void t2() {
		//激活
		repositoryService.activateProcessDefinitionById(null);
		//挂起
		repositoryService.suspendProcessDefinitionById(null);
	}
}
