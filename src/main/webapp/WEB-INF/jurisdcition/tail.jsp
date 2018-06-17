<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	  <div data-options="region:'east',title:'East',split:true" style="width:100px;"></div>
	  <div data-options="region:'south',split:false" style="height:30px;line-height: 30px;text-align: center;">
		  
	 </div>

	<div id="tabsMenu" class="easyui-menu" style="width:120px;">
		   <div name="close"   data-options="iconCls:'icon-close'">关闭标签</div>  
		   <div name="other"   data-options="">关闭其他标签</div>  
		   <div name="all"  data-options="">关闭所有标签</div>
		   <div class="menu-sep"></div>
	       <div name="closeRight">关闭右侧标签</div>
	       <div name="closeLeft">关闭左侧标签</div>
	       <div class="menu-sep"></div>
	      <div name="refresh"  data-options="iconCls:'icon-reload'">刷新标签</div> 
	</div>

	<div id="dialogChangePwd" class="easyui-dialog" title="密码修改"
		style="width:400px;height:200px;"
		data-options="iconCls:'icon-edit',resizable:true,modal:true,closed:true">

		<form id="formChangePwd" action="${pageContext.request.contextPath}/login/updatePwd" method="get"
			class="easyui-form" data-options="novalidate:true">
			<table height="140" align="center">
				<tr>
					<td>Password:</td>
					<td><input id="upwd" name="sysUser.password" class="easyui-textbox"
						type="password"
						data-options="required:true,validType:'minLength[3]'"></input> <%-- 					<input  id="upwd" name="upwd" class="easyui-validatebox" type="password" data-options="required:true,validType:'minLength[6]'"></input> --%>
					</td>
				</tr>
				<tr>
					<td>New password:</td>
					<td><input id="newpwd" name="newPwd" class="easyui-textbox"
						type="password"
						data-options="required:true,validType:'minLength[3]'"></input></td>
				</tr>
				<tr>
					<td>Confirm new password:</td>
					<td><input id="renewpwd" name="confirmPwd"
						class="easyui-textbox" type="password"
						data-options="required:true,validType:['minLength[3]','equals[\'#newpwd\',\'与新密码不一致.\']']"></input></td>
				</tr>
				<tr>
					<td>
                        <input type="submit" />&nbsp;&nbsp;
                        <input type="reset" />
                    </td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>