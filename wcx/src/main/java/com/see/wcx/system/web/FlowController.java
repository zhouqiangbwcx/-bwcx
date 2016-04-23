package com.see.wcx.system.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.see.wcx.common.persistence.Page;
import com.see.wcx.common.persistence.PropertyFilter;
import com.see.wcx.common.utils.UUIDBuild;
import com.see.wcx.common.web.BaseController;
import com.see.wcx.system.entity.ActIdGroupEntity;
import com.see.wcx.system.entity.ActIdUserEntity;
import com.see.wcx.system.entity.ProcessDefinitionInfo;
import com.see.wcx.system.entity.TaskCustom;
import com.see.wcx.system.service.FlowService;
import com.see.wcx.system.service.UserOrgService;
import com.see.wcx.system.service.UserRoleService;
import com.see.wcx.system.service.UserService;

/**
 * 用户controller
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/flow")
public class FlowController extends BaseController {

	@Autowired
	private  ProcessEngineConfiguration processEngineConfiguration;
	 
	@Autowired
	private RepositoryService repositoryService;
	
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService; 
	
	@Autowired
	private FlowService flowService;
	
	// 测试启动流程
		@RequestMapping("/flowService")
		@ResponseBody
		public String flowService(String id) throws Exception {
			String processDefinitionKey="purchasingflow";
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put("businessKey",  UUIDBuild.getUUID());
			String ss=	flowService.processStart(processDefinitionKey, id,variables);
			 System.out.println("================================:"+ss);
			
			 return ss;
		}
		
		@RequestMapping("/taskList")
		@ResponseBody
		public  List<ProcessDefinitionInfo> taskList(String id) throws Exception {
			String processDefinitionKey="purchasingflow";
			Map<String, List<TaskCustom>>list=	flowService.taskList(  id,processDefinitionKey);
			
			System.out.println("================================:");
			Map<String, List<TaskCustom>> lista=flowService.taskList( id,  null);
			
			System.out.println("================================:");
			 List<ProcessDefinitionInfo>  sl=flowService.queryHistoryProcessInstance( processDefinitionKey);
			return sl;
		}
		
		@RequestMapping("/submitTask")
		@ResponseBody
		public String submitTask(String id,String taskId) throws Exception {
			Map<String,Integer> map=new HashMap<String, Integer>();
			map.put("price", 1000);
			
			Map<String,Integer> firstAudit=new HashMap<String, Integer>();
			firstAudit.put("status", 1);
			
			Map<String, Object> variables=new HashMap<String, Object>();
					variables.put("thirdAudit", firstAudit);
					//variables.put("order", map);
			flowService.submitTask(id,  taskId,variables);
			
			return "xxx";
		}
		
		

		
	// 流程定义部署页面
	@RequestMapping("/deployProcess")
	public String deployProcess(Model model) throws Exception {

		return "flow/deployProcess";
	}
	
	
	
	
	
	// 流程定义查询
		@RequestMapping("/queryProcessDefinitionJson")
		@ResponseBody
		public List<ProcessDefinitionInfo> queryProcessDefinition() throws Exception {

			// 流程定义的key
			/*String processDefinitionKey = ResourcesUtil.getValue(
					"diagram.purchasingflow", "purchasingProcessDefinitionKey");// 采购流程标识
*/		
		String processDefinitionKey="purchasingflow";
		// 创建查询对象
			ProcessDefinitionQuery processDefinitionQuery = repositoryService
					.createProcessDefinitionQuery();

			// 设置查询条件
			// 只查询采购流程的定义
			processDefinitionQuery.processDefinitionKey(processDefinitionKey);

			// 查询列表
			List<ProcessDefinition> list = processDefinitionQuery.list();
			// 分页查询
			// processDefinitionQuery.listPage(firstResult, maxResults)
			// 根据流程定义的id查询一条记录
			// processDefinitionQuery.processDefinitionId(definitionId).singleResult();

			List<ProcessDefinitionInfo> pdInfoList=new ArrayList<ProcessDefinitionInfo>();
			
			
			ProcessDefinitionInfo pdInfo=null;
			 for (ProcessDefinition processDefinition : list) {
				 pdInfo= new ProcessDefinitionInfo();
				 pdInfo.setDeploymentId(processDefinition.getDeploymentId()); 
				 pdInfo.setId(processDefinition.getId());
				 pdInfo.setKey(processDefinition.getKey());
				 pdInfo.setName(processDefinition.getName());
				 pdInfo.setVersion(processDefinition.getVersion());
				 
				 pdInfoList.add(pdInfo);
			 
			  }
			 
			return pdInfoList;
		}
	
	// 流程定义查询
	@RequestMapping("/queryProcessDefinition")
		public String queryProcessDefinition(Model model) throws Exception {

			// 流程定义的key
			/*String processDefinitionKey = ResourcesUtil.getValue(
					"diagram.purchasingflow", "purchasingProcessDefinitionKey");// 采购流程标识
*/		
		String processDefinitionKey="purchasingflow";
		// 创建查询对象
			ProcessDefinitionQuery processDefinitionQuery = repositoryService
					.createProcessDefinitionQuery();

			// 设置查询条件
			// 只查询采购流程的定义
			processDefinitionQuery.processDefinitionKey(processDefinitionKey);

			// 查询列表
			List<ProcessDefinition> list = processDefinitionQuery.list();
			// 分页查询
			// processDefinitionQuery.listPage(firstResult, maxResults)
			// 根据流程定义的id查询一条记录
			// processDefinitionQuery.processDefinitionId(definitionId).singleResult();

			model.addAttribute("list", list);

			
			/* * for (ProcessDefinition processDefinition : list) {
			 * System.out.println("================================");
			 * System.out.println("流程定义id：" + processDefinition.getId());
			 * System.out.println("流程定义部署id：" +
			 * processDefinition.getDeploymentId()); System.out.println("流程定义的key："
			 * + processDefinition.getKey()); System.out.println("流程定义的名称：" +
			 * processDefinition.getName()); System.out.println("bpmn资源名称：" +
			 * processDefinition.getResourceName()); System.out.println("png资源名称：" +
			 * processDefinition.getDiagramResourceName()); }
			 */

			return "flow/queryProcessDefinition";
		}
	

	// 流程定义部署提交
	@RequestMapping("/deployProcessSubmit")
	@ResponseBody
	public String deployProcessSubmit(MultipartFile resource_bpmn,
			MultipartFile resource_png) throws Exception {
		// 第一步：上传文件
		// springmvc通过文件上传的参数解析器将页面提交 的file赋值给形参
		// resource_bpmn和resource_png存储了上传的文件

		// 第二步：调用activiti的service执行流程定义部署
		// 部署bpmn文件和png文件
		// bpmn文件名

		String resourceName_bpmn = resource_bpmn.getOriginalFilename();
		InputStream inputStream_bpmn = resource_bpmn.getInputStream();

		String resourceName_png = resource_png.getOriginalFilename();
		InputStream inputStream_png = resource_png.getInputStream();

		// 部署对象
		Deployment deployment = repositoryService.createDeployment()
				.addInputStream(resourceName_bpmn, inputStream_bpmn)// 部署bpmn文件
				.addInputStream(resourceName_png, inputStream_png)// 部署png文件
				.deploy();

		// 部署对象id
		System.out.println("部署id：" + deployment.getId());

		System.out.println("部署时间：" + deployment.getDeploymentTime());

		// 返回到流程定义的查询页面
		return "success";
	}
	
	
	// 删除流程定义
	@RequestMapping("/deleteProcessDefinition")
	@ResponseBody
	public String deleteProcessDefinition(String processDefinitionId)
			throws Exception {

		// 根据流程定义id查询流程定义对象
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		// 流程定义所属部署id
		String deploymentId = processDefinition.getDeploymentId();

		// 级联删除
		repositoryService.deleteDeployment(deploymentId, true);

		// 返回到流程定义的查询页面
		return "success";
	}
	
	// 流程定义资源文件查看
	/**
	 * 
	 * <p>Title: queryProcessDefinitionResource</p>
	 * <p>Description: </p>
	 * @param response  输出 对象
	 * @param processDefinitionId 流程定义id
	 * @param resourcesType 资源 文件类型（bpmn和png）
	 * @throws Exception
	 */
		@RequestMapping("/queryProcessDefinitionResource")
		public void queryProcessDefinitionResource(HttpServletResponse response,String processDefinitionId,
				String resourceType) throws Exception {
			//根据流程定义id获取流程定义对象
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			// 部署id
			String deploymentId = processDefinition.getDeploymentId();

			// 资源 文件名称
			String resourceName = null;

			if (resourceType.equals("bpmn")) {
				// bpmn资源文件名称
				resourceName = processDefinition.getResourceName();

			} else if (resourceType.equals("png")) {
				// png资源文件名称
				resourceName = processDefinition.getDiagramResourceName();

			}
			// 资源 文件输入流
			InputStream inputStream = repositoryService.getResourceAsStream(
					deploymentId, resourceName);

			// 流复制

			byte[] b = new byte[1024];

			int len = -1;
			while ((len = inputStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}

		}
		
		// 动态图形
		// 当前运行流程中当前结点图形
		@RequestMapping("/queryActivityMap")
		public String queryActivityMap(Model model, String processInstanceId)
				throws Exception {

			// 根据 流程实例的id查询出流程实例 的对象，从对象 中获取processDefinitionId。

			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();

			String processDefinitionId = processInstance.getProcessDefinitionId();

			// 将流程定义 id传到页面，用于图形显示
			model.addAttribute("processDefinitionId", processDefinitionId);

			// 根据流程实例 id processInstanceId，获取当前结点
			String activityId = processInstance.getActivityId();

			// 根据 流程定义 id查询流程定义 实体对象
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
					.getProcessDefinition(processDefinitionId);
			
			//从流程定义 实体对象查询结点的坐标和宽高
			ActivityImpl activityImpl =  processDefinitionEntity.findActivity(activityId);
			int activity_x= activityImpl.getX();//坐标
			int activity_y = activityImpl.getY();//坐标
			int activity_width =  activityImpl.getWidth();//宽
			int activity_height = activityImpl.getHeight();//高
			
			model.addAttribute("activity_x",activity_x);
			model.addAttribute("activity_y", activity_y);
			model.addAttribute("activity_width", activity_width);
			model.addAttribute("activity_height", activity_height);
			
			

			return "flow/queryActivityMap";

		}
		
		/**
		 * 查询组所有用户
		 * @param groupName
		 */
		public List<ActIdUserEntity> getGroupUser(String groupId){
			List<User> list=identityService.createUserQuery().memberOfGroup(groupId).list();
			List<ActIdUserEntity> alist=new ArrayList<ActIdUserEntity>();
			ActIdUserEntity auser=null;
			for (User user : list) {
				auser=new ActIdUserEntity();
				auser.setEmail(user.getEmail());
				auser.setFirstName(user.getFirstName());
				auser.setId(user.getId());
				auser.setLastName(user.getLastName());
				auser.setPassword(user.getPassword());
				auser.setPictureSet(user.isPictureSet());
				alist.add(auser);
			}
			return alist;
		}
		
		

		
		/**
		 * 查询权限组页面
		 * @param model
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/flowGroupList")
		public String frowGroupList(Model model) throws Exception {

			return "flow/flowGroupList";
		}
		
		/**查询所有组
		 * @return
		 */
		@RequestMapping("/flowGroupListJosn")
		@ResponseBody
		public List<ActIdGroupEntity>  getGroupList(String entiy)throws Exception {
			List<Group> list=identityService.createGroupQuery().list();
			List<ActIdGroupEntity> alist=new ArrayList<ActIdGroupEntity>();
			ActIdGroupEntity aGroup=null;
			for (Group group : list) {
				aGroup=new ActIdGroupEntity();
				aGroup.setId(group.getId());
				aGroup.setName(group.getName());
				aGroup.setType(group.getType());
				
				if(null==entiy||"".equals(entiy)){
					alist.add(aGroup);
				}else{
					if(group.getId().indexOf(entiy+"-")==0){
						alist.add(aGroup);
					}
					
				}
			}
			
			
			return alist;
		}
		
		/**查询所有用户
		 * @return
		 */
		public  List<ActIdUserEntity> getUserList(){
			List<User> list=identityService.createUserQuery().list();
			List<ActIdUserEntity> alist=new ArrayList<ActIdUserEntity>();
			ActIdUserEntity auser=null;
			for (User user : list) {
				auser=new ActIdUserEntity();
				auser.setEmail(user.getEmail());
				auser.setFirstName(user.getFirstName());
				auser.setId(user.getId());
				auser.setLastName(user.getLastName());
				auser.setPassword(user.getPassword());
				auser.setPictureSet(user.isPictureSet());
				alist.add(auser);
			}
			 return alist;
		}
		
	    /**查询用户所在组
	     * @param userId
	     * @return
	     */
	    public List<ActIdGroupEntity> getUserGroups(String userId){
	    	
	    	List<Group> datas = identityService.createGroupQuery().groupMember(userId).list();
	    	List<ActIdGroupEntity> alist=new ArrayList<ActIdGroupEntity>();
	    	ActIdGroupEntity aGroup=null;
	    	for (Group group : datas) {
				aGroup=new ActIdGroupEntity();
				aGroup.setId(group.getId());
				aGroup.setName(group.getName());
				aGroup.setType(group.getType());
				
				alist.add(aGroup);
			}
	    	return alist;
	    }
	    
	    /**
	     * 用户更新组
	     * @param userId
	     * @param groupIds
	     */
		public List<ActIdGroupEntity> addUserGroup(String userId,String  groupIds){
			
			List<ActIdGroupEntity> list=null;
			try {
				String [] str=null;
				if(null==groupIds ){
					groupIds="";
				}
					 str=groupIds.split(",");	
				

			
			 List<ActIdGroupEntity>  grouList=	getUserGroups( userId);
			 for (ActIdGroupEntity actIdGroupEntity : grouList) {
				 identityService.deleteMembership(userId,actIdGroupEntity.getId());//删除
			 }
			for (String groupId : str) {
				User user = null;
				if (null != userId && !"".equals(userId) &&  null!=groupId&&!"".equals(groupId)) {
					user = identityService.createUserQuery().userId(userId)
							.singleResult();// 查询用户是否有存在
					if (null == user) {
						user = identityService.newUser(userId);
						identityService.saveUser(user);
						user = identityService.createUserQuery().userId(userId)
								.singleResult();// 创建用户
					}
					
					identityService.createMembership(userId, groupId);// 把userId加入到组groupId里面
					
					
				}
			}
			 list= getUserGroups(userId);
			} catch (Exception e) {
				
			}
			return list;
		}
		
		/**
		 * 删除权限组
		 * @param groupId 组id
		 * @return
		 * @throws CLDException
		 */
		public String deleteGroup(String groupId) {
			try {

				List<Group> list=	identityService.createGroupQuery().groupId(groupId).list();
				if(list.size()>0){
					identityService.deleteGroup(groupId);
				}
				

			} catch (Exception e) {
			
				
			}
			return "";
			
		}
		
		/**
		 * 创建修改权限组页面
		 * @param model
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/flowGroupForm")
		public String flowGroupForm(Model model) throws Exception {

			return "flow/flowGroupForm";
		}
		
		/**
		 * 创建用户和组
		 * 
		 * @param userId 必填
		 * @param groupId 组编号 可选
		 * @param groupName组中名称
		 * @param groupType组分两种类型 即assignment和security-role前者一种普通的岗位角色，后置为安全角色可以全局管理用户及整个流程的状态
		 * @return
		 */
		@RequestMapping("/frowUserGroupCreatejson")
		@ResponseBody
		public boolean createUserGroup(String userId, String groupId,
				String groupName,String groupType) throws Exception {
			boolean bl = true;
			try {
				User user = null;
				if (null != userId && !"".equals(userId)) {
					user = identityService.createUserQuery().userId(userId)
							.singleResult();// 查询用户是否有存在
					if (null == user) {
						user = identityService.newUser(userId);
						identityService.saveUser(user);
						user = identityService.createUserQuery().userId(userId)
								.singleResult();// 创建用户
					}
				}
				if (null != groupId && !"".equals(groupId)) {
					List<Group> list = identityService.createGroupQuery()
							.groupId(groupId).list();// 查询组是否有存在
					if (null == list || 0 == list.size()) {
						Group group=identityService.newGroup(groupId);//创建组
						group.setName(groupName);
						group.setType(groupType);
						identityService.saveGroup(group);
					}
					if (null != user) {
						//判断用户是否加入了组
						List<Group> userInGroup=identityService.createGroupQuery().groupMember(userId).list();
						boolean blg=true;
						for (Group group : userInGroup) {
							if(groupId.equals(group.getId())){
								blg=false;
								break;
							}
						}
						if(blg){
						identityService.createMembership(userId, groupId);// 把userId加入到组groupId里面
						}
					}
				}
			} catch (Exception e) {
				bl = false;
			}
			return bl;

		}

		
}
