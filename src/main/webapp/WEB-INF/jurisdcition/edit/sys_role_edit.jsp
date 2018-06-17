<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysRoleEdit_dialog" class="easyui-dialog" title="修改角色" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysRoleEdit_form" method="post"> 
       <table>
           <tr><td><input type="hidden" name="roleId" /></td></tr>
          <tr>
             <th> 角色名称：</th>
             <td> 
                <input class="easyui-textbox" name="roleName" data-options="iconCls:'icon-man',required:true" 
                style="width:200px;height:30px;">

             </td>
          </tr>
          <tr>
             <th>菜单权限：</th>
             <td>
                <ul id="editRoleMenu" ></ul> 
             </td>
          </tr>
  
          <tr>
             <th>操作权限:</th>
             <td>
              <select id="editRoleOpertions" name="editOperations" multiple></select>
             </td>
          </tr>
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
															}]" name="roleStatus" id="roleStatus"/>		
			</td>
   		</tr>
   		<tr>
   			<td>备注:</td>
   			<td>
   			   <script id="roleEditRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
				<input id="roleEditRemarkInput" name="roleRemark" type="hidden" > 	
				<!-- <input name="roleRemark" class="easyui-textbox"
				 data-options="iconCls:'icon-search',multiline:'true' ,width:'150',height:'60'" > 			 -->
			</td>
   		</tr>
       </table>
    </form> 
   <div id="roleLinkButton" style="padding:10px 80px 0;">
	<a id="sysRoleEdit"  class="easyui-linkbutton" >修改</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysRoleEdit_dialog')">关闭</a>
   </div>
</div>
    

