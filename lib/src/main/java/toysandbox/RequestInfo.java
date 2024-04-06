package toysandbox;

public class RequestInfo {
	private final String requestType;

	private final Long startTimeMilli;

	public RequestInfo(String requestType) {
		this.requestType = requestType;
		this.startTimeMilli = System.currentTimeMillis();
	}

	public String getRequestType() {
		return this.requestType;
	}

	public Long getStartTimeMilli() {
		return this.startTimeMilli;
	}
}
