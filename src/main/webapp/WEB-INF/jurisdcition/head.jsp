<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>JavaEE-SSH框架通用权限系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/style/easyssh.main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/default/easyui.css" id="themeLink">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/portal.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/datagrid-dnd.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/datagrid-detailview.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.portal.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/treegrid-dnd.js"></script>
	<link rel="stylesheet" type="text/css" href="easyuiex/css/easyuiex.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/easy.easyuiex-2.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/easy.easyuiex-validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/easy.jquery.edatagrid.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyuiex/lang/easy.easyuiex-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/default.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/blue.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/dark.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/gray.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/green.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/helianthus.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/infographic.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/macarons.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/red.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/themes/shine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/echarts-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyssh/easyssh.main.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/main.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyssh/jquery.cookie.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/utf8-jsp/ueditor.config.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/utf8-jsp/ueditor.all.js"></script>
	<script type="text/javascript">
	var EasySSH={
			basePath:'<%=basePath%>'
		}
	</script>
</head>

<body class="easyui-layout" >

		<div id="easysshtop" data-options="region:'north',split:false,collapsible:true" 
		style="height:75px;padding: 0; background-image: url('${pageContext.request.contextPath}/images/banner_bg.jpg');" >
	 	<%-- <img alt="" src="${pageContext.request.contextPath}/images/banner.jpg"/>   --%>
			<div style="float: right; padding-top: 20px;line-height: 30px;text-align: right;"><!-- background-image: url('images/banner_bg.jpg'); --> 
				<%request.setAttribute("now", new java.util.Date());%>
			          欢迎 <span style="font-weight:bold">${sessionScope.user.userRealName }</span> 
			     <span id="showtime"></span>
				 <input id="themeCombobox" class="easyui-combobox" style="width: 120px;"
				data-options="hasDownArrow:false,icons:[{'iconCls':'icon-palette'}]" /> 
				<%--<a id="btnChangePwd" class="easyui-linkbutton"
				data-options="iconCls:'icon-lock_edit'">修改密码</a>--%>
				<a id="btnExit" onclick="uiEx.confirm('退出系统?', function(res){if(res){window.location.href='${pageConetxt.request.contextPath}/login/logout';}}); " class="easyui-linkbutton"
				data-options="iconCls:'icon-monitor_go'">退出系统</a>
			<%--	<a id="btnLoginRefresh"
				href="reLogin.action" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'">重登系统</a>--%>
			</div>
		</div>
		<div data-options="region:'west',title:'功能菜单',split:true" style="width:180px;">
			<div style="margin:10px 0;"></div>
			<ul id="menu" ></ul> 
 		</div>


		</div>