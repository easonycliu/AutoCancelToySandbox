package toysandbox;

import java.util.function.Function;

public class ToySandbox {
	private static Function<Object, RequestInfo> requestParser;

	private static RequestRecord requestRecord;

	public static void start(Function<Object, RequestInfo> requestParser) {
		ToySandbox.requestParser = requestParser;
		ToySandbox.requestRecord = new RequestRecord();
	}

	public static void onRequestReceive(Object request) {
		RequestInfo requestInfo = ToySandbox.requestParser.apply(request);
		Long waitingTimeMilli = ToySandbox.requestRecord.getPenaltyMilli(requestInfo).longValue();
		ToySandbox.doWaiting(waitingTimeMilli);
	}

	public static void onResponseSend(Object request) {
		ToySandbox.requestRecord.updateRequestRecord(ToySandbox.requestParser.apply(request));
	}

	private static void doWaiting(Long waitingTimeMilli) {
		try {
			Thread.sleep(waitingTimeMilli);
		}
		catch (InterruptedException e) {
			System.out.println(String.format("Waiting request interrupted by %s", e.toString()));
		}
	}
}
