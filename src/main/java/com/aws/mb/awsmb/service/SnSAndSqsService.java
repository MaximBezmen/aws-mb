package com.aws.mb.awsmb.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.aws.mb.awsmb.entity.SnsSubscriber;
import com.aws.mb.awsmb.repository.SnsSubscriberRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnSAndSqsService {
    private static final String TOPIC_ARN = "m-bezmen-uploads-notification-topic";
    private static final String QUEUE_URL = "m-bezmen-uploads-notification-queue";
    private final AmazonSNS amazonSNS;
    private final AmazonSQSAsync amazonSQSAsync;
    private final SnsSubscriberRepository snsSubscriberRepository;


    public SnSAndSqsService(AmazonSNS amazonSNS, AmazonSQSAsync amazonSQSAsync, SnsSubscriberRepository snsSubscriberRepository) {
        this.amazonSNS = amazonSNS;
        this.amazonSQSAsync = amazonSQSAsync;
        this.snsSubscriberRepository = snsSubscriberRepository;
    }

    public String subscribeToTopic(String email) {

        SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, "email", email);

        SubscribeResult subscribeResponse = amazonSNS.subscribe(subscribeRequest);

        String subscriptionArn = subscribeResponse.getSubscriptionArn();

        SnsSubscriber snsSubscriber = new SnsSubscriber();
        snsSubscriber.setEmail(email);
        snsSubscriber.setSubscriptionArn(subscriptionArn);

        snsSubscriberRepository.save(snsSubscriber);

        return "Subscription ARN request is pending. To confirm the subscription, check your email.";
    }

    public String unsubscribeToTopic(String email) {

        SnsSubscriber snsSubscriber = snsSubscriberRepository.findByEmail(email);

        UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest(snsSubscriber.getSubscriptionArn());

        UnsubscribeResult unsubscribe = amazonSNS.unsubscribe(unsubscribeRequest);


        return "Unsubscription ARN request is done.";
    }


    private String sendToTopic(String message) {

        final PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, message);

        PublishResult publishResponse = amazonSNS.publish(publishRequest);

        return "Email sent to subscribers. Message-ID: " + publishResponse.getMessageId();
    }

    @Scheduled(cron = "0 * * * * *")
    public void readFromSqsAndSendToSns(){
        List<Message> messages = readFromSqs();
        messages.forEach(message -> sendToTopic(message.getBody()));
    }

    public void pushToSqs(String body) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(QUEUE_URL, body);

        amazonSQSAsync.sendMessageAsync(sendMessageRequest);
    }

    public List<Message> readFromSqs() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(QUEUE_URL);


        ReceiveMessageResult receiveMessageResult = amazonSQSAsync.receiveMessage(receiveMessageRequest);
        return receiveMessageResult.getMessages();
    }
}
