package com.jachs.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/***
 * 
 * @author zhanchaohan
 *
 */
public class Demo1 {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//流程的部署
	@Test
	public void t1() {
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
		        .createDeployment()//创建一个部署对象
		        .name("application-test")//添加部署的名称
		        .addClasspathResource("diagrams/t1/application.bpmn")//从classpath的资源中加载，一次只能加载一个文件
		        .addClasspathResource("diagrams/t1/application.png") 
//		        .addZipInputStream(zipInputStream)
		        .deploy();//完成部署

		        System.out.println("部署ID："+deployment.getId());
		        System.out.println("部署名称："+deployment.getName());//test
	}
	
	//启动流程
	@Test
	public void t2() {
		 //流程定义的key
	    String processDefinitionKey = "testActiviti";
	    ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
	    .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应application.bpmn文件中id的属性值，使用key值启动，默认是按照最新                                  //版本的流程定义启动
	    System.out.println("流程实例ID:"+pi.getId());
	    System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
	}
	
	//查询待办任务
	@Test
	public void t3() {
		String assignee = "张三";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                        .createTaskQuery()//创建任务查询对象
                        .taskAssignee(assignee)//指定个人任务查询，指定办理人
                        .list();for(Task task:list){
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
            }
	}
	//完成任务
	@Test
	public void t4() {
		 //任务ID
        String taskId = "17501";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskId);
        System.out.println("完成任务：任务ID："+taskId);
	}
	
	//查看流程状态
	@Test
	public void t5() {
		 ProcessInstance pi=processEngine.getRuntimeService() // 获取运行时Service
		            .createProcessInstanceQuery() // 创建流程实例查询
		            .processInstanceId("17501") // 用流程实例ID查询
		            .singleResult();
		 if(pi!=null){
		     System.out.println("流程正在执行！");
		 }else{
		     System.out.println("流程已经执行结束！");
		 }
	}
}
