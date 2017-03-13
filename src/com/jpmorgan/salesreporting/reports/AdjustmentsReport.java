package com.jpmorgan.salesreporting.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * @version : 1.00
 * @author : Sarath Pillai
 * @date : 05/03/2017
 * @description : Adjustment report to track adjustments
 */
public class AdjustmentsReport {
	
	private String productType;
	private float initialUnitValue;
	private List<Float> adjustedUnitValue = new ArrayList<>();
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public float getInitialUnitValue() {
		return initialUnitValue;
	}
	public void setInitialUnitValue(float initialUnitValue) {
		this.initialUnitValue = initialUnitValue;
	}
	public List<Float> getAdjustedUnitValue() {
		return adjustedUnitValue;
	}
	public void setAdjustedUnitValue(List<Float> adjustedUnitValue) {
		this.adjustedUnitValue = adjustedUnitValue;
	}
	public void addAdjustedUnitValue(float adjustedUnitValue) {
		this.adjustedUnitValue.add(adjustedUnitValue);
	}

}
