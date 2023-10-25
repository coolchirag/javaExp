package com.chirag.clon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Clonable1 implements Cloneable {

	private String data1;
	private Set<String> dataList;
	private List<ClonnableBean2> cList;
	private Set<ClonnableBean2> cSet;
	
	
	public Clonable1() {
		super();
	}
	public Clonable1(String data1) {
		super();
		this.data1 = data1;
	}
	
	
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public Set<String> getDataList() {
		return dataList;
	}
	public void setDataList(Set<String> dataList) {
		this.dataList = dataList;
	}
	
	public void addData(String data) {
		if(this.dataList == null) {
			this.dataList = new HashSet<String>();
		}
		this.dataList.add(data);
	}
	
	public List<ClonnableBean2> getcList() {
		return cList;
	}
	public void setcList(List<ClonnableBean2> cList) {
		this.cList = cList;
	}
	public void addcList(ClonnableBean2 cList) {
		if(this.cList == null) {
			this.cList = new ArrayList<>();
		}
		this.cList.add(cList);
	}
	
	public Set<ClonnableBean2> getcSet() {
		return cSet;
	}
	public void setcSet(Set<ClonnableBean2> cSet) {
		this.cSet = cSet;
	}
	public void addcSet(ClonnableBean2 cSet) {
		if(this.cSet == null) {
			this.cSet = new HashSet<>();
		}
		this.cSet.add(cSet);
	}
	
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public String toString() {
		return "Clonable1 [data1=" + data1 + ", dataList=" + dataList + ", cList=" + cList + ", cSet=" + cSet + "]";
	}
	
	
}
