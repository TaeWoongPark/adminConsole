package kr.co.drawing;

import java.math.BigDecimal;

public class Console {
	
	// 기준 시작 시간(start)
	private String start;
	
	// 기준 종료 시간(end)
	private String end;
	
	// 시초가(open)
	private String open;
	
	// 시종가(close)
	private String close;
	
	// 고가(high)
	private String high;
	
	// 저가(low)
	private String low;
	
	// 평단가(average)
	private String average;
	
	// 가중평균(end)
	private String weightedAverage;
	
	// 누적 거래수(volume)
	private float volume;
	
	public String getStart() {		
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getOpen() {
		return open;
	}
	
	public void setOpen(String open) {
		this.open = open;
	}
	
	public String getClose() {
		return close;
	}
	
	public void setClose(String close) {
		this.close = close;
	}
	
	public String getHigh() {
		return high;
	}
	
	public void setHigh(String high) {
		this.high = high;
	}
	
	public String getLow() {
		return low;
	}
	
	public void setLow(String low) {
		this.low = low;
	}
	
	public String getAverage() {
		return average;
	}
	
	public void setAverage(String average) {
		this.average = average;
	}
	
	public String getWeightedAverage() {
		return weightedAverage;
	}
	
	public void setWeightedAverage(String weightedAverage) {
		this.weightedAverage = weightedAverage;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
}
