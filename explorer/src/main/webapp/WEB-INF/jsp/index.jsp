<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>调试工具</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<form id="form">

		<table align="center">
			<tr >
				<td nowrap="nowrap" colspan="3" align="center" >
					<h3>
						<a href="#" onclick="clikJSON();">JSON在线校验</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" onclick="clikJSON2();">JSON在线压缩/转义</a>
					</h3>
				</td>
			</tr>
			<tr >
			<td nowrap="nowrap" colspan="3" align="center" >
<!-- 						appId：<input type="text" id="appId" name="appId" value="ipi"> -->
<!-- 						appkey: <input type="text" id="appkey"  name="appkey" value="4b79cea82e6ae26d968bedf70d29c3a23a246f05asd"> -->
<!-- 						token：<input type="text" id="token"  name="token" value="63b695caf8a2838abde06237ee8e3d06"><br><br> -->
						
<!-- 						appId：<input type="text" id="appId" name="appId" value="cg"> -->
<!-- 						appkey: <input type="text" id="appkey"  name="appkey" value="c65d8a45714b18caa8a345469c77ae85403b8253asd"> -->
<!-- 						token：<input type="text" id="token"  name="token" value="78b269619f0723e8037a282f7fe8364b"><br><br> -->

						appId：<input type="text" id="appId" name="appId" value="lj">
						appkey: <input type="text" id="appkey"  name="appkey" value="97b5546f1418150242544bd479d3f067cde1b886asd">
						token：<input type="text" id="token"  name="token" value="21e63386cf9d20fc4a4aa050a190f62a"><br><br>
						
						调用接口：<input type="text"
					id="actionName" name="actionName" placeholder="如：getCustByUserId"
					value="/getCustByUserId"> <input type="button" value="发送请求"
					style="width: 100px;" id="fsbutn">
				</td>
			</tr>

			<tr><td>
				<textarea rows="50" cols="90"
						placeholder="请输入JSON参数，如{'cod_cust_userid':'tujun'}"
						id="jsonString" name="jsonString">{"cod_cust_userid":"tujun"}</textarea>
				</td>
				<td><textarea rows="50" cols="90" placeholder="返回结果"
						id="resultText"></textarea></td>
			</tr>
		</table>
	</form>
	<div id="divtext"></div>
</body>
<script type="text/javascript">
	$("#fsbutn").click(function() {
		$('#resultText').val("");
		$('#divtext').html("");
		var jsonString = $('#jsonString').val();
		var actionName = $('#actionName').val();
		if (jsonString == "") {
			// 			alert("请输入JSON参数");
			// 			$('#jsonString').focus();
			// 			return false;
		}
		if (actionName == "") {
			alert("请输入调用接口");
			$('#actionName').focus();
			return false;
		}
		var jsonuserinfo = $('#form').serializeObject();
		//alert(JSON.stringify(jsonuserinfo));
		$.ajax({
			type : "POST",
			url : "<%=basePath%>debugrequest",
			data : jsonuserinfo,
			contentType : "application/x-www-form-urlencoded",
			dataType : "json",
			success : function(msg) {
				var ts = JSON.stringify(msg);
				$('#resultText').val(FromJsonString(ts));
			},
			error : function(msg) {
				$('#divtext').html(msg.responseText);
			}
		});
	});

	//将一个表单的数据返回成JSON对象     
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	//格式化JSON
	function FromJsonString(jsonStr) {
		var res = "";
		for (var i = 0, j = 0, k = 0, ii, ele; i < jsonStr.length; i++) {//k:缩进，j:""个数
			ele = jsonStr.charAt(i);
			if (j % 2 == 0 && ele == "}") {
				k--;
				for (ii = 0; ii < k; ii++)
					ele = "    " + ele;
				ele = "\n" + ele;
			} else if (j % 2 == 0 && ele == "{") {
				ele += "\n";
				k++;
				for (ii = 0; ii < k; ii++)
					ele += "    ";
			} else if (j % 2 == 0 && ele == ",") {
				ele += "\n";
				for (ii = 0; ii < k; ii++)
					ele += "    ";
			} else if (ele == "\"")
				j++;
			res += ele;
		}
		return res;
	};
	
	function clikJSON(){
		window.open("http://tool.oschina.net/codeformat/json");
	}
	
	function clikJSON2(){
		window.open("http://www.bejson.com/go.html?u=http://www.bejson.com/zhuanyi.php");
	}
	
</script>
</html>