package toysandbox;

import java.util.function.Consumer;
import java.util.function.Function;

public class ToySandbox {
	private static Function<Object, RequestInfo> requestParser;

	private static RequestRecord requestRecord;

	public static void start(Function<Object, RequestInfo> requestParser) {
		ToySandbox.requestParser = requestParser;
		ToySandbox.requestRecord = new RequestRecord();
	}

	public static void setRequestSender(Consumer<Object> requestSender) { }

	public static void onRequestReceive(Object task, Object request) {
		RequestInfo requestInfo = ToySandbox.requestParser.apply(request);
		Long waitingTimeMilli = ToySandbox.requestRecord.getWaitingTimeMilli(requestInfo);
		ToySandbox.doWaiting(waitingTimeMilli);
	}

	private static void doWaiting(Long waitingTimeMilli) {
		try {
			Thread.sleep(waitingTimeMilli);
		}
		catch (InterruptedException e) {
			System.out.println(String.format("Waiting request interrupted by %s", e.toString()));
		}
	}

	public static void doStart() { }

	public static void stop() { }

	public static void onTaskCreate(Object task, Object request) { }

	public static void onTaskExit(Object task) { }

	public static void onTaskFinishInThread() { }

	public static void onTaskQueueInThread(Runnable runnable) { }

	public static void onTaskStartInThread(Runnable runnable) { }

	public static void addTaskWork(Long work) { }

	public static void finishTaskWork(Long work) { }

	public static void startCPUUsing(String name) { }

	public static void endCPUUsing(String name) { }

	public static void addMemoryUsage(
			String name, Long evictTime, Long totalMemory, Long usingMemory, Long reuseMemory) { }

	public static void startQueueWait(String name) { }

	public static void endQueueWait(String name) { }

	public static void startQueueOccupy(String name) { }

	public static void endQueueOccupy(String name) { }

}
