package com.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.WebhookNotification;
import com.braintreegateway.WebhookNotification.Kind;
import com.util.BraintreePaymentGateway;

/* This is a implementation class where you generate payment token */
@Service
public class BraintreeWebhookServiceImpl implements BraintreeWebhookService {
	@Autowired
	BraintreePaymentGateway gateway;
	
	@Override
	public void webhook(String bt_signature_param, String bt_payload_param)
	{
		// Set bt_signature_param and bt_payload_param to the "bt_signature" and "bt_payload" POST parameters
		WebhookNotification webhookNotification = gateway.getGateway().webhookNotification().parse(
		  bt_signature_param, bt_payload_param
		);

		Kind kind = webhookNotification.getKind();
		switch(kind)
		{
			case DISBURSEMENT:
				String id = webhookNotification.getDisbursement().getId();
				System.out.println("We got: DISBURSEMENT "+id);
				break;
			case DISBURSEMENT_EXCEPTION:
				id = webhookNotification.getDisbursement().getId();
				System.out.println("We got: DISBURSEMENT_EXCEPTION "+id);
				break;
			case TRANSACTION_DISBURSED:
				id = webhookNotification.getDisbursement().getId();
				System.out.println("We got: TRANSACTION_DISBURSED "+id);
				break;

			case DISPUTE_LOST:
				id = webhookNotification.getDispute().getId();
				System.out.println("We got: DISPUTE_LOST "+id);
				break;
			case DISPUTE_OPENED:
				id = webhookNotification.getDispute().getId();
				System.out.println("We got: DISPUTE_OPENED "+id);
				break;
			case DISPUTE_WON:
				id = webhookNotification.getDispute().getId();
				System.out.println("We got: DISPUTE_WON "+id);
				break;

			case SUBSCRIPTION_CANCELED:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_CANCELED "+id);
				break;
			case SUBSCRIPTION_CHARGED_SUCCESSFULLY:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_CHARGED_SUCCESSFULLY "+id);
				break;
			case SUBSCRIPTION_CHARGED_UNSUCCESSFULLY:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_CHARGED_UNSUCCESSFULLY "+id);
				break;
			case SUBSCRIPTION_EXPIRED:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_EXPIRED "+id);
				break;
			case SUBSCRIPTION_TRIAL_ENDED:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_TRIAL_ENDED "+id);
				break;
			case SUBSCRIPTION_WENT_ACTIVE:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_WENT_ACTIVE "+id);
				break;
			case SUBSCRIPTION_WENT_PAST_DUE:
				id = webhookNotification.getSubscription().getId();
				System.out.println("We got: SUBSCRIPTION_WENT_PAST_DUE "+id);
				break;
				
				
			case SUB_MERCHANT_ACCOUNT_APPROVED:
				id = webhookNotification.getMerchantAccount().getId();
				System.out.println("We got: SUB_MERCHANT_ACCOUNT_APPROVED "+id);
				break;
			case SUB_MERCHANT_ACCOUNT_DECLINED:
				id = webhookNotification.getMerchantAccount().getId();
				System.out.println("We got: SUB_MERCHANT_ACCOUNT_DECLINED "+id);
				break;
		}
		Calendar timestamp = webhookNotification.getTimestamp();

	}
	

}
