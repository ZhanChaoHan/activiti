package com.jachs.activiti.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

/***
	对用户任务UserTask的管理和流程的控制
	设置用户任务的权限信息（设置候选人等）
	针对用户任务添加任务附件，任务评论和事件记录
 * @author zhanchaohan
 *
 */
public class TaskServiceDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	TaskService taskService = processEngine.getTaskService();
	
	Task task1 = taskService.createTaskQuery().singleResult();
	ProcessInstance processInstance=null;
	String processKey="1";
	
	private void printTask(List<Task>taskList) {
		for (Task task : taskList) {
			System.out.println("processInstanceId:"+task.getProcessInstanceId());
			System.out.println("taskId:"+task.getId());
			System.out.println("taskName:"+task.getName());
			System.out.println("assignee:"+task.getAssignee());
		}
	}
	
	@Test
	public void t1() {
		 // 设置全局变量
		taskService.setVariable(task1.getId(),"key1","value1");
		// 设置局部变量
		taskService.setVariableLocal(task1.getId(),"key2","value2");

		// 获取全局变量
		Map<String,Object> a = taskService.getVariables(task1.getId());
		// 获取局部变量
		Map<String,Object> b = taskService.getVariablesLocal(task1.getId());
		// 获取全局变量
//		Map<String,Object> c = runtimeService.getVariables(processInstance.getId());
		
		//除了直接设置变量外，在任务提交的时候也可以附带变量，即使用taskService.complete(),这个complete方法多个重载方法：
		taskService.complete(null, b);
	}
	/***
		TaskService设置Task权限信息
		候选用户candidateUser和候选组candidateGroup
		指定拥有人Owner和办理人Assignee
		通过claim设置办理人(签收 )
	 */
	//设置权限
	@Test
	public void t3() {
		////委托
		taskService.delegateTask(null, null);
		taskService.claim(null, null);//办理人
		
		taskService.addCandidateUser(null, null);//候选用户
		taskService.addCandidateGroup(null, null);//候选组
		
		taskService.setOwner(null, null);//设置任务持有人
		taskService.setAssignee(null, null);//设置任务代理人
		
		//添加用户组权限数据，第一个参数为任务 ID， 第二个参数为用户组ID，第三个参数为权限数据类型标识。
		taskService.addGroupIdentityLink(null, null, IdentityLinkType.CANDIDATE);
		//添加用户权限数据，第一个参数为任务 ID， 第二个参数为用户ID，第三个参数为权限数据类型标识。
		taskService.addUserIdentityLink(null, null, null);
	}
	
	//添加评论
	@Test
	public void t5() {
		taskService.addComment(task1.getId(),processInstance.getId(),"record note 1");
		taskService.addComment(task1.getId(),processInstance.getId(),"record note 2");

		List<Comment> commentList = taskService.getTaskComments(task1.getId());
		for(Comment comment : commentList){
			System.out.println(ToStringBuilder.reflectionToString(comment, ToStringStyle.MULTI_LINE_STYLE));
		}
	}
	
	@Test
	public void t6() {
		// 添加附件(地址位于/url/test.png)到task中
		taskService.createAttachment("url",task1.getId(),
		          processInstance.getId(),
		           "name",
		           "desc",
		          "/url/test.png");

		// 查询附件
		List<Attachment> attachmentList = taskService.getTaskAttachments(task1.getId());
		for(Attachment attachment : attachmentList){
		      System.out.println(attachment);
		}
	}
	
	//查询全部任务
	@Test
	public void t9() {
		List<Task> taskList=taskService.createTaskQuery().list();
		
//		List<Task> taskList=taskService.createTaskQuery().processDefinitionKey("testActiviti")
//				.taskAssignee("张三").list();

		printTask(taskList);
	}
	
	//查看事件
	@Test
	public void t7() {
		List<Event> eventList = taskService.getTaskEvents(task1.getId());
		for(Event event : eventList){
			System.out.println(ToStringBuilder.reflectionToString(event,ToStringStyle.SIMPLE_STYLE));
		}
	}
	
	@Test
	public void t2() {
		taskService.complete("15001");//完成
	}
	
	@Test
	public void t10() {
		//act_re_deployment表数据主键
		List<Task> task=taskService.createTaskQuery().deploymentId("1").list();
		printTask(task);
		
//		taskService.claim("2504", "jack");
	}
	
}
