<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>

<body style="font-family: '微软雅黑'">
<div id="tb" style="padding:5px;height:auto">
    <div>
    	<shiro:hasPermission name="sys:flow:add">
    	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="add();">添加</a>
    	<span class="toolbar-item dialog-tool-separator"></span>
    	</shiro:hasPermission>
    	<shiro:hasPermission name="sys:flow:delete">
	      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	      <span class="toolbar-item dialog-tool-separator"></span>
	    </shiro:hasPermission>
   
    </div>
</div>
<table id="dg"></table> 
<div id="dlg"></div>

<script type="text/javascript">
var dg;
$(function(){   
	
	dg=$('#dg').treegrid({  
	method: "get",
    url:'${ctx}/system/flow/queryProcessDefinitionJson', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'deploymentId',
	treeField:'name',
	parentField : 'deploymentId',
	animate:true, 
	rownumbers:true,
	singleSelect:true,
	striped:true,
    columns:[[    
        {field:'deploymentId',title:'流程部署id',width:100},    
        {field:'id',title:'流程定义id',width:100},
        {field:'name',title:'流程定义名称',width:100},
        {field:'key',title:'流程定义key',width:100},
        {field:'version',title:'流程定义版本',width:100},
        {field:'bpmn',title:'bpmn',width:100,
        	formatter: function(value,row,index){
				return '<a href="${ctx}/system/flow/queryProcessDefinitionResource?processDefinitionId='+row.id+'&resourceType=bpmn" target="_blank">查看bpmn</a>';
			
		}},
        {field:'图片',title:'图片',width:100,
        	formatter: function(value,row,index){
        		
					return '<a href="${ctx}/system/flow/queryProcessDefinitionResource?processDefinitionId='+row.id+'&resourceType=png" target="_blank">查看图片</a>';
				
			}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tb',
    dataPlain: true
	});
	
});




//弹窗增加
function add() {
	//父级权限
	var row = dg.treegrid('getSelected');
	if(row){
		parentPermId=row.id;
	}
	
	d=$('#dlg').dialog({    
	    title: '添加菜单',    
	    width: 450,    
	    height: 320,    
	    closed: false,    
	    cache: false,
	    maximizable:true,
	    resizable:true,
	    href:'${ctx}/system/flow/deployProcess',
	    modal: true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#deployProcess").submit();
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '您确认删除该流程定义吗？', function(data){
		if (data){
			 $.ajax({
				type:'get',
				url:"${ctx}/system/flow/deleteProcessDefinition?processDefinitionId="+row.id,
				success: function(data){
					if(successTip(data,dg))
			    		dg.treegrid('reload');
				}
			});
		}

	});
}


</script>
</body>
</html>