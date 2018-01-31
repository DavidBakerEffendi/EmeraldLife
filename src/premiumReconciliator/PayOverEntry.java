package premiumReconciliator;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

class PayOverEntry {
    private int rowIndex;
    private String payNo;
    private String area;
    private String policyCode;
    private String policyID;
    private String doi;
    private String initials;
    private String surname;
    private String id;
    private double payment;

    public PayOverEntry(int rowIndex, Row row) {
	this.rowIndex = rowIndex;
	Iterator<Cell> cellIterator = row.cellIterator();

	payNo = cellIterator.next().getStringCellValue();
	area = cellIterator.next().getStringCellValue();
	policyCode = cellIterator.next().getStringCellValue();
	policyID = cellIterator.next().getStringCellValue();
	doi = cellIterator.next().getStringCellValue();
	initials = cellIterator.next().getStringCellValue();
	surname = cellIterator.next().getStringCellValue();
	id = cellIterator.next().getStringCellValue();
	payment = cellIterator.next().getNumericCellValue();
    }
	
    public String getPayNo() {
	return this.payNo;
    }

    public String getArea() {
	return this.area;
    }

    public String getPolicyCode() {
	return this.policyCode;
    }

    public String getPolicyID() {
	return this.policyID;
    }

    public String getDOI() {
	return this.doi;
    }
    
    public String getInitials() {
	return this.initials;
    }
    
    public String getSurname() {
	return this.surname;
    }
    
    public String getID() {
	return this.id;
    }
    
    public double getPayment() {
	return this.payment;
    }
    
    public int getRowIndex() {
	return this.rowIndex;
    }

    public String toString() {
	String format = "| %s | %s | %s | %s | %s | %s | %s | %s | %4.2f |";
	Object[] args = { payNo, area, policyCode, policyID, doi, initials, surname, id, payment };
	return String.format(format, args);
    }
}
