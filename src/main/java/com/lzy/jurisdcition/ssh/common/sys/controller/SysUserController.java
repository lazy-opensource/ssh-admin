package com.lzy.jurisdcition.ssh.common.sys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzy.jurisdcition.ssh.common.base.controller.BaseController;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.util.MyJson;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

@Controller
@RequestMapping("/user")
public class SysUserController extends BaseController{
	

	private Logger log = Logger.getLogger(SysUserController.class);

	/**
	 * 添加
	 * @param sysUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add" ,  produces = "application/json",  method = RequestMethod.POST)
	public MyJson add(SysUser sysUser,String[] roles){
		 MyJson json = new MyJson();
		try{
			Set<SysRole> sysRoles = new HashSet<SysRole>();
			if(roles != null){
				for(int a=0;a<roles.length;a++){
					SysRole role = new SysRole();
					role.setRoleId(Integer.valueOf(roles[a]));
					sysRoles.add(role);
				}
				sysUser.setSysRoles(sysRoles);
			}
		    sysUserService.save(sysUser);
		    json.setMsg("添加成功");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用user/add出错了>>>>>>>>>>"+"\n"+e.getMessage());
			throw e;
		}
		
	    return json;
	}
	/**
	 * 批量/单条删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{ids}" , produces = "application/json", method = RequestMethod.POST)
	public MyJson delete(@PathVariable(value = "ids") String ids){
		MyJson json = new MyJson();
		try{
			String[] userIds = ids.split(",");
//            if(userIds != null && userIds.length > 0){
//                for(int id =0; id <userIds.length; id++){
//                    if("1".equals(userIds[id])){
//                        json.setMsg("没有删除超级用户的权限!");
//                        return json;
//                    }
//                }
//            }
			Integer rows = sysUserService.delete(userIds);
		   
		    json.setMsg("成功删除"+rows+"条数据");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用user/delete出错了>>>>>>>>>>");
			throw e;
		}
	    return json;
	}
	
	/**
	 * 修改
	 * @param sysUser
	 * @param roles
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update" ,  produces = "application/json",  method = RequestMethod.POST)
	public MyJson update(SysUser sysUser,String[] roles){
		MyJson json = new MyJson();
		try{
			Set<SysRole> sysRoles = new HashSet<SysRole>();
			for(int a=0;a<roles.length;a++){
				SysRole role = new SysRole();
				role.setRoleId(Integer.valueOf(roles[a]));
				sysRoles.add(role);
			}

            SysUser oldUser = sysUserService.get(sysUser.getUserId());
            oldUser.setSysRoles(sysRoles);
            oldUser.setUserName(sysUser.getUserName());
            oldUser.setUserPassword(sysUser.getUserPassword());
            oldUser.setUserRealName(sysUser.getUserRealName());
            oldUser.setUserStatus(sysUser.getUserStatus());
            oldUser.setUserRemark(sysUser.getUserRealName());
		    sysUserService.update(oldUser);
		    
		    json.setMsg("修改成功");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用user/update出错了>>>>>>>>>>");
			throw e;
		}
	    return json;
	}
	
	/**
	 * 跳转到列表
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/toList" , method = RequestMethod.GET)
	public String toList(@RequestParam(value = "menuId") Integer menuId){
		super.setmId(menuId);
		return "list/sys_user";
	}
	
	/**
	 * 用户列表 json
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list" , produces = "application/json",  method = RequestMethod.POST)
	public PageBean<SysUser> list(String rows,String page){
		PageBean<SysUser> pagin = new PageBean<SysUser>();
		try{
			
			pagin.setRowsPerPage(rows != null ?Integer.valueOf(rows) : 10);//每页多少行
			pagin.setPageNo(page != null ? Integer.valueOf(page) : 1);//当前第几页  	
			sysUserService.findByPage(pagin, null);
			List<SysUser> list = pagin.getData();
			pagin.setRows(list);//返回的数据
			pagin.setTotal(pagin.getRowsCount());//总条数
			pagin.setPage(((Integer)(pagin.getPageNo())).toString());//当前第几页 
		}catch(RuntimeException e){
			log.info("调用user/list出错了>>>>>>>>>>");
			throw e;
		}
		return pagin;
	}
	
	/**
	 * 获得操作列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/operations" , produces = "application/json" ,method = RequestMethod.GET)
	public MyJson getOperation(HttpServletRequest request ){
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		List<SysOperation> operationList = new ArrayList<SysOperation>();
		Set<SysOperation> operationSet = new HashSet<SysOperation>();
		MyJson json = new MyJson();
		Set<SysRole> roles = sysUser.getSysRoles();//角色
		for(SysRole role : roles){//遍历用户的所有角色
			Set<SysOperation> operations = role.getSysOperations();
			for(SysOperation oper : operations){
				operationSet.add(oper);
			}
		}
		for(SysOperation operation : operationSet){//遍历用户的所有角色
			operationList.add(operation);
		}
		super.sortOperation(operationList);
        json.setData(operationList);
        json.setStatusCode(super.getmId());
		return json;
	}

    @RequestMapping(value = "/export" , method = RequestMethod.GET)
    public void export(/*@RequestParam(value = "page") String page,
                       @RequestParam(value = "rows") String rows*/
                       HttpServletResponse response){
        PageBean<SysUser> pagin = new PageBean();
        pagin.setRowsPerPage(10);//每页多少行
        pagin.setPageNo(1);//当前第几页
        sysUserService.findByPage(pagin, null);
        List<SysUser> users = pagin.getData();

        //String basePath = "F:"+ File.separator +"excl" + File.separator;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(new Date());
        //String basePath = System.getProperties().getProperty("user.dir")+ File.separator +"Excl" + File.separator;
        String tableName = "用户列表"+time;

        HSSFWorkbook workbook = null; //生成2003 Excel
        OutputStream outStream = null;
        try{
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0,"用户信息");
            sheet.autoSizeColumn(1 ,true);
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFFont.COLOR_NORMAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFCellStyle cellStyle = workbook.createCellStyle();//格式
            cellStyle.setFont(font);
            cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
            int cellCount = 0;
            HSSFRow titleRow = sheet.createRow(0);//第一行标题
            HSSFCell cell = null;
            String titles[] = {"用户ID" , "用户姓名" ,"用户真实姓名" , "用户密码" , "用户状态" , "所属角色"};
            for(int z = 0; z < titles.length; z++){
                cell = titleRow.createCell(cellCount++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(titles[z]);
                sheet.setColumnWidth(z , 3000);
            }

            for(int g =0; g < users.size(); g++){
                SysUser u = users.get(g);
                HSSFRow r = sheet.createRow(g+1);
                HSSFCell c = null;
                for(int z = 0; z < titles.length; z++){
                    c = r.createCell(z);
                    c.setCellStyle(cellStyle);
                    switch(z){
                        case 0:
                            c.setCellValue(u.getUserId());
                            break;
                        case 1:
                            c.setCellValue(u.getUserName());
                            break;
                        case 2:
                            c.setCellValue(u.getUserRealName());
                            break;
                        case 3:
                            c.setCellValue(u.getUserPassword());
                            break;
                        case 4:
                            c.setCellValue(u.getUserStatus());
                            break;
                        case 5:
                            Set<SysRole> roles = u.getSysRoles();
                            String rs = "";
                            if(roles != null){
                                for(SysRole role : roles){
                                    rs += role.getRoleName()+",";
                                }
                                rs = rs.substring(0 ,rs.length() - 1);
                            }
                            c.setCellValue(rs);
                            break;
                    }
                }
            }
         /* File file = new File(basePath+tableName);
            FileOutputStream outStream = new FileOutputStream(file);
            workbook.write(outStream);
            outStream.flush();
            outStream.close();
            log.info("Excel 2003文件导出完成!导出路径"+basePath+tableName);*/
            // 表示以附件的形式把文件发送到客户端
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((tableName+".xls").getBytes(), "ISO-8859-1"));//设定输出文件头
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
            // 通过response的输出流把工作薄的流发送浏览器形成文件
            outStream = response.getOutputStream();
            workbook.write(outStream);
            outStream.flush();
        }catch(Exception e){
            log.info("excl 导出出错了.....");
            e.printStackTrace();
        }finally {
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStream !=  null){
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
