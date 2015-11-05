package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.PaymentMethod;
import com.braintreegateway.PaymentMethodRequest;
import com.braintreegateway.Plan;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.SubscriptionRequest;
import com.braintreegateway.exceptions.NotFoundException;
import com.util.BraintreePaymentGateway;

/* This is a implementation class where you can create and cancel subscription */
@Service
public class BraintreeSubscriptionServiceImpl implements BraintreeSubscriptionService {

	@Autowired
	BraintreePaymentGateway gateway;



	@Override
	public List<Plan> getAllPlans() {
		List<Plan> plans = gateway.getGateway().plan().all();
		return plans;
	}
	
	
	@Override
	public Result<Customer> createUpdateCustomer(String customerId){

		Customer customer = //get your customer
		Result<Customer> result = null;


		CustomerRequest request = new CustomerRequest()
		//.paymentMethodNonce(nonce)
		.id(String.valueOf(customer.getId()))
	    .customerId(String.valueOf(customer.getId()))
		.firstName(customer.getFirstName())
		.lastName(customer.getLastName())
		.email(customer.getEmail())
		.phone(customer.getPhone());


		try
		{
			Customer cust = gateway.getGateway().customer().find(customerId);
			result = gateway.getGateway().customer().update(customerId, request);
			
		}catch(NotFoundException e)
		{
		// for creating customer id inside the braintree
			result = gateway.getGateway().customer().create(request);
		}
		return result;
	}
	
	@Override
	public Result<Subscription> createSubscription(String planId, String customerId, String payment_nonce) 
	{
	
		//Add or update customer on braintree
		Result<Customer> custResult = createUpdateCustomer(customerId);

		//Generate payment token for subscription service
		PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest().
		            customerId(customerId).
		            paymentMethodNonce(payment_nonce);

        Result<? extends PaymentMethod> result = gateway.getGateway().paymentMethod().create(paymentMethodRequest);
        PaymentMethod paymentMethod = result.getTarget();
 		
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest()
		.paymentMethodNonce(payment_nonce)
		.paymentMethodToken(paymentMethod.getToken())
		.planId(planId)
		.descriptor()
//        .name("company*my product")
//        .phone("3125551212")
//        .url("company.com")
        .done();
		
		Result<Subscription> res = gateway.getGateway().subscription().create(subscriptionRequest);
		return res;
	}

}
