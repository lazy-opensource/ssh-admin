<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysMenuAdd_dialog" class="easyui-dialog" title="添加菜单" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysMenuAdd_form" method="post"> 
       <table>
          <tr>
             <th>菜单名称：</th>
             <td> 
                <input class="easyui-textbox" name="menuName" data-options="iconCls:'icon-man',required:true" 
                style="width:200px;height:30px;">
             </td>
          </tr>
          <tr>
             <th>父菜单：</th>
             <td>
               <ul id="menuParents" ></ul> 
             </td>
          </tr>
  
          <tr>
             <th>路径:</th>
             <td>
               <input class="easyui-textbox" name="menuAction"
               data-options="iconCls:'icon-lock'" style="width:200px;height:30px;">
             </td>
          </tr>
           <tr>
             <th>排序:</th>
             <td>
               <input class="easyui-textbox" name="menuSortOrder"
               data-options="iconCls:'icon-lock' ,required:true" style="width:200px;height:30px;">
             </td>
          </tr>
          <tr>
             <th>小标图:</th>
             <td>
               <input class="easyui-textbox" name="menuIcon"
               data-options="iconCls:'icon-lock'" style="width:200px;height:30px;">
             </td>
          </tr>
   		<tr>
   			<td>备注:</td>
   			<td>
			    <script id="menuAddRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
			    <input type="hidden" name="menuRemark" id="menuRemarkInput"/>
				<!-- <input name="userRemark" class="easyui-textbox"
				 data-options="iconCls:'icon-search',multiline:'true' ,width:'150',height:'60'" > -->
			</td>
   		</tr>
       </table>

    </form> 

   <div id="menuAdd" style="padding:10px 80px 0;">
	<a id="sysMenuAdd"  class="easyui-linkbutton" >保存</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysMenuAdd_dialog')">关闭</a>
   </div>
</div>
    

