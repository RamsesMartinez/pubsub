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

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;

import java.io.FileInputStream;

public class CreatePullSubscriptionExample_02 {
  private static final String PROJECT_ID = "findep-desarrollo-170215";
  private static final String TOPIC_ID = "PagoSeviciosVTA";
  private static final String SUBSCRIPTION_ID = "gcr-pago-sevicios-vta";

  /**
   * Create a pull subscription.
   *
   * @param args topic subscriptionId
   * @throws Exception exception thrown if operation is unsuccessful
   */
  public static void main(String... args) throws Exception {
    ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_ID);

    CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(
            ServiceAccountCredentials.fromStream(
                    new FileInputStream("C:\\findep-desarrollo-170215-bc6001bfa109.json")));

    // Create a new subscription
    ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(
            PROJECT_ID, SUBSCRIPTION_ID);

    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(
            SubscriptionAdminSettings.newBuilder()
                    .setCredentialsProvider(credentialsProvider)
                    .build()
    )) {

      // create a pull subscription with default acknowledgement deadline (= 10 seconds)
      Subscription subscription =
          subscriptionAdminClient.createSubscription(
                  subscriptionName, topicName, PushConfig.getDefaultInstance(), 0);
      System.out.printf(
          "Subscription %s:%s created.\n",
              subscriptionName.getProject(), subscriptionName.getSubscription());
    } catch (ApiException e) {
      // example : code = ALREADY_EXISTS(409) implies subscription already exists
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
    }

  }
}
// [END pubsub_quickstart_create_subscription]