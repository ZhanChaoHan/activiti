package com.jachs.activiti.query;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;

/***
 * 
 * @author zhanchaohan
 *
 */
public class Query2Demo {

	private static final String ke="testActiviti";
	
	@Test
	public void tt() {
		 ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		 // 得到流程存储服务
		 RepositoryService repositoryService = engine.getRepositoryService();
		 // 得到运行时服务
		 RuntimeService runtimeService = engine.getRuntimeService();
		 // 获取流程任务
		 TaskService taskService = engine.getTaskService();
		 // 部署流程文件,文件名与第二节第4步一致。流程更新后，需要重新部署流程文件
		 repositoryService.createDeployment().addClasspathResource("bpmn/MyProcess.bpmn").deploy();
		 // // 开启流程，流程名与流程定义中一致，第二节第8步。
		 runtimeService.startProcessInstanceByKey("myProcess","xxx");
		 // 查询第一个节点的任务并且输出
		 Task task = taskService.createTaskQuery().singleResult();
		 System.out.println("第一个任务完成前，当前任务名称：" + task.getName());
		 //设置请假申请任务的执行人
		 taskService.setAssignee(task.getId(), "zhuzhu");
		 Map<String, Object> taskVariables = new HashMap<String, Object>();
		 taskVariables.put("vacationApproved", "false"); //拒绝了请假
		 taskVariables.put("managerMotivation", "We have a tight deadline!");
						
		 // 完成第一个任务，相当于流程图中的请假申请
		 taskService.complete(task.getId(),taskVariables);
		 // 查询第二个节点的任务并且输出
		 task = taskService.createTaskQuery().singleResult();
		 System.out.println("第二个任务完成前，当前任务名称：" + task.getName());
		 taskService.claim(task.getId(), "chenghong");
		 // 完成第二个任务，相当于流程图中的请假审核（流程结束）
		 taskService.complete(task.getId());
		 task = taskService.createTaskQuery().singleResult();
		 System.out.println("流程结束后，查找任务：" + task);
	}
}
