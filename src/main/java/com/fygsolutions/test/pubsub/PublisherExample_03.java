package com.fygsolutions.test.pubsub;

/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


// [START pubsub_quickstart_publisher]

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class PublisherExample_03 {
  private static final String PROJECT_ID = "fintech-desarrollo-mx";
  private static final String TOPIC_ID = "vta-topic-test";

  /** Publish messages to a topic.
   * @param args topic name, number of messages
   */
  public static void main(String... args) throws Exception {
    // Many number
    int messageCount = 5;
    ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_ID);
    CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(
            ServiceAccountCredentials.fromStream(
                    new FileInputStream("C:\\fintech-desarrollo-mx-pubsub.json")));
    Publisher publisher = null;
    List<ApiFuture<String>> futures = new ArrayList<>();

    try {
      // Create a publisher instance with default settings bound to the topic
      publisher = Publisher.newBuilder(topicName)
              .setCredentialsProvider(credentialsProvider)
              .build();

      for (int i = 0; i < messageCount; i++) {
        String message = "message-" + i;

        // convert message to bytes
        ByteString data = ByteString.copyFromUtf8(message);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(data)
                .build();

        // Schedule a message to be published. Messages are automatically batched.
        ApiFuture<String> future = publisher.publish(pubsubMessage);
        futures.add(future);
      }
    } finally {
      // Wait on any pending requests
      List<String> messageIds = ApiFutures.allAsList(futures).get();

      for (String messageId : messageIds) {
        System.out.println("Id del mesanje obtenido: " + messageId);
      }

      if (publisher != null) {
        // When finished with the publisher, shutdown to free up resources.
        publisher.shutdown();
      }
    }
  }
}
// [END pubsub_quickstart_quickstart]