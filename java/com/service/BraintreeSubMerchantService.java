package com.service;

import com.braintreegateway.MerchantAccount;
import com.braintreegateway.Result;

public interface BraintreeSubMerchantService {
    
	public Result<MerchantAccount> createSubMerchant(String customerId);
	
	public Result<MerchantAccount> createSubMerchant(String customerId, String businessId);
	
	public MerchantAccount findSubMerchant(String customerId);

}
