package toysandbox;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RequestRecord {
	private ConcurrentMap<Long, Integer> finishedRequestRecord;

	private ConcurrentMap<String, Integer> requestPenaltyMilli;

	public RequestRecord() {
		this.finishedRequestRecord = new ConcurrentHashMap<Long, Integer>();
		this.requestPenaltyMilli = new ConcurrentHashMap<String, Integer>(Map.ofEntries(
			Map.entry("criminal", (Integer) Settings.getSetting("criminal")),
			Map.entry("normal", (Integer) Settings.getSetting("normal"))
		));
	}

	public Integer getPenaltyMilli(RequestInfo requestInfo) {
		return this.requestPenaltyMilli.getOrDefault(requestInfo.getRequestType(), 0);
	}

	public void updateRequestRecord(RequestInfo requestInfo) {
		final Long finishTimeMilli = System.currentTimeMillis();
		final Long startTimeMilli = requestInfo.getStartTimeMilli();
		final Integer currentThroughput = this.finishedRequestRecord.compute(finishTimeMilli / 1000,
			(key, value) -> (value == null) ? 1 : value + 1
		);

		this.requestPenaltyMilli.compute(requestInfo.getRequestType(),
			(key, value) -> (value == null) ? 0 : updatePeneltyMilli(value, currentThroughput, finishTimeMilli - startTimeMilli)
		);
	}

	private Integer updatePeneltyMilli(Integer oldPenalty, Integer currentThroughput, Long requestLatency) {
		return oldPenalty;
	}
}
