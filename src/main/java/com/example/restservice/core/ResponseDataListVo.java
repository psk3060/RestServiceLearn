package com.example.restservice.core;

import java.util.List;
import java.util.Map;

public class ResponseDataListVo extends ResponseVo {

	public ResponseDataListVo() {
		super();
	}
	
	public ResponseDataListVo(String code) {
		super(code);
	}
	
	public ResponseDataListVo(String code, String message) {
		super(code, message);
	}
	
	public ResponseDataListVo(String code, String message, List<Map<String, Object>> dataList) {
		this(code, message);
		this.dataList = dataList;
	}
	
	public ResponseDataListVo(String code, String message, List<Map<String, Object>> dataList, Integer totalCount) {
		this(code, message, dataList);
		this.totalCount = totalCount;
	}
	
	private List<Map<String, Object>> dataList;
	
	private Integer totalCount;
	
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "code[" + getCode() + "], " + ", message[" + getMessage() + "], dataList : " + dataList.toString() + ", totalCount[ " + totalCount + " ]"; 
	}
	
}
