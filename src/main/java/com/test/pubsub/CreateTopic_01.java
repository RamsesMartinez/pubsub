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

package com.test.pubsub;


// [START pubsub_quickstart_create_topic]
// Imports the Google Cloud client library

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectTopicName;

public class CreateTopic_01 {

  /**
   * Create a topic.
   *
   * @param args topicId
   * @throws Exception exception thrown if operation is unsuccessful
   */
  public static void main(String... args) throws Exception {

    // Your Google Cloud Platform project ID
    String projectId = ServiceOptions.getDefaultProjectId();

    // Your topic ID, eg. "my-topic"
    String topicId = "my-topic";

    // Create a new topic
    ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      topicAdminClient.createTopic(topic);
      System.out.printf("Topic %s:%s created.\n", topic.getProject(), topic.getTopic());
    } catch (ApiException e) {
      // example : code = ALREADY_EXISTS(409) implies topic already exists
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
    }
  }
}
// [END pubsub_quickstart_create_topic]