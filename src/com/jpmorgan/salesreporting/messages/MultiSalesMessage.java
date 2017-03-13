package com.jpmorgan.salesreporting.messages;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Adjustment messages are represented in this POJO
 */
public class MultiSalesMessage extends Message{
    private int salesCount;
	
	public int getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}
	public float getTotalSalesValue() {
		return this.salesCount * this.getProductValue();
	}
	public String toString() {
        return "Product Type:" + super.getProductType() + ",, "
                + "Product Value.:" + super.getProductValue() + ",, "
                + "Sales Count.:" + this.salesCount; 
    }

}
