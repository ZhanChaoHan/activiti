package com.jachs.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/***
 * 启动服务
 * @author zhanchaohan
 *
 */
public class StartService {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	RuntimeService runtimeService = processEngine.getRuntimeService();
	TaskService taskService = processEngine.getTaskService();
	
	
	private static final String InstanceKey="testActiviti";
	//流程的部署
	@Test
	public void t1() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(InstanceKey);
		
		System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
	    System.out.println("流程实例id：" + processInstance.getId());
	    System.out.println("当前活动Id：" + processInstance.getActivityId());
	}
	
	//占位符方式启动
	@Test
	public void t2() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //创建流程实例  流程定义的key  或者别的参数
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("user", "张三");//写入bpmn定义的占位符
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(InstanceKey,variables);
        
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
	    System.out.println("流程实例id：" + processInstance.getId());
	    System.out.println("当前活动Id：" + processInstance.getActivityId());
	}
	
	@Test
	public void t3() {
		String processInstanceId="2501";
		String taskId="2505";
		
		taskService.addComment(taskId, processInstanceId, "一条消息");
		Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("reviewed", "李四");//审批人
        taskService.complete(taskId,variables);
	}
	
}
