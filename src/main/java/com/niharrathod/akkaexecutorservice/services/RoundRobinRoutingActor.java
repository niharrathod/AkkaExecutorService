package com.niharrathod.akkaexecutorservice.services;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import com.niharrathod.akkaexecutorservice.executors.TaskWrapper;

import java.util.LinkedList;
import java.util.List;

public class RoundRobinRoutingActor extends AbstractLoggingActor {

    Router router;

    public RoundRobinRoutingActor(int taskActorCount) {
        List<Routee> routees = new LinkedList<Routee>();
        for (int i = 0; i < taskActorCount; i++) {
            ActorRef r = getContext().actorOf(ExecutorActor.props());
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TaskWrapper.class, this::onTask)
                .build();
    }

    public void onTask(TaskWrapper taskWrapper) {
        router.route(taskWrapper, self());
    }

    public static Props props(int taskActorCount) {
        return Props.create(RoundRobinRoutingActor.class,taskActorCount);
    }

}
