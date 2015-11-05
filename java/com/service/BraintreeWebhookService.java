package com.service;

public interface BraintreeWebhookService {

	public void webhook(String bt_signature_param, String bt_payload_param);
}
