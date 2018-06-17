<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath }">
<title>Permissions System</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- EasyUI CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/default/easyui.css" id="themeLink">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyuiex/css/easyuiex.css">
<!-- 全局变量 -->
<script type="text/javascript"> var EasySSH={basePath:'${pageContext.request.contextPath}'}</script>
<!-- EasyUI JS -->
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/easy.easyuiex.min-2.0.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/easy.easyuiex-validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/lang/easy.easyuiex-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/login.js"></script>

<!-- 登录消息提示JS -->
<c:if test="${MSG != null }">
	<script type="text/javascript">
		$(function() {
			uiEx.alert("${MSG }", "info");
		})
			var EasySSH={
			basePath:'<%=basePath%>'
		}
	</script>
</c:if>
    <script type="text/javascript">
        var EasySSH={
            basePath:'<%=basePath%>'
        }

    </script>
</head>
<body>
	<div style="text-align: center;overflow:auto;width:100%;height:100%;margin: 10px auto;">
		<img alt="通用权限系统 " src="${pageContext.request.contextPath }/images/spdlogo.png">
		<div style="margin: 0 auto;width: 564px;">   
			<div class="easyui-panel"  style="width: 564px;">
				<div style="padding:10px 60px 20px 60px;">
					<form id="loginForm" class="easyui-form" method="post"
						data-options="novalidate:true" action="${pageContext.request.contextPath }/login/login">
						<table cellpadding="5" style="">
							<tr>
								<td width="90">Theme:</td>
								<td>
								<div style="margin: 10px auto;">
			 <input id="themeCombobox" class="easyui-combobox"
				data-options="" />
		</div>
								</td>

							</tr>
							<tr>
								<td width="90">用户名:</td>
								<td><input class="easyui-textbox" type="text"
									name="username" id="username" style="height:30px;width: 220px;"
									data-options="validType:[],required:true,prompt:'user name...'" value="admin"></input></td>
								<!-- 								<td><input class="easyui-textbox" type="text" name="uname" style="height:30px;width: 180px;"
									data-options="validType:['email','startChk[\'A\']'],required:true"></input></td> -->
							</tr>
							<tr>
								<td>密 码:</td>
								<td><input class="easyui-textbox" type="password"
									name="password" style="height:30px;width: 220px;"
									data-options="required:true" value="123"></input></td>
							</tr>
							<tr>
								<td>验证码:</td> 
								<td><input class="easyui-validatebox textbox"
									       id="verifyCode" name="verifycode"   
									       style="height:30px;width: 100px;" 
									       data-options="required:true, validType:'minLength[4]' , tipPosition:'right',position:'bottom', deltaX:135"
									        maxlength="4"></input> 
									<div style="display: none; float: right; border: 1px solid #ccc;" id="vcTr">
										<img  title="点击切换" alt="加载中..." align="middle"
											style="cursor: pointer;" width="100" height="28" id="vcImg" src="${pageContext.request.contextPath }/jsp/VerifyCode.jsp">
									</div></td>
							</tr>
						</table>
					</form>
					<div style="text-align:center;padding:5px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							id="loginBtn" iconCls="icon-man">登录</a> <a
							href="javascript:void(0)" iconCls="icon-clear"
							class="easyui-linkbutton" onclick="uiEx.clearForm('#loginForm')">重置</a>
					</div>

				</div>
			</div>

		</div>
		
		<div class="footer">
			<p>
				© LZY-CSM  
			</p>
			<p>
				SSH框架通用权限系统@
			</p>
			</div>

	</div>

</body>
</html>
