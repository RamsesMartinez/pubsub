/*
 * Copyright 2016 Google Inc.
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

package com.fygsolutions.test.pubsub;


// [START pubsub_quickstart_create_topic]
// Imports the Google Cloud client library

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.PermissionDeniedException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ProjectTopicName;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateTopic_01 {
  private static final String PROJECT_ID = "fintech-desarrollo-mx";
  private static final String TOPIC_ID = "vta-topic-test";

  public static void main(String... args) throws IOException {
    createTopic();
  }

  /**
   * Create a topic.
   *
   * @throws IOException exception thrown if operation is unsuccessful
   */
  private static void createTopic() throws IOException {
    CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(
            ServiceAccountCredentials.fromStream(
                    new FileInputStream("C:\\fintech-desarrollo-mx-pubsub.json")));

    // Create a new topic
    ProjectTopicName projectTopicName = ProjectTopicName.of(PROJECT_ID, TOPIC_ID);

    try (TopicAdminClient topicAdminClient = TopicAdminClient.create(
            TopicAdminSettings.newBuilder()
                    .setCredentialsProvider(credentialsProvider)
                    .build()
    )) {
      topicAdminClient.createTopic(projectTopicName);
      System.out.printf("Topic %s:%s created.\n", projectTopicName.getProject(), projectTopicName.getTopic());
    } catch (ApiException e) {
      // example : code = ALREADY_EXISTS(409) implies topic already exists
      System.out.println(e.getStatusCode().getCode());
      System.out.println(e.isRetryable());
      System.out.println("Intentando eliminar Topico");
      try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
        ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_ID);
        System.out.println(topicName);
        topicAdminClient.deleteTopic(topicName);
        System.out.println("Creando tópico después de borrado " + topicName.toString());
        createTopic();
      } catch (PermissionDeniedException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}
// [END pubsub_quickstart_create_topic]