package com.dyashin.javacollections.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dyashin.javacollections.model.Employee;
import com.dyashin.javacollections.model.LeaveRecord;
import com.dyashin.javacollections.util.DateUtil;

public class LeaveAnalyzerService {

	private Map<Employee, List<LeaveRecord>> leaveData = new HashMap<>();

	public void addEmployee(Employee employee) {
		if (!leaveData.containsKey(employee)) {
			leaveData.put(employee, new ArrayList<LeaveRecord>());
		}
	}

	public Employee getEmployeeById(int id) {
		for (Employee e : leaveData.keySet()) {
			if (e.getId() == id) {
				return e;
			}
		}
		return null;
	}

	public void addLeave(Employee employee, LeaveRecord leaveRecord) {
		List<LeaveRecord> record = leaveData.get(employee);
		if (record != null) {
			record.add(leaveRecord);
		}
	}

	public Map<Employee, Integer> totalLeaves() {
		Map<Employee, Integer> result = new HashMap<>();
		for (Map.Entry<Employee, List<LeaveRecord>> entry : leaveData.entrySet()) {
			result.put(entry.getKey(), entry.getValue().size());
		}
		return result;
	}

	public List<Employee> frequentLongWeekendEmployess(int value) {
		List<Employee> list = new ArrayList<>();

		for (Map.Entry<Employee, List<LeaveRecord>> entry : leaveData.entrySet()) {
			int count = 0;
			for (LeaveRecord leaveRecord : entry.getValue()) {
				if (DateUtil.isMondayOrFriday(leaveRecord.getLeaveDate())) {
					count++;
				}
			}
			if (count >= value) {
				list.add(entry.getKey());
			}
		}
		return list;
	}

	public Map<Month, Integer> monthlyLeaveCount() {
		Map<Month, Integer> monthCount = new LinkedHashMap<>();

		for (List<LeaveRecord> record : leaveData.values()) {
			for (LeaveRecord leaveRecord : record) {
				Month month = leaveRecord.getLeaveDate().getMonth();
				Integer value = monthCount.get(month);
				if (value == null) {
					monthCount.put(month, 1);
				} else {
					monthCount.put(month, value + 1);
				}
			}
		}

		return monthCount;
	}

	public List<Map.Entry<Employee, Integer>> sortedLeaveSummary() {
		Map<Employee, Integer> totalMap = totalLeaves();
		List<Map.Entry<Employee, Integer>> list = new ArrayList<Map.Entry<Employee, Integer>>(totalMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Employee, Integer>>() {

			@Override
			public int compare(Entry<Employee, Integer> o1, Entry<Employee, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		return list;
	}

}
