package com.see.wcx.system.entity;

import java.util.Date;
import java.util.Map;

public class ProcessDefinitionInfo {
	

	  private String deploymentId;//流程部署id    
      private String id;//流程定义id
      private String name;//流程定义名称
      private String key;//流程定义key
      private int version;//流程定义版本
      private Date startTime;//开始时间
      private Date endTime;//开始时间
      private Long durationInMillis;//开始时间
      private String TenantId;
      private String StartUserId;//开始人
      
  	//全局变量
  	private Map<String, Object>  variables;
  	
  	
      
      
	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	public String getStartUserId() {
		return StartUserId;
	}
	public void setStartUserId(String startUserId) {
		StartUserId = startUserId;
	}
	public String getTenantId() {
		return TenantId;
	}
	public void setTenantId(String tenantId) {
		TenantId = tenantId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getDurationInMillis() {
		return durationInMillis;
	}
	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
      
      
}
