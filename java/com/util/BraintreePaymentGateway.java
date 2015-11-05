package com.util;

import com.braintreegateway.BraintreeGateway;

public interface BraintreePaymentGateway {
	
 	public BraintreeGateway getGateway();

 	public String getMerchantId();

}
