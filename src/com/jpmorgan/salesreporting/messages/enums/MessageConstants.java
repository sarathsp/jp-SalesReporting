package com.jpmorgan.salesreporting.messages.enums;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Message Constants are added in this enum
 */

public enum MessageConstants {
	REPORTCOUNT(10),ADJUSTMENTCOUNT(50),SINGLE(1),ZERO(0);
	 private MessageConstants(int messageCount){
		    this.messageCount = messageCount;
		  }
		  private int messageCount;

		  public int getMessageCount(){
		    return this.messageCount;
		  }

		  public void setMessageCount(int messageCount){
		    this.messageCount = messageCount;
		  }
}
