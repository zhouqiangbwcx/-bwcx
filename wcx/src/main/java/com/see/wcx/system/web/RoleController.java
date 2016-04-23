package com.see.wcx.system.web;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.see.wcx.common.persistence.Page;
import com.see.wcx.common.persistence.PropertyFilter;
import com.see.wcx.common.web.BaseController;
import com.see.wcx.system.entity.Role;
import com.see.wcx.system.entity.User;
import com.see.wcx.system.service.RolePermissionService;
import com.see.wcx.system.service.RoleService;

/**
 * 角色controller
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "system/roleList";
	}
	
	/**
	 * 角色集合(JSON)
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Role> page=getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = roleService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取角色拥有的权限ID集合
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:permView")
	@RequestMapping("{id}/json")
	@ResponseBody
	public List<Integer> getRolePermissions(@PathVariable("id") Integer id){
		List<Integer> permissionIdList=rolePermissionService.getPermissionIds(id);
		return permissionIdList;
	}
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:role:permUpd")
	@RequestMapping(value = "{id}/updatePermission")
	@ResponseBody
	public String updateRolePermission(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleIdList,HttpSession session){
		List<Integer> oldRoleIdList=rolePermissionService.getPermissionIds(id);
		
		//获取application中的sessions
		@SuppressWarnings("rawtypes")
		HashSet sessions=(HashSet) session.getServletContext().getAttribute("sessions");
		if (null!=sessions) {//当前如果有正在使用的用户，需要更新正在使用的用户的权限
			
			@SuppressWarnings("unchecked")
			Iterator<Session> iterator= sessions.iterator();
			PrincipalCollection pc=null;
			
			//遍历sessions
			while(iterator.hasNext()){
				HttpSession s=(HttpSession) iterator.next();
				User user=(User) s.getAttribute("user");
				if(user.getId()==id){
					pc= (PrincipalCollection) s.getAttribute(String.valueOf(id));
					//清空该用户权限缓存
					rolePermissionService.clearUserPermCache(pc);
					s.removeAttribute(String.valueOf(id));
					break;
				}
			}
		}
		
		rolePermissionService.updateRolePermission(id,oldRoleIdList ,newRoleIdList);
		
		return "success";
	}
	
	/**
	 * 添加角色跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("action", "create");
		return "system/roleForm";
	}

	/**
	 * 添加角色
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Role role,Model model) {
		roleService.save(role);
		// 设置组信息
		// 添加之前应该校验组信息是否存在，不存在再进行添加
		
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(role.getId().toString());
		groupEntity.setName(role.getRoleCode());
		identityService.saveGroup(groupEntity);
		
		return "success";
	}

	/**
	 * 修改角色跳转
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("role", roleService.get(id));
		model.addAttribute("action", "update");
		return "system/roleForm";
	}

	/**
	 * 修改角色
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("role") Role role,Model model) {
		

		roleService.save(role);
		if(identityService.createGroupQuery().groupId(role.getId().toString()).singleResult()==null){
		// 修改权限组
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(role.getId().toString());
		groupEntity.setName(role.getRoleCode());	
		identityService.saveGroup(groupEntity);
		}
		return "success";
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		roleService.delete(id);
		if(identityService.createGroupQuery().groupId(id.toString()).singleResult()!=null){
			identityService.deleteGroup(id.toString());
		}
		return "success";
	}
	
	@ModelAttribute
	public void getRole(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("role", roleService.get(id));
		}
	}
}
