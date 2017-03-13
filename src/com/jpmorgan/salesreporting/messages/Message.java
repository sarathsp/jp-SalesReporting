package com.jpmorgan.salesreporting.messages;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Adjustment messages are represented in this POJO
 */

public abstract class Message {
	
	private String productType;
	private float productValue;
	private long messageId;
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public float getProductValue() {
		return productValue;
	}
	public void setProductValue(float productValue) {
		this.productValue = productValue;
	}
	public abstract float getTotalSalesValue();

	public String toString() {
        return "Product Type:" + this.productType + ",, "
                + "Product Value.:" + this.productValue;
    }
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = System.nanoTime();
	}

}
