<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysRoleAdd_dialog" class="easyui-dialog" title="添加角色" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysRoleAdd_form" method="post"> 
       <table>
          <tr>
             <th> 名称：</th>
             <td> 
                <input class="easyui-textbox" name="roleName" data-options="iconCls:'icon-man',required:true" 
                style="width:200px;height:30px;">
             </td>
          </tr>
          <tr>
             <th>菜单权限：</th>
             <td>
               <ul id="roleMenu" ></ul> 
             </td>
          </tr>
  
          <tr>
             <th>操作权限:</th>
             <td>
               <input id="roleOpertions">		
             </td>
          </tr>
          <tr>
             <th>状态:</th>
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
															}]" name="roleStatus"/>
             </td>
          </tr>
       	 <tr>
       	    <th>备注：</th>
   			<td>
   			   <script id="roleAddRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
			   <input type="hidden" name="roleRemark" id="roleAddRemarkInput"/>
				<!-- <input name="roleRemark" class="easyui-textbox"
				 data-options="iconCls:'icon-search',multiline:'true' ,width:'150',height:'60'" >  -->		
			</td>
   		</tr>
       </table>
    </form> 
   <div id="bb" style="padding:10px 50px 0;" >
	<a id="sysRoleAdd"  class="easyui-linkbutton" >保存</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysRoleAdd_dialog')">关闭</a>
   </div>
</div>
    

