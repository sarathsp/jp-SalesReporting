package com.jpmorgan.salesreporting.messages;

import com.jpmorgan.salesreporting.messages.enums.MessageConstants;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Adjustment messages are represented in this POJO
 */

public class SalesMessage extends Message {

	public int getSalesCount() {
		return  MessageConstants.SINGLE.getMessageCount();
	}
	public float getTotalSalesValue() {
		return this.getSalesCount() * this.getProductValue();
	}
}
