package toysandbox;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RequestRecord {
	private ConcurrentMap<Long, Integer> finishedRequestRecord;

	private ConcurrentMap<String, Long> requestPenaltyMilli;

	public RequestRecord() {
		this.finishedRequestRecord = new ConcurrentHashMap<Long, Integer>();
		this.requestPenaltyMilli = new ConcurrentHashMap<String, Long>(Map.ofEntries(
			Map.entry("abnormal", (Long) Settings.getSetting("abnormal_request_base_penalty_milli")),
			Map.entry("normal", (Long) Settings.getSetting("normal_request_base_penalty_milli"))
		));
	}

	public Long getPenaltyMilli(RequestInfo requestInfo) {
		return this.requestPenaltyMilli.getOrDefault(requestInfo.getRequestType(), 0L);
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

	private Long updatePeneltyMilli(Long oldPenalty, Integer currentThroughput, Long requestLatency) {
		return oldPenalty;
	}
}
