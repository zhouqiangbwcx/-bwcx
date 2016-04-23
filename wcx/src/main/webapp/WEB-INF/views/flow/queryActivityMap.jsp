<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<!-- 流程图 -->
	<img style="position: absolute; top: 0px; left: 0px;"
		src="${ctx}/system/flow/queryProcessDefinitionResource?processDefinitionId=${processDefinitionId}&resourceType=png">


	<!-- 流程图中当前活动框 -->
	<div
		style="position: absolute;border:1px solid red;width: ${activity_width }px;height:${activity_height }px;top:${activity_y }px;left: ${activity_x }px;"></div>

</body>

</html>

