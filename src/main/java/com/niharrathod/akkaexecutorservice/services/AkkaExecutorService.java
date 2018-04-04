package com.niharrathod.akkaexecutorservice.services;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.niharrathod.akkaexecutorservice.executors.TaskWrapper;
import com.niharrathod.akkaexecutorservice.executors.ITask;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

public class AkkaExecutorService {

    private ActorSystem actorSystem;
    private int taskActorCount;
    private ActorRef routingActorRef;

    public AkkaExecutorService(int taskActorCount) {
        this.actorSystem = ActorSystem.create("AkkaExecutorService");
        this.taskActorCount = taskActorCount;
        routingActorRef = actorSystem.actorOf(RoundRobinRoutingActor.props(taskActorCount));
    }

    public void execute(ITask executor) {
        TaskWrapper taskWrapper = new TaskWrapper(executor);
        execute(taskWrapper);
    }

    public <Request> void execute(Consumer<Request> consumer, Request request) {
        TaskWrapper taskWrapper = new TaskWrapper(consumer, request);
        execute(taskWrapper);
    }


    public <Request, Response> void execute(Function<Request, Response> function,
                                            Request request,
                                            BlockingQueue<Response> resultQueue) {
        TaskWrapper taskWrapper = new TaskWrapper(function, request, resultQueue);
        execute(taskWrapper);
    }

    public void execute(TaskWrapper taskWrapper) {
        routingActorRef.tell(taskWrapper, ActorRef.noSender());
    }

    public void execute(List<TaskWrapper> taskWrapperList) {
        for (TaskWrapper taskWrapper : taskWrapperList) {
            routingActorRef.tell(taskWrapper, ActorRef.noSender());
        }
    }
}
