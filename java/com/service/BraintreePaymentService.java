package com.service;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;

public interface BraintreePaymentService {

    public Result<Transaction> checkout(String paymentMethodNonce, String amount, String customerId, String fee);
    
}
