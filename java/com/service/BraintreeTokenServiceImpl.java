package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.ClientTokenRequest;
import com.util.BraintreePaymentGateway;

/* This is a implementation class where you generate client token */
@Service
public class BraintreeTokenServiceImpl implements BraintreeTokenService {

	@Autowired
	BraintreePaymentGateway gateway;

	@Override
	public String getClientToken(String customerId) 
	{
		ClientTokenRequest clientTokenRequest = new ClientTokenRequest().customerId(customerId);
		String clientToken = gateway.getGateway().clientToken().generate(clientTokenRequest);
		return clientToken;
	}

}