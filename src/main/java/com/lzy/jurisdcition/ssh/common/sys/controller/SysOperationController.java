package com.lzy.jurisdcition.ssh.common.sys.controller;

import com.lzy.jurisdcition.ssh.common.base.controller.BaseController;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.util.MyJson;
import com.lzy.jurisdcition.ssh.common.util.PageBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/operation")
@Controller
public class SysOperationController extends BaseController{
	
	
	private Logger log = Logger.getLogger(SysOperationController.class);
	/**
	 * 添加
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add" ,  produces = "application/json",  method = RequestMethod.POST)
	public MyJson add(SysOperation sysOperation , String menuIds){
		MyJson json = new MyJson();
		try{
			String ids[] = menuIds.split(",");
			if(ids != null){
				for(int i =0;i<ids.length;i++){
			    	SysMenu m = new SysMenu();
                    SysOperation sub = new SysOperation();
			    	m.setMenuId(Integer.valueOf(ids[i]));
			    	sub.setSysMenu(m);
                    sub.setOperationRemark(sysOperation.getOperationRemark());
                    sub.setOperationName(sysOperation.getOperationName());
                    sub.setOperationAction(sysOperation.getOperationAction());
			    	sysOperationService.add(sub);
			    }
			}else{
				sysOperationService.add(sysOperation);
			}
		    json.setMsg("添加成功");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用operation/add 出错了>>>>>>>>>>>");
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
		
		Integer rows = sysOperationService.delete(ids);
	    MyJson json = new MyJson();
	    json.setMsg("成功删除"+rows+"条数据");
	    json.setStatusCode(200);
	    return json;
	}
	
	/**
	 * 修改
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update" ,  produces = "application/json",  method = RequestMethod.POST)
	public MyJson update(SysOperation sysOperation , String menuIds){
		MyJson json = new MyJson();
        SysOperation oldOperation = sysOperationService.get(sysOperation.getOperationId());

		try{
			String ids[] = menuIds == "" ? null : menuIds.split(",");
	        if(ids != null && ids.length >1){
	        	for(int i = 0;i < ids.length; i++){
	            	SysMenu m = new SysMenu();
                    m.setMenuId(Integer.valueOf(ids[i]));
                    SysOperation sub = new SysOperation();
                    //sub.setOperationAction(oldOperation.getOperationAction());
                    sub.setOperationName(sysOperation.getOperationName());
                    sub.setOperationRemark(sysOperation.getOperationRemark());
                    sub.setOperationAction(oldOperation.getOperationAction());
                    //sub.setSysRoles(oldOperation.getSysRoles());
                    sub.setSysMenu(m);
	            	sysOperationService.add(sub);
	            }
	        }else{
                //oldOperation.setOperationAction(sysOperation.getOperationAction());
                oldOperation.setOperationName(sysOperation.getOperationName());
                oldOperation.setOperationRemark(sysOperation.getOperationRemark());
                oldOperation.setOperationAction(sysOperation.getOperationAction());
                SysMenu m = new SysMenu();
                m.setMenuId(Integer.valueOf(ids[0]));
                oldOperation.setSysMenu(m);
	        	sysOperationService.update(oldOperation);
	        }
	        
		    
		    json.setMsg("修改成功");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用operation/update 出错了>>>>>>>");
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
		
		return "list/sys_operation";
	}
	
	/**
	 * 操作列表 json
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list" , produces = "application/json",  method = RequestMethod.POST)
	public PageBean<SysOperation> list(String rows,String page){
		PageBean<SysOperation> pagin = new PageBean<SysOperation>();
		pagin.setRowsPerPage(rows != null ?Integer.valueOf(rows) : 10);//每页多少行
		pagin.setPageNo(page != null ? Integer.valueOf(page) : 1);//当前第几页  	
		sysOperationService.findByPage(pagin, null);
		List<SysOperation> list = pagin.getData();
		pagin.setRows(list);//返回的数据
		pagin.setTotal(pagin.getRowsCount());//总条数
		pagin.setPage(((Integer)(pagin.getPageNo())).toString());//当前第几页 
		
		return pagin;
	}
	
	/**
	 * 操作列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/operations" , produces = "application/json" , method = RequestMethod.GET)
	public MyJson getOperation(HttpServletRequest request){
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		List<SysOperation> operationList = new ArrayList<>();
		Set<SysOperation> operationSet = new HashSet<>();
		MyJson json = new MyJson();
		Set<SysRole> roles = sysUser.getSysRoles();//角色
		
		for(SysRole role : roles){//遍历用户的所有角色
			Set<SysOperation> operations = role.getSysOperations();
			for(SysOperation oper : operations){
                operationSet.add(oper);
			}
		}
		for(SysOperation operation : operationSet){
			operationList.add(operation);
		}
		super.sortOperation(operationList);
        json.setData(operationList);
        json.setStatusCode(super.getmId());
		return json;
	}

    /**
     * 生成2007 Excel
     */
    @RequestMapping(value = "/export" , method = RequestMethod.GET)
    public  void export(HttpServletResponse response){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = format.format(new Date());
       // String basePath = /*System.getProperties().getProperty("user.dir")*/"F:"+ File.separator+"Excel"+File.separator;
        String exportFileName = "操作列表" + time+ ".xlsx";//导出文件名
        String title[] = {"操作ID" ,"操作名词"  ,"菜单权限"};
        OutputStream outStream = null;
        FileOutputStream fileOutputStream = null;
        XSSFWorkbook workBook = null;
        List<SysOperation> operations = sysOperationService.findAll();
        try {
            workBook = new XSSFWorkbook();//创建工作薄
            XSSFSheet sheet = workBook.createSheet();
            sheet.autoSizeColumn(1);
            workBook.setSheetName(0, "操作信息");//工作簿名称

            XSSFFont font = workBook.createFont();
            font.setColor(XSSFFont.COLOR_NORMAL);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

            XSSFCellStyle titleStyle = workBook.createCellStyle();//创建格式
            titleStyle.setFont(font);

            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置垂直对齐
            //创建第一行标题
            XSSFRow titleRow = sheet.createRow(0);//第一行标题
            for(int i = 0,size = title.length; i < size; i++){//创建第1行标题单元格
                XSSFCell cell = titleRow.createCell(i,0);//第0行第i个cell
                cell.setCellStyle(titleStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(title[i]);
            }
            //从第二行开始写入数据
            //注：此处如果数据过多，会抛出java.lang.IllegalStateException异常：The maximum number of cell styles was exceeded.
            //You can define up to 4000 styles in a .xls workbook。这是是由于cell styles太多create造成，故一般可以把cellstyle设置放到循环外面

            if(operations!=null && !operations.isEmpty()){
                XSSFCellStyle cellStyle = workBook.createCellStyle();//创建格式
                for(int i = 0,size = operations.size();i < size; i++){
                    SysOperation operation = operations.get(i);
                    XSSFRow row = sheet.createRow((short) i+1);
                    for (int j = 0,length = title.length; j < length; j++) {
                        XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
                        switch(j){// 在单元格中输入一些内容
                            case 0:
                                cell.setCellValue(operation.getOperationId());
                                cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                                cell.setCellStyle(cellStyle);
                                break;
                            case 1:
                                cell.setCellValue(operation.getOperationName());
                                cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                                cell.setCellStyle(cellStyle);
                                break;
                            case 2:
                                cell.setCellValue(operation.getSysMenu().getMenuName());
                                cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
                                cell.setCellStyle(cellStyle);
                                break;
                        }
                    }
                }
            }
            // 通过文件输出流生成Excel文件
           /* File file = new File("F://roleInfo.xlsx");
            fileOutputStream = new FileOutputStream(file);
            workBook.write(fileOutputStream);
            fileOutputStream.flush();
            System.out.println("Excel 2007文件导出完成！导出文件路径："+file.getPath());*/
            /***
             * Web形式输出Excel
             *
             */

             // 表示以附件的形式把文件发送到客户端
             response.setHeader("Content-Disposition", "attachment;filename=" + new String((exportFileName).getBytes(), "ISO-8859-1"));//设定输出文件头
             response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
             // 通过response的输出流把工作薄的流发送浏览器形成文件
             outStream = response.getOutputStream();
             workBook.write(outStream);
             outStream.flush();
        }catch(IOException e){
            System.out.println("生成Excel发生IO 异常！"+e.getMessage());
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("文件导出发生异常！异常原因："+e.getMessage());
            e.printStackTrace();
        }finally {
            if(workBook != null){
                try {
                    workBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( outStream != null){
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
