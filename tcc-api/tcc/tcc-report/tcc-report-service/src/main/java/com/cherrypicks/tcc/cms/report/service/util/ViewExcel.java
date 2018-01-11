package com.cherrypicks.tcc.cms.report.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.cherrypicks.tcc.cms.dto.ExcelElement;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.ReleaseResourceUtil;


public class ViewExcel extends AbstractXlsView {

    /**
     * request.Attribute = xlsFileName is file name
     */
    @Override
    public void buildExcelDocument(final Map<String, Object> model, final Workbook workbook, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        OutputStream ouputStream = null;
        try {
            String fileName = "notname.xls";// Excel Name
            if (request.getAttribute("xlsFileName") != null) {
                fileName = request.getAttribute("xlsFileName").toString();
                final String userAgent = request.getHeader("User-Agent");
                final byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
                fileName = new String(bytes, "ISO-8859-1");
            }
            // filename = StringUtils.encodeFilename(filename, request);//set CN

            // Dealing with the browser's code

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            ReleaseResourceUtil.closeOutputStream(ouputStream);
        }
    }

    public String saveExcelDocument(final HSSFWorkbook workbook, final String path, final String xlsFileName) {
        String filefullPath = "";
        FileOutputStream fos = null;
        try {
            final File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            final byte[] bytes = xlsFileName.getBytes("UTF-8");
            final String fileName = new String(bytes, "ISO-8859-1");
            filefullPath = path + fileName + ".xls";
            fos = new FileOutputStream(filefullPath);
            workbook.write(fos);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            ReleaseResourceUtil.closeFileOutputStream(fos);
        }
        return filefullPath;
    }

    public HSSFWorkbook resultSetToExcel(final List<ExcelElement> lstTitle, final List<?> lstData, final Class<?> c,
            final boolean isShowTotal) throws Exception {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet();

        final DecimalFormat df = new DecimalFormat("#0.00");

        HSSFRow row = sheet.createRow((short) 0);
        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        
        // 设置单元格类型
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setFont(font);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
        titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        titleCellStyle.setWrapText(true);
        
        row.setHeight((short) (5 * 100));
        HSSFCell cell = null;

        for (int i = 0; i < lstTitle.size(); i++) {
            cell = row.createCell((i));
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(lstTitle.get(i).getTitle());
            cell.setCellStyle(titleCellStyle);
            sheet.setColumnWidth(i, lstTitle.get(i).getWidth() * 256);
        }

        int iRow = 1;

        final Object countReport = c.newInstance();
        for (int i = 0; i < lstData.size(); i++) {
            final Object m = lstData.get(i);
            row = sheet.createRow((short) iRow);
            for (int j = 0; j < lstTitle.size(); j++) {

                cell = row.createCell(j);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);

                String field = lstTitle.get(j).getField();

                field = Character.toUpperCase(field.charAt(0)) + field.substring(1);
                final String getField = "get" + field;

                // 不支持父类的方法调用。
                final Method getMethod = c.getDeclaredMethod(getField);
                Object oj = getMethod.invoke(m);

                if (oj == null) {
                    oj = "";
                }

                if(oj instanceof Date){
                	cell.setCellValue(DateUtil.format((Date)oj, DateUtil.DATETIME_PATTERN_DEFAULT));
                }else if(oj instanceof Float){
                	if(((Float)oj) == 0 ){
                		cell.setCellValue("");
                	}else{
                		cell.setCellValue(df.format(oj).toString());
                	}
                }else if(oj instanceof Double){
                	if(((Double)oj) == 0 ){
                		cell.setCellValue("");
                	}else{
                		cell.setCellValue(df.format(oj).toString());
                	}
                }else{
                	cell.setCellValue(oj.toString());
                }

                // count total
                if (lstTitle.get(j).getIsCount() == true) {
                    final String setField = "set" + field;

                    if (oj instanceof Double) {
                        final Method setMethod = c.getDeclaredMethod(setField, Double.class);
                        final Double dOj = Double.valueOf(oj.toString());
                        if (getMethod.invoke(countReport) == null) {
                            setMethod.invoke(countReport, dOj);
                        } else {
                            final Double dCountOj = Double.valueOf(getMethod.invoke(countReport).toString());
                            setMethod.invoke(countReport, dOj + dCountOj);
                        }
                    } else if (oj instanceof Integer) {
                        final Method setMethod = c.getDeclaredMethod(setField, Integer.class);
                        final Integer iOj = Integer.valueOf(oj.toString());
                        if (getMethod.invoke(countReport) == null) {
                            setMethod.invoke(countReport, iOj);
                        } else {
                            final Integer iCountOj = Integer.valueOf(getMethod.invoke(countReport).toString());
                            setMethod.invoke(countReport, iOj + iCountOj);
                        }
                    }
                }
            }
            iRow++;
        }
        // show count

        if (isShowTotal && lstData.size() > 0) {
            row = sheet.createRow((short) iRow);
            cell = row.createCell(0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("Total no. of search result: ");
 
            cell = row.createCell(1);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(lstData.size());
 
            HSSFCellStyle totalCellStyle = workbook.createCellStyle();
            totalCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); //水平布局：居中
            titleCellStyle.setWrapText(true);
            
            cell.setCellStyle(totalCellStyle);
        }

        for (int i = 0; i < lstTitle.size(); i++) {
            if (lstTitle.get(i).getIsCount() == true) {

                cell = row.createCell(i);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);

                String field = lstTitle.get(i).getField();

                field = Character.toUpperCase(field.charAt(0)) + field.substring(1);
                final String getField = "get" + field;
                final Method getMethod = c.getDeclaredMethod(getField);

                Object oj = getMethod.invoke(countReport);

                if (oj == null) {
                    oj = "";
                }
                final String value = oj.toString().indexOf(".") != -1 ? df.format(oj).toString() : oj.toString();
                cell.setCellValue(value);
            }
        }
        return workbook;
    }
    
    public static void copySheet(HSSFSheet createSheet, HSSFSheet sheet) {
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		
		for(int i=firstRow; i<=lastRow; i++){
			HSSFRow row = createSheet.createRow(i);
			HSSFRow oldRow = sheet.getRow(i);
			
			int firstCell = oldRow.getFirstCellNum();
			int lastCell = oldRow.getLastCellNum();
			for(int j=firstCell; j<=lastCell; j++){
				HSSFCell cell = row.createCell(j);
				HSSFCell oldCell = oldRow.getCell(j);
				if(null != oldCell){
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(null == oldCell.getStringCellValue()?"":oldCell.getStringCellValue());
				}
				createSheet.autoSizeColumn(j,true);
			}
		}
	}
}
