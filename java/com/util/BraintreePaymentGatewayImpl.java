package com.util;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.braintreegateway.BraintreeGateway;

@Configurable
public class BraintreePaymentGatewayImpl implements BraintreePaymentGateway{
	

	@Value("${Environment}") 
	private String ENVIRONMENT;

	@Value("${Merchant_ID}") 
	private String MERCHANT_ID;
	
	@Value("${Public_Key}") 
	private String PUBLIC_KEY;
	
	@Value("${Private_Key}") 
	private String PRIVATE_KEY;


 	
	@Override
	public BraintreeGateway getGateway() {
		return BraintreePaymentUtil.getBraintreeGateway(ENVIRONMENT, MERCHANT_ID, PUBLIC_KEY, PRIVATE_KEY);
	}

	@Override
	public String getMerchantId() {
		return BraintreePaymentUtil.getMerchantId(MERCHANT_ID);
	}

	
}
