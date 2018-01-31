/**
 * 
 */
package premiumReconciliator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author David Baker Effendi
 * @since 0.1
 */
public class PremiumReconciliator {

    private static String date = null;
    //private static Date deductionMonth = null;
    private static int newColumnNum = 0;
    private static int totalRowNum = 0;
    	
    /**
     * 
     * @param template
     * @param target
     */
    @SuppressWarnings("deprecation")
    public static void reconcile(File template, File target) {
	FileInputStream templateFis = null;
	FileInputStream targetFis = null;

	XSSFWorkbook billingScheduleXLSX = null;
	XSSFWorkbook payOverXLSX = null;

	XSSFSheet billingScheduleSheet = null;
	XSSFSheet payOverSheet = null;

	try {
	    templateFis = new FileInputStream(template);
	    targetFis = new FileInputStream(target);

	    // Finds workbook instance for XLSX file.
	    billingScheduleXLSX = new XSSFWorkbook(templateFis);
	    payOverXLSX = new XSSFWorkbook(targetFis);

	    // Return first sheet from XLSX workbook.
	    billingScheduleSheet = billingScheduleXLSX.getSheetAt(0);
	    payOverSheet = payOverXLSX.getSheetAt(0);

	    // Get iterator to all the rows in the current sheet.
	    Iterator<Row> billingScheduleRow = billingScheduleSheet.iterator();
	    Iterator<Row> payOverRowIterator = payOverSheet.iterator();

	    // Create LinkedLists
	    LinkedList<BillingScheduleEntry> listBS = collectBS(billingScheduleRow);
	    LinkedList<PayOverEntry> listPOE = collectPOE(payOverRowIterator);

	    // Reconcile files and set payments
	    setPayment(listPOE, listBS);
	    
	    // Initialize new column
	    
	    // Heading initialization
	    Row headingRow = billingScheduleSheet.getRow(17);
	    newColumnNum = headingRow.getLastCellNum();
	    Cell headingCell = headingRow.createCell(newColumnNum);
	    String cellHeading = date.substring(0, 3).toUpperCase() + "  " + date.substring(date.length() - 2);
	    headingCell.setCellValue("REC " + cellHeading);
	    
	    // Find final row
	    for (int i = 19; i < billingScheduleSheet.getLastRowNum(); i++) {
		Row premiumRow = billingScheduleSheet.getRow(i);
		Cell cell = premiumRow.getCell(11);
		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
		    totalRowNum = i;
		    break;
		}
	    }
	    
	    // Insert payments
	    for (BillingScheduleEntry bsEntry : listBS) {
		Row premiumRow = billingScheduleSheet.getRow(bsEntry.getRowIndex());
		Cell premiumCell = premiumRow.createCell(newColumnNum);
		premiumCell.setCellValue(bsEntry.getPayment());
	    }
	    
	    // Set cell addresses:
	    Row startRow = billingScheduleSheet.getRow(18);
	    CellAddress startAdd = startRow.getCell(newColumnNum).getAddress();
	    Row endRow = billingScheduleSheet.getRow(totalRowNum - 1);
	    CellAddress endAdd = endRow.getCell(newColumnNum).getAddress();

	    // Create SUM formula:
	    Row sumRow = billingScheduleSheet.getRow(totalRowNum);
	    Cell sumCell = sumRow.getCell(newColumnNum);
	    sumCell.setCellFormula("SUM(" + startAdd.formatAsString() + ":" + endAdd.formatAsString() + ")");

	    // Cell formatting
	    for (int i = 0; i < totalRowNum + 1; i++) {
		try {
		    Row premiumRow = billingScheduleSheet.getRow(i);
		    Cell premiumCell = premiumRow.getCell(newColumnNum);
		    premiumCell.setCellStyle(premiumRow.getCell(newColumnNum - 1).getCellStyle());
		} catch (Exception e) {

		}
	    }
	    
	    // Fix bug cell
	    Row bugRow = billingScheduleSheet.getRow(totalRowNum + 1);
	    Cell bugCell = bugRow.getCell(newColumnNum);
	    bugCell.setCellType(CellType.BLANK);
	    
	    // Write the results
	    FileOutputStream os = new FileOutputStream(template);
	    billingScheduleXLSX.write(os);
	} catch (Exception e) {
	    System.out.println("Error: Failed to process .xlsx files.");
	    e.printStackTrace();
	    System.exit(0);
	} finally {
	    try {
		if (templateFis != null) {
		    templateFis.close();
		}
		if (targetFis != null) {
		    targetFis.close();
		}

		if (billingScheduleXLSX != null) {
		    billingScheduleXLSX.close();
		}
		if (payOverXLSX != null) {
		    payOverXLSX.close();
		}
	    } catch (IOException e) {
		System.out.println("Error: Could not free resources for the .xlsx files.");
		System.exit(0);
	    }
	}
	
	System.out.println("Sucessfully written payments to '" + template.getName() + "'.");
	return;
    }

    /**
     * 
     * @param collectPOE
     * @param collectBS
     */
    private static void setPayment(LinkedList<PayOverEntry> collectPOE, LinkedList<BillingScheduleEntry> collectBS) {
	for (BillingScheduleEntry bsEntry : collectBS) {
	    for (PayOverEntry poeEntry : collectPOE) {
		if (bsEntry.getPolicyNumber().equals(poeEntry.getPolicyCode())) {
		    bsEntry.setPayment(poeEntry.getPayment());
		    break;
		}
	    }
	}
    }

    /**
     * 
     * @param billingScheduleRow
     * @return
     */
    private static LinkedList<BillingScheduleEntry> collectBS(Iterator<Row> billingScheduleRow) {
	int rowCount = 1;
	LinkedList<BillingScheduleEntry> billingScheduleEntries = new LinkedList<BillingScheduleEntry>();

	while (billingScheduleRow.hasNext()) {
	    Row row = billingScheduleRow.next();

//	    if (rowCount == 12) {
//		deductionMonth = row.getCell(7).getDateCellValue();
//	    }

	    if (rowCount >= 19) {
		try {
		    billingScheduleEntries.add(new BillingScheduleEntry(rowCount - 1, row));
		} catch (Exception e) {
		    // This is where the entries end but not necessarily the spreadsheet.
		    // e.printStackTrace();
		}
	    }

	    rowCount++;
	}

	return billingScheduleEntries;
    }

    /**
     * 
     * @param payOverRowIterator
     * @return
     */
    private static LinkedList<PayOverEntry> collectPOE(Iterator<Row> payOverRowIterator) {
	int rowCount = 1;
	LinkedList<PayOverEntry> payOverEntries = new LinkedList<PayOverEntry>();

	while (payOverRowIterator.hasNext()) {
	    Row row = payOverRowIterator.next();

	    if (rowCount == 2) {
		date = row.getCell(3).getStringCellValue();
	    }

	    if (rowCount >= 4) {
		try {
		    payOverEntries.add(new PayOverEntry(rowCount - 1, row));
		} catch (Exception e) {
		    // This is where the entries end but not necessarily the spreadsheet.
		}
	    }

	    rowCount++;
	}
	return payOverEntries;
    }
}
