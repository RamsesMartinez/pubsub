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

// [START pubsub_quickstart_subscriber]


import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.FileInputStream;

public class Subscription_04 {
	private static final String PROJECT_ID = "findep-desarrollo-170215";
	private static final String SUBSCRIPTION_ID = "gcr-pago-sevicios-vta";

	static class MessageReceiverExample implements MessageReceiver {

		@Override
		public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
			System.out.println(
					"Message Id: " + message.getMessageId() + " Data: " + message.getData().toStringUtf8());
			// Ack only after all work for the message is complete.
			consumer.ack();
		}
	}

	/** Receive messages over a subscription. */
	public static void main(String... args) throws Exception {
		ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(PROJECT_ID, SUBSCRIPTION_ID);
		try {
			CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(
					ServiceAccountCredentials.fromStream(
							new FileInputStream("C:\\findep-desarrollo-170215-bc6001bfa109.json")));

			// create a subscriber bound to the asynchronous message receiver
			Subscriber subscriber = Subscriber.newBuilder(subscriptionName, new MessageReceiverExample())
					.setCredentialsProvider(credentialsProvider)
					.build();
			subscriber.startAsync().awaitRunning();
			// Allow the subscriber to run indefinitely unless an unrecoverable error occurs.
			subscriber.awaitTerminated();
		} catch (IllegalStateException e) {
			System.out.println("Subscriber unexpectedly stopped: " + e);
		}
	}
}
// [END pubsub_quickstart_subscriber]