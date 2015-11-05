package com.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.util.BraintreePaymentGateway;

/* This is a implementation class where you generate payment token */
@Service
public class BraintreePaymentServiceImpl implements BraintreePaymentService {
	@Autowired
	BraintreePaymentGateway gateway;
	
	
	@Override
	public Result<Transaction> checkout(String nonceFromTheClient, String amount, String customerId, String fee)
	{
		Customer customer = //get your customer;
		
		TransactionRequest request = new TransactionRequest().
			amount(new BigDecimal(amount)).
			customer().
            firstName(customer.getFirstName()).
            lastName(customer.getLastName()).
            email(customer.getEmail()).
            phone(customer.getPhone()).
            done().
        billingAddress().
            firstName(customer.getFirstName()).
            lastName(customer.getLastName()).
            streetAddress(customer.getAddress()).
            locality(customer.getCity()).
            region(customer.getState()).
            postalCode(customer.getPostalcode()).
//            countryName("United States of America").
//            countryCodeAlpha2("US").
            countryCodeAlpha3(customer.getCountry()).
//            countryCodeNumeric("840").
            done().
            customerId(customerId).
            customer().
            email(customer.getEmail()).
            firstName(customer.getFirstName()).
            lastName(customer.getLastName()).
            email(customer.getEmail()).
            done().
        paymentMethodNonce(nonceFromTheClient).
        customerId(customerId).
        serviceFeeAmount(new BigDecimal(fee)).
        options().
//           storeInVaultOnSuccess(true). we ask for payment method each time
           submitForSettlement(true).
           done();

		Result<Transaction> result = gateway.getGateway().transaction().sale(request);
		return result;
	}
	

}
