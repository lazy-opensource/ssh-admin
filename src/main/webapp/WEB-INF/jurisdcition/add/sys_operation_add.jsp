<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysOperationAdd_dialog" class="easyui-dialog" title="添加操作" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysOperationAdd_form" method="post"> 
       <table>
          <tr>
             <th>操作名称：</th>
             <td> 
                <input class="easyui-textbox" name="operationName" data-options="iconCls:'icon-man',required:true" 
                style="width:200px;height:30px;">
             </td>
          </tr>
          <tr>
             <th>所属菜单：</th>
             <td>
               <ul id="operationMenus" ></ul> 
             </td>
          </tr>
  
          <tr>
             <th>路径:</th>
             <td>
               <input class="easyui-textbox" name="operationAction"
               data-options="iconCls:'icon-lock',required:true" style="width:200px;height:30px;">
             </td>
          </tr>
   		<tr>
   			<td>备注:</td>
   			<td>
			    <script id="operationAddRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
			    <input type="hidden" name="operationRemark" id="operationRemarkInput"/>
				<!-- <input name="userRemark" class="easyui-textbox"
				 data-options="iconCls:'icon-search',multiline:'true' ,width:'150',height:'60'" > -->
			</td>
   		</tr>
       </table>

    </form> 

   <div id="operationAdd" style="padding:10px 80px 0;">
	<a id="sysOperationAdd"  class="easyui-linkbutton" >保存</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysOperationAdd_dialog')">关闭</a>
   </div>
</div>
    

