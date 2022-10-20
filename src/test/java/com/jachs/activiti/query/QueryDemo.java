package com.jachs.activiti.query;

import java.text.SimpleDateFormat;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

/***
	ProcessEngine processEngine;
	//流程存储服务 提供一系列管理流程定义和流程部署的API
	RepositoryService repositoryService = processEngine.getRepositoryService();
	//任务 对流程任务进行管理 包括任务提醒 完成和创建
	TaskService taskService = processEngine.getTaskService();
	//运行服务组件 在流程运行时对流程实例进行管理与控制
	RuntimeService runtimeService = processEngine.getRuntimeService();
	//表单
	FormService formService = processEngine.getFormService();
	//历史服务组件 对流程历史数据进行操作，包括查询 删除
	HistoryService historyService = processEngine.getHistoryService();
	//身份服务组件 提供对流程角色数据进行管理的API（用户 用户组 及它们之间的关系）
	IdentityService identityService = processEngine.getIdentityService();
	//管理服务组件  提供对流程引擎进行管理和维护服务
	ManagementService managementService = processEngine.getManagementService();
	//动态修改流程中的一些参数信息等，是引擎中的一个辅助的服务 ,使用该服务，可以不需要重新部署流程模型，就可以实现对流程模型的
	//部分修改
	DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
 * @author zhanchaohan
 *
 */
public class QueryDemo {

	private static final String ke="testActiviti";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//获取全部流程图信息
	@Test
	public void tt() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
	    List<ProcessDefinition> definitionList = processDefinitionQuery.list();
	    
	    for (ProcessDefinition processDefinition : definitionList) {
	          System.out.println("流程定义 id="+processDefinition.getId());
	          System.out.println("流程定义 name="+processDefinition.getName());
	          System.out.println("流程定义 key="+processDefinition.getKey());
	          System.out.println("流程定义 Version="+processDefinition.getVersion());
	          System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
	          System.out.println(processDefinition.getCategory());
	          System.out.println(processDefinition.getDescription());
	          System.out.println(processDefinition.getDiagramResourceName());
	          System.out.println(processDefinition.getResourceName());
	          System.out.println(processDefinition.getTenantId());
	          System.out.println(processDefinition.getVersion());
	          System.out.println("----------------------------------------------");
	     }
	}
	
	@Test
	public void t1() {
      ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
      
      RepositoryService repositoryService = processEngine.getRepositoryService();
      ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
      List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey(ke)
              .orderByProcessDefinitionVersion()
              .desc()
              .list();
      
      for (ProcessDefinition processDefinition : definitionList) {
          System.out.println("流程定义 id="+processDefinition.getId());
          System.out.println("流程定义 name="+processDefinition.getName());
          System.out.println("流程定义 key="+processDefinition.getKey());
          System.out.println("流程定义 Version="+processDefinition.getVersion());
          System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
      }
	}
	
	
	//获取历史信息
	 @Test
	 public void findHistoryInfo(){
	        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	        
	        HistoryService historyService = processEngine.getHistoryService();
	        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
	        instanceQuery.processDefinitionId("testActiviti:1:4");
	        
	        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
	        List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
	        for (HistoricActivityInstance hi : activityInstanceList) {
	        	System.out.println(hi.getId());
	            System.out.println(hi.getActivityId());
	            System.out.println(hi.getActivityName());
	            System.out.println(hi.getActivityType());
	            System.out.println(hi.getAssignee());
	            System.out.println(hi.getCalledProcessInstanceId());
	            System.out.println(hi.getExecutionId());
	            System.out.println(hi.getProcessDefinitionId());
	            System.out.println(hi.getProcessInstanceId());
	            System.out.println(hi.getTaskId());
	            System.out.println(hi.getTenantId());
	            System.out.println(hi.getDurationInMillis());
	            System.out.println(sdf.format(hi.getEndTime()));
	            System.out.println(sdf.format(hi.getStartTime()));
	            System.out.println(sdf.format(hi.getTime()));
	            System.out.println("<==========================>");
	        }
	 }
}
