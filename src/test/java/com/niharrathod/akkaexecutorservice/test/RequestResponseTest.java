package com.niharrathod.akkaexecutorservice.test;

import com.niharrathod.akkaexecutorservice.services.AkkaExecutorService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class RequestResponseTest {

    Logger logger = LoggerFactory.getLogger(RequestResponseTest.class);

    @Test
    public void testPingPongTest() {

        /*
         *  Function which takes the request and returns the response.
         *  AkkaExecutorService takes the request, function implementation and BlockingQueue as response queue.
         *  Internally, Request is passed to the function and response is handed over to the given blocking queue.
         * */

        Function<String, String> pingPongFunc = new Function<String, String>() {
            @Override
            public String apply(String s) {
                logger.info(s);
                return s + " done";
            }
        };

        AkkaExecutorService akkaExecutorService = new AkkaExecutorService(5);
        BlockingQueue<String> pingPongResultQueue = new LinkedBlockingQueue<>();

        akkaExecutorService.execute(pingPongFunc, "Hello world", pingPongResultQueue);
        akkaExecutorService.execute(pingPongFunc, "Hello world again", pingPongResultQueue);
        akkaExecutorService.execute(pingPongFunc, "Hello world again", pingPongResultQueue);

        for (int i = 0; i < 3; i++) {
            String response = null;
            try {
                response = pingPongResultQueue.take();
                logger.info("MainThread : {}", response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
