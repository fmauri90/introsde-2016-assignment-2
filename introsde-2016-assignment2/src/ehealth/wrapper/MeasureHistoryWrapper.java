package ehealth.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonValue;

import ehealth.model.HealthMeasureHistory;

/**
 * Wrapper used when is listened the history
 */

@XmlRootElement(name = "measureHistory")

public class MeasureHistoryWrapper {
	
	private List<HealthMeasureHistory> healthMeasureHistory = new ArrayList<HealthMeasureHistory>();
	
	@XmlElement(name = "measure")
	@JsonValue
	public List<HealthMeasureHistory> getHealthMeasureHistory(){
		return this.healthMeasureHistory;
	}
	
	public void setHealthMeasureHistory(List<HealthMeasureHistory> hmh){
		this.healthMeasureHistory = hmh;
	}
}