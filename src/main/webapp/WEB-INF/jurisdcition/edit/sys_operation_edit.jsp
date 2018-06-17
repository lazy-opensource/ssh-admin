<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysOperationEdit_dialog" class="easyui-dialog" title="修改角色" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysOperationEdit_form" method="post"> 
       <table>
           <input type="hidden" name="operationId"/>
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
                <ul id="operationMenu" ></ul> 
             </td>
          </tr>
  
          <tr>
             <th>操作路径:</th>
             <td>
              <input class="easyui-textbox" name="operationAction" data-options="iconCls:'icon-man',required:true" 
                style="width:200px;height:30px;">
             </td>
          </tr>
       
   		<tr>
   			<td>备注:</td>
   			<td>
   			   <script id="operationEditRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
				<input id="operationEditRemarkInput" name="operationRemark" type="hidden" > 	
				<!-- <input name="roleRemark" class="easyui-textbox"
				 data-options="iconCls:'icon-search',multiline:'true' ,width:'150',height:'60'" > 			 -->
			</td>
   		</tr>
       </table>
    </form> 
   <div id="operationLinkButton" style="padding:10px 80px 0;">
	<a id="sysOperationEdit"  class="easyui-linkbutton" >修改</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysOperationEdit_dialog')">关闭</a>
   </div>
</div>
    

