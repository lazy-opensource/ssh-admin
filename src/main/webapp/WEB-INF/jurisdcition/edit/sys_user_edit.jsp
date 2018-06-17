<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysUserEdit_dialog" class="easyui-dialog" title="修改用户" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysUserEdit_form" method="post"> 
       <table>
          <tr><td><input type="hidden" name="userId"/></td></tr>
          <tr>
             <th> 姓名：</th>
             <td> 
                <input class="easyui-textbox" name="userName" data-options="iconCls:'icon-man',required:true" 
                style="width:200px;height:30px;">

             </td>
          </tr>
          <tr>
             <th>密码：</th>
             <td>
                <input class="easyui-textbox" name="userPassword"
                data-options="iconCls:'icon-search',required:true" style="width:200px;height:30px;">
             </td>
          </tr><tr>
           <tr>
               <td>状态:</td>
               <td>
                   <input class="easyui-combobox" data-options=" required:true,
                                                            editable:false,
															valueField: 'value',
															textField: 'label',
															panelHeight:'50',
															panelWidth:'100',
															width:'100',
															data: [{
																label: '启用',
																value: '0'
															},{
																label: '禁用',
																value: '1'
															}]" name="userStatus" id="userStatus"/>
               </td>
           </tr>
       </tr>
          <tr>
             <th>  真实姓名:</th>
             <td>
               <input class="easyui-textbox" name="userRealName"
               data-options="iconCls:'icon-lock'" style="width:200px;height:30px;">
             </td>
          </tr>
       	 <tr>
   			<td>角色:</td>
   			<td>
				<select id="edit" name="roleIds" multiple></select>			
			</td>
   		</tr>
   		<tr>
       	    <th>备注：</th>
   			<td>
   			    <script id="userEditRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
				<input id="userEditRemarkInput" name="userRemark" type="hidden" >
			</td>
   		</tr>
       </table>
    </form> 
   <div id="bb" style="padding:10px 80px 0;">
	<a id="sysUserEdit"  class="easyui-linkbutton" >修改</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysUserEdit_dialog')">关闭</a>
   </div>
</div>
    

