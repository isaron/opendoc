package com.cloud.platform;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {
	
	public static StringBuffer import_status = new StringBuffer();

	/**
	 * get excel cell string value
	 * 
	 * @param cell
	 * @return
	 */
    public static String getStringCellValue(Cell cell) {
        String strCell = "";
        
        if(cell == null) {
        	return strCell;
        }
        
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }

        return StringUtil.isNullOrEmpty(strCell) ? "" : strCell;
    }
}
