package com.jachs.activiti.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Test;

/***
	管理用户
	管理用户组
	用户与用户组的关系(MemberShip)
 * @author zhanchaohan
 *
 */
public class IdentityServiceDemo {
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	IdentityService identityService=engine.getIdentityService();
	
	@Test
	public void tt() {
		 User user = identityService.newUser("Jonathan");
		 
		 user.setFirstName("Jonathan");
		 user.setLastName("chang");
		 user.setEmail("whatlookingfor@gmail.com");
		 user.setPassword("123");
		    //保存用户到数据库
		 identityService.saveUser(user);
		 
		 User userInDb = identityService.createUserQuery().userId("Jonathan").singleResult();
	}
	
	@Test
	public void t1() {
		String groupId="g1";
		
		Group group=identityService.newGroup(groupId);
		
		group.setName("gName");
		group.setType("gType");
		identityService.saveGroup(group);
	}
	
	@Test
	public void t3() {
		 //创建并保存用户组
        Group group = identityService.newGroup("hr");
        group.setName("hr用户组");
        group.setType("assignment");
        //保存用户组
        identityService.saveGroup(group);

        User user = identityService.newUser("Jonathan");
        user.setFirstName("Jonathan");
        user.setLastName("chang");
        user.setEmail("whatlookingfor@gmail.com");
        user.setPassword("123");
        
        User user1 = identityService.newUser("jack");
        user1.setFirstName("puter");
        user1.setLastName("jarm");
        user1.setEmail("kkji@gmail.com");
        user1.setPassword("abc");
        
        identityService.saveUser(user);
        identityService.saveUser(user1);
        
        identityService.createMembership("Jonathan","hr");
        identityService.createMembership("jack","hr");
        
        
        //查询用户所属组
        Group groupContainsUser = identityService.createGroupQuery().groupMember("Jonathan").singleResult();
        
        //查询组所有用户
        List<User> userList= identityService.createUserQuery().memberOfGroup("hr").list();
        
        for (User us : userList) {
			System.out.println(us.getFirstName());
		}
	}
	
	@Test
	public void t5() {
		// 修改用户信息
        User user11 = identityService.createUserQuery().userId("user1").singleResult();
        user11.setLastName("dd");
        identityService.saveUser(user11);
	}
	
	@Test
	public void t2() {
		List<Group> datas = identityService.createGroupQuery().orderByGroupId().asc().list();

		for (Group data : datas) {
			System.out.println(data.getId());
			System.out.println(data.getName());
			System.out.println(data.getType());
		}
	}
}
