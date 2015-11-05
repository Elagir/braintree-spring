package com.util;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;


public class BraintreePaymentUtil {	

	
	public static BraintreeGateway getBraintreeGateway(String environment, 
			String merchant_id, String public_key, String private_key)
	{
		BraintreeGateway gateway = new BraintreeGateway(
				getEnvironment(environment),
				merchant_id, 
				public_key, 
				private_key 
				);
		return gateway;
	}

    public static Environment getEnvironment(String environment)
    {
    	Environment env = null;
    	
		switch(environment)
		{
			case "Environment.SANDBOX":
				env=Environment.SANDBOX;
				break;
			case "Environment.DEVELOPMENT":
				env=Environment.DEVELOPMENT;
				break;
			case "Environment.PRODUCTION":
				env=Environment.PRODUCTION;
				break;
		}
		return env;
    }

	public static String getMerchantId(String merchantId)
	{
		return merchantId;
	}
	}
