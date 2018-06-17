<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sysMenuEdit_dialog" class="easyui-dialog" title="修改菜单" style="width:500px;height:380px;padding:20px 50px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   

    <form id="sysMenuEdit_form" method="post"> 
       <table>
           <tr><td><input type="hidden" name="menuId"/></td></tr>
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
                <ul id="menuEditParent" ></ul> 
             </td>
          </tr>
  
<%--          <tr>
             <th>路径:</th>
             <td>
              <input class="easyui-textbox" name="menuAction" data-options="iconCls:'icon-man'" 
                style="width:200px;height:30px;">
             </td>
          </tr>--%>
          <tr>
             <th>排序:</th>
             <td>
              <input class="easyui-textbox" name="menuSortOrder" data-options="iconCls:'icon-man' ,required:true"
                style="width:200px;height:30px;">
             </td>
          </tr>
           <tr>
             <th>小标图:</th>
             <td>
              <input class="easyui-textbox" name="menuIcon" data-options="iconCls:'icon-man'" 
                style="width:200px;height:30px;">
             </td>
          </tr>
           <tr>
               <td>备注:</td>
               <td>
                   <script id="menuEditRemark" name="content" type="text/plain" style="width:200px;height:100px;"></script>
                   <input type="hidden" id="menuEditRemarkInput" name="menuRemark"/>
               </td>
           </tr>
       </table>
    </form> 
   <div id="menuLinkButton" style="padding:10px 80px 0;">
	<a id="sysMenuEdit"  class="easyui-linkbutton" >修改</a>
	<a  class="easyui-linkbutton" onclick="javascript:uiEx.closeDialog('#sysMenuEdit_dialog')">关闭</a>
   </div>
</div>
    

