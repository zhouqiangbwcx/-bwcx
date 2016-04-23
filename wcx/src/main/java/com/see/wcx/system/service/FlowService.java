package com.see.wcx.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.see.wcx.common.utils.StringUtils;
import com.see.wcx.system.entity.ProcessDefinitionInfo;
import com.see.wcx.system.entity.TaskCustom;

@Service
@Transactional
public class FlowService {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	/**
	 * 启动流程
	 * 
	 * @param processDefinitionKey
	 *            流程key
	 * @param userId
	 *            用户id
	 * @param variables
	 *            全局变量
	 * @return 流程id
	 */
	public String processStart(String processDefinitionKey, String userId,
			Map<String, Object> variables) throws Exception {

		// 设置流程发起人
		identityService.setAuthenticatedUserId(userId);

		// 启动流程实例
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(processDefinitionKey, variables);
		// 启动的流程实例id
		String processInstance_id = processInstance.getProcessInstanceId();

		return processInstance_id;
	}

	/**
	 * 查询任务
	 * 
	 * @param userId
	 *            用户id 必须有
	 * @param processDefinitionKey
	 *            流程定义key（流程定义的标识 ） 可以为空
	 * @return 需要返回的内容：assignee受理人,candidate候选人<任务id、任务标识 、任务名称、任务负责人、全局变量>
	 * @throws Exception
	 */
	public Map<String, List<TaskCustom>> taskList(String userId,
			String processDefinitionKey) throws Exception {
		Map<String, List<TaskCustom>> taskMap = new HashMap<String, List<TaskCustom>>();
		// 用户空时
		if (StringUtils.isNullOrEmpty(userId)) {
			return null;
		}
		// 需要直接人返回的内容：任务id、任务标识 、任务名称、任务负责人、业务标识
		taskMap.put("assignee", assigneeTaskList(userId, processDefinitionKey));

		// 需要候选人返回的内容：任务id、任务标识 、任务名称、任务负责人、业务标识
		taskMap.put("candidate",
				candidateTaskList(userId, processDefinitionKey));

		return taskMap;
	}

	// 任务转换
	private List<TaskCustom> getTaskCustomList(List<Task> list)
			throws Exception {

		List<TaskCustom> taskList = new ArrayList<TaskCustom>();

		for (Task task : list) {

			TaskCustom taskCustom = new TaskCustom();

			// 流程实例id
			String processInstanceId = task.getProcessInstanceId();
			// 根据流程实例id找到流程实例对象
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();

			Map<String, Object> variables = runtimeService
					.getVariables(processInstanceId);

			// 下边向orderCustom开始设置任务信息
			// 任务id、任务标识 、任务名称、任务负责人、全局变量
			taskCustom.setVariables(variables);
			// 任务id
			taskCustom.setTaskId(task.getId());
			// 任务标识
			taskCustom.setTaskDefinitionKey(task.getTaskDefinitionKey());
			// 任务名称
			taskCustom.setTaskName(task.getName());
			// 任务负责人
			taskCustom.setAssignee(task.getAssignee());

			taskList.add(taskCustom);
		}
		return taskList;
	}

	/**
	 * 查询直接分配的任务
	 * 
	 * @param userId
	 *            直接用户
	 * @param processDefinitionKey
	 *            流程定义key
	 * @return
	 */
	public List<TaskCustom> assigneeTaskList(String userId,
			String processDefinitionKey) throws Exception {
		// 任务 负责人
		String assignee = userId;
		// 创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();

		// 设置查询条件
		taskQuery.taskAssignee(assignee);
		if (StringUtils.isNoNullOrEmpty(processDefinitionKey)) {
			// 指定 流程定义key，只查询某个流程的任务
			taskQuery.processDefinitionKey(processDefinitionKey);
		}
		// 设置排序 字段，根据任务创建时间降序
		taskQuery.orderByTaskCreateTime().desc();
		// 获取查询列表
		List<Task> list = taskQuery.list();
		return getTaskCustomList(list);

	}

	/**
	 * 查询候选任务
	 * 
	 * @param candidateUserId
	 *            候选人，在act_id_user表中存在，从act_id_membership通过group_id_查询出用户
	 * @param processDefinitionKey
	 *            流程定义key
	 * @return
	 */
	public List<TaskCustom> candidateTaskList(String candidateUserId,
			String processDefinitionKey) throws Exception {
		// 任务查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();

		taskQuery.taskCandidateUser(candidateUserId);

		if (StringUtils.isNoNullOrEmpty(processDefinitionKey)) {
			// 指定 流程定义key，只查询某个流程的任务
			taskQuery.processDefinitionKey(processDefinitionKey);
		}

		return getTaskCustomList(taskQuery.list());

	}

