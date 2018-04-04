package com.niharrathod.akkaexecutorservice.executors;


import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class RequestWithQueuedResponseTask<Request, Response> implements ITask {

    Request request;
    Function<Request, Response> function;
    BlockingQueue<Response> resultQueue;

    public RequestWithQueuedResponseTask(Function<Request, Response> function,
                                         Request request,
                                         BlockingQueue<Response> resultQueue) {
        this.resultQueue = resultQueue;
        this.request = request;
        this.function = function;
    }

    @Override
    public final void execute() {
        resultQueue.add(this.function.apply(request));
    }

}
