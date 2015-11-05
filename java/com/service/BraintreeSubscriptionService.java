package com.service;

import java.util.List;

import com.braintreegateway.Customer;
import com.braintreegateway.Plan;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;

/* This is an interface where you can create and cancel subscription */
public interface BraintreeSubscriptionService {
	
	public List<Plan> getAllPlans();
    
 	public Result<Customer> createUpdateCustomer(String customerId);

    public Result<Subscription> createSubscription(String planId, String customerId, String payment_nonce);

 }
