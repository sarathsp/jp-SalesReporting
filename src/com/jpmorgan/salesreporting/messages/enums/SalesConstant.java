package com.jpmorgan.salesreporting.messages.enums;

public enum SalesConstant {
	 ZERO(0.0f);
	 private SalesConstant(float totalSales){
		    this.totalSales = totalSales;
		  }
		  private float totalSales;

		  public float getTotalSales(){
		    return this.totalSales;
		  }

		  public void setotalSales(float totalSales){
		    this.totalSales = totalSales;
		  }
}
