package com.jpmorgan.salesreporting.engine;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.jpmorgan.salesreporting.messages.AdjustmentMessage;
import com.jpmorgan.salesreporting.messages.Message;
import com.jpmorgan.salesreporting.messages.MultiSalesMessage;
import com.jpmorgan.salesreporting.messages.SalesMessage;
import com.jpmorgan.salesreporting.messages.enums.MessageConstants;
import com.jpmorgan.salesreporting.messages.enums.OperationType;
import com.jpmorgan.salesreporting.reports.AdjustmentsReport;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Adjustment messages are represented in this POJO
 */

public class SalesProcessingEngine {
	private ArrayList<Message> inStreamMessages = new ArrayList<Message>();
	private ArrayList<AdjustmentsReport> adjustmentReportList = new ArrayList<AdjustmentsReport>();
	
	/*
	 * This message will be called by actual messaging system's implementation,when
	 * new message arrives
	 * @param msg
	 */
	private void processMessages(Message msg) {
		System.out.println("Received Message" + msg);
		
			if(msg instanceof AdjustmentMessage) {
				handleAdjustments((AdjustmentMessage) msg,inStreamMessages);
			} else {
				AdjustmentsReport  adjustmentReport = new AdjustmentsReport();
				adjustmentReport.setInitialUnitValue(msg.getProductValue());
				adjustmentReport.setProductType(msg.getProductType());
				adjustmentReportList.add(adjustmentReport);
			}
			inStreamMessages.add(msg);
			if(inStreamMessages.size() == MessageConstants.REPORTCOUNT.getMessageCount()) {
				logSalesReportForProduct(inStreamMessages);
			}
			if(inStreamMessages.size() == MessageConstants.ADJUSTMENTCOUNT.getMessageCount()) {
				logAdjustmentsDone(inStreamMessages);
			}
	}
	
	/*
	 * This method is to print the sales report
	 * @param messages
	 */
	private void logSalesReportForProduct(ArrayList<Message> messages) {
		 Map<String, Float> logReportMap = messages.stream()
		 .collect(
		  Collectors.groupingBy(Message::getProductType,
				  Collectors.reducing(
		                    0.0f,
		                    Message::getTotalSalesValue,
		                    Float::sum))
		  );
		 System.out.println("-------------------------------");
		 System.out.println("--- Sales Report Start ---");
		 logReportMap.forEach((productType, totalSalesValue) -> {
			    System.out.println("Product Type : " + productType);
			    System.out.println("Total Sales Value : " + totalSalesValue);
			});
		 System.out.println("--- Sales Report End ---");
		
	}
	
	/*
	 * This method is to print the adjustments report
	 * @param messages
	 */
	private void logAdjustmentsDone(ArrayList<Message> messages) {
		 
		 System.out.println("-------------------------------");
		 System.out.println("--- Adjustments Report Summary Start ---");
		 messages.stream()
		 .filter(message -> message instanceof AdjustmentMessage)
		 .map(message -> (AdjustmentMessage) message)
		  .forEach(System.out::println);

		 System.out.println("--- Adjustments Report Summary End ---");
		 
		 System.out.println("--- Adjustments Report Details Start ---");
		 adjustmentReportList.stream().forEach((adjustmentReport) -> {
			    System.out.println("Initial Value : " + adjustmentReport.getInitialUnitValue());
			    System.out.println("Product Type : " + adjustmentReport.getProductType());
			    System.out.println("Adjusted Values : " + adjustmentReport.getAdjustedUnitValue());
			});
		 System.out.println("--- Adjustments Report Details End ---");

		
	}
	
	/*
	 * This method should handle adjustment for all the sales recorded as of now
	 * @param adjustmentMessage
	 * @param messages
	 */
	private void handleAdjustments(AdjustmentMessage adjustmentMessage,ArrayList<Message> messages) {
//		Map<String,Float> adjustmentMap = messages.stream().
//		filter(message -> !(message instanceof AdjustmentMessage))
//		.filter(message -> message.getProductType().equals(adjustmentMessage.getProductType()))
//		.collect(
//				Collectors.groupingBy(Message::getProductType,
//							  Collectors.reducing(
//									    adjustmentMessage.getProductValue(),
//					                    Message::getProductValue,
//					                    Float::sum))
//				);	
//		Map<String,Float> adjustmentMap = messages.stream().
//				filter(message -> !(message instanceof AdjustmentMessage))
//				.filter(message -> message.getProductType().equals(adjustmentMessage.getProductType()))
//				.reduce(message)
	

		messages.stream().filter(message -> message.getProductType().equals(adjustmentMessage.getProductType())).
				forEach(message -> {
					if(adjustmentMessage.getOperationType() == OperationType.ADD) {
					  message.setProductValue(message.getProductValue() + adjustmentMessage.getProductValue());
					}
					if(adjustmentMessage.getOperationType() == OperationType.MULTIPLY) {
						  message.setProductValue(message.getProductValue() * adjustmentMessage.getProductValue());
					}
					if(adjustmentMessage.getOperationType() == OperationType.SUBSTARCT) {
						  message.setProductValue(message.getProductValue() - adjustmentMessage.getProductValue());
					}
				});

		updateAdjustmentReport(messages);
	}
	/*
	 * This method is to update adjustment report
	 * @param messages
	 * @param isAdjustmentsDone
	 */
	private void updateAdjustmentReport(ArrayList<Message> messages) {
		AtomicInteger index = new AtomicInteger(0);
		messages.stream().
		forEach(message -> {
					if(!(message instanceof AdjustmentMessage)) {
						AdjustmentsReport adjustmentsReport = adjustmentReportList.get(index.get());
						if(message.getProductValue() != adjustmentsReport.getInitialUnitValue()) {
							adjustmentsReport.addAdjustedUnitValue(message.getProductValue());
						}
						adjustmentReportList.set(index.get(), adjustmentsReport);
						index.getAndIncrement();
				     }
			
		});
		
	}
	/*
	 * Main method - Stubs to test
	 * @param args
	 */
	public static void main(String args[]) {
		SalesProcessingEngine spe = new SalesProcessingEngine();
		
        for(int i= 0;i<20;i++) {
        	SalesMessage msg = new SalesMessage();
    		msg.setProductType("apple");
            msg.setProductValue(10);
            MultiSalesMessage mmsg1 = new MultiSalesMessage();
            mmsg1.setProductType("orange");
            mmsg1.setProductValue(10);
            mmsg1.setSalesCount(10);
        	spe.processMessages(msg);
        	spe.processMessages(mmsg1);
        };
        
        AdjustmentMessage amsg = new AdjustmentMessage();
        amsg.setProductType("apple");
        amsg.setProductValue(20);
        amsg.setOperationType(OperationType.ADD);
        spe.processMessages(amsg);
       
        for(int i= 0;i<10;i++) {
        	 MultiSalesMessage mmsg2 = new MultiSalesMessage();
             mmsg2.setProductType("orange");
             mmsg2.setProductValue(10);
             mmsg2.setSalesCount(10);
        	spe.processMessages(mmsg2);
        }
        
	}

}