	/**
	 * 提交任务
	 * 
	 * @param userId
	 *            用户id
	 * @param taskId
	 *            任务id
	 * @param variables
	 *            变量
	 * @return ture成功 false　失败
	 * @throws Exception
	 */
	public boolean submitTask(String userId, String taskId,
			Map<String, Object> variables) throws Exception {

		claimTask(userId, taskId);
		// 根据任务id和assignee查询该任务
		Task task = taskService.createTaskQuery().taskId(taskId)
				.taskAssignee(userId).singleResult();

		if (task != null) {
			// 设置流程变量，值 为采购单信息
			taskService.complete(taskId, variables);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 根据候选人和组任务id查询，如果有记录说明该 候选人有资格拾取该 任务
	 * 
	 * @param candidateUserId
	 *            候选人
	 * @param taskId
	 *            任务id
	 * @throws Exception
	 */
	public void claimTask(String candidateUserId, String taskId)
			throws Exception {

		// 根据候选人和组任务id查询，如果有记录说明该 候选人有资格拾取该 任务
		Task task = taskService.createTaskQuery().taskId(taskId)
				.taskCandidateUser(candidateUserId).singleResult();

		if (task != null) {
			// 任务拾取
			taskService.claim(taskId, candidateUserId);
		}

	}

	/**
	 * 归还组任务,任务负责人将任务交给其它候选人
	 * 
	 * @param userId
	 *            任务负责人
	 * @param taskId
	 *            当前待办任务
	 */
	public void assigneeToGroupTask(String userId, String taskId) {
		assigneeToGroupTask(userId, taskId, null);
	}

	/**
	 * 任务交接,任务负责人将任务交给其它候选人办理该任务
	 * 
	 * @param userId
	 *            任务负责人
	 * @param taskId
	 *            当前待办任务
	 * @param candidateUserId
	 *            将此任务交给其它候选人办理该 任务
	 */
	public void assigneeToGroupTask(String userId, String taskId,
			String candidateUserId) {
		// 校验userId是否是taskId的负责人，如果是负责人才可以归还组任务
		Task task = taskService.createTaskQuery().taskId(taskId)
				.taskAssignee(userId).singleResult();

		if (task != null) {

			// 如果设置为null，归还组任务,该 任务没有负责人
			if (StringUtils.isNullOrEmpty(candidateUserId)) {
				taskService.setAssignee(taskId, null);
			} else {
				// 根据候选人和组任务id查询，如果有记录说明该 候选人有资格拾取该 任务
				Task task2 = taskService.createTaskQuery().taskId(taskId)
						.taskCandidateUser(candidateUserId).singleResult();
				if (task2 != null) {
					// 才可以交接
					taskService.setAssignee(taskId, candidateUserId);
				}
			}

		}
	}

	/**
	 * 查询历史 流程实例
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	public List<ProcessDefinitionInfo> queryHistoryProcessInstance(
			String processDefinitionKey) {

		// 创建历史流程实例 查询对象
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
				.createHistoricProcessInstanceQuery();
		// 设置查询条件
		// 指定流程定义key，只查询某个业务流程的实例
		// String processDefinitionKey= "purchasingflow";
		historicProcessInstanceQuery.processDefinitionKey(processDefinitionKey);

		// 设置只查询已完成的流程实例
		historicProcessInstanceQuery.finished();

		// 数据列表
		List<HistoricProcessInstance> list = historicProcessInstanceQuery
				.list();
		List<ProcessDefinitionInfo> pdiList = new ArrayList<ProcessDefinitionInfo>();
		for (HistoricProcessInstance historicProcessInstance : list) {

			ProcessDefinitionInfo pdi = new ProcessDefinitionInfo();
			pdi.setDeploymentId(historicProcessInstance
					.getProcessDefinitionId());
			pdi.setId(historicProcessInstance.getId());
			pdi.setKey(processDefinitionKey);
			pdi.setName(historicProcessInstance.getName());
			pdi.setStartTime(historicProcessInstance.getStartTime());
			pdi.setEndTime(historicProcessInstance.getEndTime());
			pdi.setDurationInMillis(historicProcessInstance
					.getDurationInMillis());
			pdi.setTenantId(historicProcessInstance.getTenantId());
			pdi.setStartUserId(historicProcessInstance.getStartUserId());
			pdi.setVariables(historicProcessInstance.getProcessVariables());

			pdiList.add(pdi);
		}
		return pdiList;
	}

	/**
	 * 查询历史 任务
	 * 
	 * @param processDefinitionKey
	 *            指定 流程定义 key，只查询该流程下所有流程实例 所有历史 任务
	 * @param processInstanceId
	 *            指定 流程实例 id,只查询该 流程实例 执行的历史 任务，流程实例 的id可以完成也可以未完成的
	 * @return
	 */
	public List<TaskCustom> queryHistoryTask(String processDefinitionKey,
			String processInstanceId) {

		// 创建查询对象，用于查询历史 任务
		HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService
				.createHistoricTaskInstanceQuery();

		// 设置查询条件
		// 设置taskAssignee只查询某个用户的历史 任务
		// historicTaskInstanceQuery.taskAssignee(taskAssignee);

		// 指定 流程定义 key，只查询该流程下所有流程实例 所有历史 任务
		// String processDefinitionKey = "purchasingflow";
		historicTaskInstanceQuery.processDefinitionKey(processDefinitionKey);

		// 指定 流程实例 id,只查询该 流程实例 执行的历史 任务，流程实例 的id可以完成也可以未完成的
		// String processInstanceId = "5201";
		historicTaskInstanceQuery.processInstanceId(processInstanceId);

		// 查询历史 任务查询流程变量
		historicTaskInstanceQuery.includeTaskLocalVariables();

		List<HistoricTaskInstance> list = historicTaskInstanceQuery.list();

		List<TaskCustom> taskList = new ArrayList<TaskCustom>();

		for (HistoricTaskInstance task : list) {

			TaskCustom taskCustom = new TaskCustom();

			// 任务id
			taskCustom.setTaskId(task.getId());
			// 任务标识
			taskCustom.setTaskDefinitionKey(task.getTaskDefinitionKey());
			// 任务名称
			taskCustom.setTaskName(task.getName());
			// 任务负责人
			taskCustom.setAssignee(task.getAssignee());

			taskCustom.setTask_startTime(task.getStartTime());
			taskCustom.setTask_endTime(task.getEndTime());
			taskCustom.setProcessInstanceId(task.getProcessInstanceId());

			taskList.add(taskCustom);
		}

		return taskList;

	}

}
