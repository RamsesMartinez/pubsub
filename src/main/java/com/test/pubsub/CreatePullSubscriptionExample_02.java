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

package com.test.pubsub;


// [START pubsub_quickstart_create_subscription]

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;

public class CreatePullSubscriptionExample_02 {

  /**
   * Create a pull subscription.
   *
   * @param args topic subscriptionId
   * @throws Exception exception thrown if operation is unsuccessful
   */
  public static void main(String... args) throws Exception {

    // Your Google Cloud Platform project ID
    String projectId = ServiceOptions.getDefaultProjectId();

    // Your topic ID, eg. "my-topic"
    String topicId = "my-topic";

    // Your subscription ID eg. "my-sub"
    String subscriptionIdA = "my-sub-a";
    String subscriptionIdB = "my-sub-b";

    ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);

    // Create a new subscription for A
    ProjectSubscriptionName subscriptionNameA = ProjectSubscriptionName.of(
        projectId, subscriptionIdA);
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      // create a pull subscription with default acknowledgement deadline (= 10 seconds)
      Subscription subscriptionA =
          subscriptionAdminClient.createSubscription(
              subscriptionNameA, topicName, PushConfig.getDefaultInstance(), 0);
      System.out.printf(
          "Subscription A %s:%s created.\n",
          subscriptionNameA.getProject(), subscriptionNameA.getSubscription());
    } catch (ApiException e) {
      // example : code = ALREADY_EXISTS(409) implies subscription already exists
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
    }
    
    // Create a new subscription for B
    ProjectSubscriptionName subscriptionNameB = ProjectSubscriptionName.of(
        projectId, subscriptionIdB);
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      // create a pull subscription with default acknowledgement deadline (= 10 seconds)
      Subscription subscriptionB =
          subscriptionAdminClient.createSubscription(
              subscriptionNameB, topicName, PushConfig.getDefaultInstance(), 0);
      System.out.printf(
          "Subscription B %s:%s created.\n",
          subscriptionNameB.getProject(), subscriptionNameB.getSubscription());
    } catch (ApiException e) {
      // example : code = ALREADY_EXISTS(409) implies subscription already exists
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
    }

  }
}
// [END pubsub_quickstart_create_subscription]