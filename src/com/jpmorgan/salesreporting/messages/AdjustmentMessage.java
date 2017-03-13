package com.jpmorgan.salesreporting.messages;
import com.jpmorgan.salesreporting.messages.enums.OperationType;
import com.jpmorgan.salesreporting.messages.enums.SalesConstant;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Adjustment messages are represented in this POJO
 */

public class AdjustmentMessage extends Message {
	
	private OperationType operationType;
	
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public float getTotalSalesValue() {
		return SalesConstant.ZERO.getTotalSales();
	}
	public String toString() {
        return "Product Type:" + super.getProductType() + ", "
                + "Product Value.:" + super.getProductValue() + ", "
                + "Operation Type.:" + this.operationType; 
    }
	
}
