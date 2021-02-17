package kr.co.drawing;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class Refer {
	
	Integer timestamp = 0;
	Integer price = 1;
	Integer size = 2;
	String TIMESTAMP = "timestamp";
	String PRICE = "price";
	String SIZE = "size";
	String START = "start";
	String END = "end";
	String OPEN = "open";
	String CLOSE = "close";
	String HIGH = "high";
	String LOW = "low";
	String AVERAGE = "average";
	String WEIGHTED_AVERAGE = "weighted_average";
	String VOLUME = "volume";
	
	static ArrayList<Console> Output = new ArrayList<Console>();
	static Console Temp = new Console();
	static boolean isJump = false;
	static String Time = null;			// timestamp 시작 시간
	static String TimeEnd = null;		// timestamp 종료 시간
	static String tempLow = null;		// 저가 임시
	static String tempAverage = null;	// 평단가 임시
	static int tempCount = 0;			// 해당 timestamp 시간 안에 거래수
	static String tempWeightedAverage = null;	// 가중평균 임시
	
	public void set(String line) {
		String array[] = line.split(",");
		parse(array);
	}
	
	// 최초 거래시작 시간
	public void firstTimestamp(String line) {
		String list[] = line.split(",");
		Time = list[timestamp];
		TimeEnd = Long.toString(Long.parseLong(Time) + 29);
		Temp.setStart(Time);
		Temp.setEnd(TimeEnd);
		parse(list);
	}
	
	public void parse(String[] list) {
		if(Long.parseLong(list[timestamp]) >= Long.parseLong(Time) && Long.parseLong(list[timestamp]) < Long.parseLong(TimeEnd)) {		
			// 시초가(open)
			if(Temp.getOpen() == null) {
				Temp.setOpen(list[price]);
			}
			// 종가(close)
			Temp.setClose(list[price]);
			// 고가(high)
			if(Temp.getHigh() == null) {
				Temp.setHigh(list[price]);
			}else if(Long.parseLong(Temp.getHigh()) < Long.parseLong(list[price])) {
				Temp.setHigh(list[price]);
			}
			// 저가(low)
			if(Temp.getLow() == null) {
				Temp.setLow(list[price]);
			}else if(Long.parseLong(Temp.getLow()) > Long.parseLong(list[price])) {
				Temp.setLow(list[price]);
			}
			// 평단가(average)
			if(tempAverage == null) {
				tempAverage = list[price];
			}else {
				tempAverage = Long.toString(Long.parseLong(tempAverage) + Long.parseLong(list[price]));
			}			
			tempCount++;
			
			// 가중평균(weighted_average)
			if(tempWeightedAverage == null) {
				tempWeightedAverage = Float.toString(Math.round(Long.parseLong(list[price]) * Float.parseFloat(list[size])));
			}else {
				tempWeightedAverage = Float.toString(Float.parseFloat(tempWeightedAverage) + Math.round(Long.parseLong(list[price]) * Float.parseFloat(list[size])));
			}
			// 코인거래량(volume)			
			Temp.setVolume(Temp.getVolume()+Float.parseFloat(list[size]));
			
			// 값 없이 건너뛰었을경우
			if(isJump) {
				isJump = false;
				
				// 평균 계산
				averag();				
				
				// output			
				Output.add(Temp);				
			}
		}else {
			// 시초가(open)
			if(Temp.getOpen() == null) {
				isJump = true;
				Temp.setOpen(null);
			}
			// 종가(close)
			// 고가(high)
			if(Temp.getHigh() == null) {
				Temp.setHigh(null);
			}
			// 저가(low)
			if(Temp.getLow() == null) {
				Temp.setLow(null);
			}		
			
			// 평균 계산
			averag();
						
			// output			
			Output.add(Temp);
			
			// temp init
			Temp = new Console();

			// time + 30
			Time = Long.toString(Long.parseLong(Time) + 30);
			TimeEnd = Long.toString(Long.parseLong(TimeEnd) + 30);			
			Temp.setStart(Time);
			Temp.setEnd(TimeEnd);
			
			// 재귀호출
			parse(list);
			
		}
	}
	
	// 특정 구간 계산
	static void averag() {
		// 평단가(average)
		if(tempCount != 0) {
			Temp.setAverage(Long.toString(Math.round(Long.parseLong(tempAverage) / tempCount)));
			tempAverage = null;
			tempCount = 0;				
		}else {
			Temp.setAverage(null);
		}
		// 가중평균(weighted_average)
		if(tempWeightedAverage != null) {
			Temp.setWeightedAverage(Integer.toString(Math.round(Float.parseFloat(tempWeightedAverage) / Temp.getVolume())));
			tempWeightedAverage = null;
		}else {
			Temp.setWeightedAverage(null);
		}		
	}
	
	public void output() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapper.writeValueAsString(Output);
			System.out.println(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	}
}