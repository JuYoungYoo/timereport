package efuture.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2016-07-21.
 */
public class ExcelUtil extends AbstractExcelView {
    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req, HttpServletResponse res) throws Exception {
        String userAgent = req.getHeader("User-Agent");
        String fileName = model.get("fileName") + ".xls";

        /*파일명 설정*/
        if (userAgent.indexOf("MSIE") > -1) {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } else if (userAgent.indexOf("Trident") > -1) {
            fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        }

        /*헤더값 변경*/
        res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        res.setHeader("Content-Transfer-Encoding", "binary");

        int colWidth[] = (int[]) model.get("colWidth");

        /*시트생성*/
        HSSFSheet sheet = createFirstSheet(workbook, (String) model.get("excel_title"), colWidth);
        /*LIST data*/
        ArrayList<LinkedHashMap> excelList = (ArrayList<LinkedHashMap>) model.get("Excel");
        /*타이틀 입력<엑셀내용 상세>*/
        createExcelTitle(workbook, sheet, (String) model.get("excel_title"), colWidth.length);
        /*엑셀컬럼 라벨생성*/
        createColumnLabel(workbook, sheet, excelList);

        /*내부 데이터 ROW STYLE 객체 생성*/
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();

        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 세로정렬
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());

        style.setWrapText(true); // 개행 (셀에서 줄바꿈)
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        /*내부 데이터 ROW STYLE 객체 생성*/

        for (int i = 0; i <= excelList.size() - 1; i++) {
            /*엑셀ROW 데이터 생성*/
            createPageRow(sheet, style, excelList, i);
        }
    }

    /*
    * 시트생성
    * */
    private HSSFSheet createFirstSheet(HSSFWorkbook workbook,String title,int colWidth[]) {
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, title);
        for (int i = 0; i < colWidth.length; i++) {
            if (colWidth[i] == 0) colWidth[i] = 30;
            sheet.setColumnWidth(i, 262 * colWidth[i]);
        }
        return sheet;
    }

    /*
    * 타이틀 입력<엑셀내용 상세>
    * */
    private void createExcelTitle(HSSFWorkbook workbook, HSSFSheet sheet, String title, int length) {
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell cell = titleRow.createCell(0);
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();

        titleRow.setHeight((short) 700);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, length - 1));

        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(new HSSFColor.LIGHT_YELLOW().getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER); // 가로정렬
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 세로정렬
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        font.setFontHeightInPoints((short) 13);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        cell.setCellValue(title);
        cell.setCellStyle(style);
    }

    /*
    * 엑셀컬럼 라벨생성
    * */
    private void createColumnLabel(HSSFWorkbook workbook, HSSFSheet sheet, ArrayList<LinkedHashMap> list) {
        HSSFRow firstRow = sheet.createRow(1);
        HSSFCellStyle style = null;
        HSSFFont font = null;

        if (list.size() > 0 && list.size() < 65536) {
            style = workbook.createCellStyle();
            font = workbook.createFont();

            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setAlignment(CellStyle.ALIGN_CENTER); // 가로정렬
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 세로정렬
            style.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            font.setFontHeightInPoints((short) 9);
            font.setBoldweight(font.BOLDWEIGHT_BOLD);
            style.setFont(font);

            LinkedHashMap titleMap = list.get(0);

            Iterator it = titleMap.keySet().iterator();
            int i = 0;
            HSSFCell cell;
            while (it.hasNext()) {
                String key = (String) it.next();
                cell = firstRow.createCell(i);
                cell.setCellValue(key);
                cell.setCellStyle(style);
                i++;
            }
        }
    }

    /*
    * 엑셀ROW 데이터 생성
    * */
    private void createPageRow(HSSFSheet sheet, HSSFCellStyle style, ArrayList<LinkedHashMap> list, int rowNum) {
        HSSFRow row = sheet.createRow(rowNum + 2);
        HSSFCell cell;
        LinkedHashMap valueMap;
        valueMap = list.get(rowNum);
        HSSFRow key = sheet.getRow(1);
//        row.setHeight((short) 400);   // 주석 시 데이터 사이즈에 맞게 RESIZE ( 높이 ) X : 수동 높이 설정

        String keyCell;
        String keyValue;
        for (int i = 0; i < key.getLastCellNum(); i++) {
            cell = row.createCell(i);
            keyCell = key.getCell(i).toString();
            if (valueMap.get(keyCell) == null) {
                keyValue = "";
            } else {
                Object o = valueMap.get(keyCell);
                keyValue = o.toString();
            }

            cell.setCellValue(keyValue);
            cell.setCellStyle(style);
        }
    }
}