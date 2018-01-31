package premiumReconciliator;

import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class BillingScheduleEntry {
    private int rowIndex;
    private int employeeNumber;
    private String id;
    private String name;
    private String policyNumber;
    private String status;
    private double premium;
    private Date commencementDate;
    private String payRoll;
    private String comment;
    private double payment = 0;
    
    @SuppressWarnings("deprecation")
    public BillingScheduleEntry(int rowIndex, Row row) {
	this.rowIndex = rowIndex;
	Iterator<Cell> cellIterator = row.cellIterator();

	employeeNumber = (int) cellIterator.next().getNumericCellValue();
	id = String.format("%13.0f", cellIterator.next().getNumericCellValue());
	name = cellIterator.next().getStringCellValue();
	policyNumber = cellIterator.next().getStringCellValue();
	status = cellIterator.next().getStringCellValue();
	premium = cellIterator.next().getNumericCellValue();
	commencementDate = cellIterator.next().getDateCellValue();
	payRoll = cellIterator.next().getStringCellValue();
	Cell cellComment = cellIterator.next();
	if (cellComment.getCellType() == Cell.CELL_TYPE_STRING)
	    comment = cellComment.getStringCellValue();
	else
	    comment = "";
    }
    
    public int getEmployeeNumber() {
	return this.employeeNumber;
    }

    public String getID() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public String getPolicyNumber() {
	return this.policyNumber;
    }

    public String getStatus() {
	return this.status;
    }
    
    public double getPremium() {
	return this.premium;
    }
    
    public Date getCommencementDate() {
	return this.commencementDate;
    }
    
    public String getPayRoll() {
	return this.payRoll;
    }
    
    public String getComment() {
	return this.comment;
    }
    
    public int getRowIndex() {
	return this.rowIndex;
    }
    
    public double getPayment() {
	return this.payment;
    }
    
    public void setPayment(double payment) {
	this.payment = payment;
    }

    public String toString() {
	String format = "| %d | %s | %s | %s | %s | %4.2f | %s | %s | %s |";
	Object[] args = { employeeNumber, id, name, policyNumber, status, premium, commencementDate.toString(), payRoll,
		comment };
	return String.format(format, args);
    }
}
