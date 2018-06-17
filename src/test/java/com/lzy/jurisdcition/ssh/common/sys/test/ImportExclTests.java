package com.lzy.jurisdcition.ssh.common.sys.test;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2016/10/3.
 */
public class ImportExclTests {

    private final static String SEPARATOR = "|";

    @Test
    public void test(){
        File file = new File("F://roleInfo.xlsx");
        InputStream fileInputStream = null;

        XSSFWorkbook workbook = null;
        try {
            fileInputStream = new FileInputStream(file);

            workbook = new XSSFWorkbook(fileInputStream);
            //workbook = new HSSFWorkbook();

            Sheet sheet = workbook.getSheetAt(0);
            // 解析公式结果
          /*  FormulaEvaluator evaluator = workbook.getCreationHelper()
                    .createFormulaEvaluator();*/

            List<SysOperation> list = new ArrayList<SysOperation>();

            int minRowIx = sheet.getFirstRowNum();
            int maxRowIx = sheet.getLastRowNum();
            for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
                Row row = sheet.getRow(rowIx+1);
                //StringBuilder sb = new StringBuilder();
                SysOperation operation = new SysOperation();
                if(row == null){
                    continue;
                }else{
                    short minColIx = row.getFirstCellNum();
                    short maxColIx = row.getLastCellNum();
                    for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
                        Cell cell = row.getCell(colIx);
//                        CellValue cellValue = evaluator.evaluate(cell);

                        switch(colIx){
                            case 0:
                                int v = (int) cell.getNumericCellValue();
                                operation.setOperationId(v);
                                break;
                            case 1:
                                String vs = cell.getStringCellValue();
                                operation.setOperationName(vs);
                                break;
                            case 2:
                                String vm = cell.getStringCellValue();
                                SysMenu menu = new SysMenu();
                                menu.setMenuId(1);
                                menu.setMenuName(vm);
                                operation.setSysMenu(menu);
                                break;
                        }
                        list.add(operation);
                }
                    // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
                    // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
                   /* switch (cellValue.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            sb.append(SEPARATOR + cellValue.getBooleanValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            // 这里的日期类型会被转换为数字类型，需要判别后区分处理
                            if (DateUtil.isCellDateFormatted(cell)) {
                                sb.append(SEPARATOR + cell.getDateCellValue());
                            } else {
                                sb.append(SEPARATOR + cellValue.getNumberValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            sb.append(SEPARATOR + cellValue.getStringValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            break;
                        case Cell.CELL_TYPE_ERROR:
                            break;
                        default:
                            break;
                    }*/
                }
            }
            System.out.println(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
