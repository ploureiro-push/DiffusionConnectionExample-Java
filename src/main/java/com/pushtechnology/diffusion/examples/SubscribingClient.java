/*******************************************************************************
 * Copyright (C) 2014, 2018 Push Technology Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.pushtechnology.diffusion.examples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.Pings;
import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.client.features.Topics.ValueStream;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;
/**
 * A client that subscribes to the topic 'foo/counter.
 *
 * @author Push Technology Limited
 * @since 5.5
 */
public class SubscribingClient {
    private static final Logger LOG =
        LoggerFactory.getLogger(SubscribingClient.class);
    /**
     * Main.
     */
    public static void main(String... arguments) throws Exception {
        // Connect anonymously
        // Replace 'host' with your hostname
        LOG.info("Connecting...");
        final Session session = Diffusion.sessions().open("ws://localhost:8080");
        LOG.info("Session created: " + session.getSessionId());
        Thread.sleep(6000);
        
        /*
        final Topics topics = session.feature(Topics.class);
        topics.addStream("*Demos/Sportsbook/Football/England//", JSON.class, new ValueStreamPrintLn());
        topics.subscribe("*Demos/Sportsbook/Football/England//")
            .whenComplete((voidResult, exception) -> {
                if (exception != null) {
                    LOG.info("subscription failed", exception);
                }
            });
        */
        while(true)
        {
            final Pings pings = session.feature(Pings.class);
            LOG.info("Session state: " + session.getState());
            LOG.info("Pinging Server: " + pings.pingServer().get());
            //
            Thread.sleep(3000);
        }
    }
    /**
     * A topic stream that prints updates to the console.
     */
    private static class ValueStreamPrintLn extends ValueStream.Default<Long> {
        @Override
        public void onValue(
            String topicPath,
            TopicSpecification specification,
            Long oldValue,
            Long newValue) {
            System.out.println(topicPath + ":   " + newValue);
        }
    }
}