package toysandbox;

import java.util.function.Function;

public class ToySandbox {
	private static final boolean sandboxEnable = Boolean.valueOf(Settings.getFromJVMOrDefault("sandbox.enable", "false"));

	private static Function<Object, RequestInfo> requestParser = null;

	private static RequestRecord requestRecord = null;

	public static void start(Function<Object, RequestInfo> requestParser) {
		if (ToySandbox.sandboxEnable) {
			if (ToySandbox.requestParser == null && ToySandbox.requestRecord == null) {
				ToySandbox.requestParser = requestParser;
				ToySandbox.requestRecord = new RequestRecord();
			}
		}
	}

	public static void onRequestReceive(Object request) {
		if (ToySandbox.sandboxEnable) {
			RequestInfo requestInfo = ToySandbox.requestParser.apply(request);
			Long waitingTimeMilli = ToySandbox.requestRecord.getPenaltyMilli(requestInfo).longValue();
			ToySandbox.doWaiting(waitingTimeMilli);
		}
	}

	public static void onResponseSend(Object request) {
		if (ToySandbox.sandboxEnable) {
			ToySandbox.requestRecord.updateRequestRecord(ToySandbox.requestParser.apply(request));
		}
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
