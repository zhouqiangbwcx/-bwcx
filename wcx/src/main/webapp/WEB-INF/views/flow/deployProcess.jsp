<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

<script type="text/javascript">

</script>
</head>
<body>
<div>
<form id="deployProcess" name="deployProcess" 
action="${ctx}/system/flow/deployProcessSubmit" method="post"
 enctype="multipart/form-data">
<table  class="formTable">
		<thead>
			<tr>
				<th field="name1" >流程定义部署</th>
				<th field="name2" >&nbsp;</th>
			</tr>                          
		</thead>                           
		<tbody>                            
			<tr>                           
				<td>流程定义部署说明：</td>
								<td >
								请在下边分别选择流程定义bpmn文件和png文件
								</td>         
				           
			</tr>                          
	          	<tr>                           
				<td >选择流程定义bpmn文件</td>
								<td >
								<input type="file" name="resource_bpmn" />					
								</td>           
				            
				           
			</tr> 
				<tr>                           
					<td >选择流程定义png文件</td>
								<td >
								<input type="file" name="resource_png" />					
								</td>          
				           
			</tr> 
		</tbody>                           
	</table>
	
</form>
</div>
<script type="text/javascript">
$(function(){
 	$('#deployProcess').form({    
	    onSubmit: function(){    
	    	//var isValid = $(this).form('validate');
	    	//console.log(isValid);
			//return isValid;	// 返回false终止表单提交
	    	return true;
	    },    
	    success:function(data){   
	    	if(successTip(data,dg,d))
	    		dg.treegrid('reload');
	    }    
	});   
});

</script>
</body>

</html>

