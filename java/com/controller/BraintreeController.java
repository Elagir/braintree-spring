package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;
import com.service.BraintreePaymentService;
import com.service.BraintreeSubscriptionService;
import com.service.BraintreeTokenService;
import com.service.BraintreeWebhookService;
import com.service.ChargeTypeService;

/* This is a class where you provide braintree service calls */

@RestController
public class BraintreeController {

	@Autowired
	private BraintreeTokenService tokenService;

	@Autowired
	private BraintreePaymentService paymentService;

	@Autowired
	private BraintreeSubscriptionService subscriptionService;

	@Autowired
	private BraintreeWebhookService webhookService;

	@Autowired
	private ChargeTypeService typeChargeService;

	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;

	@Autowired
	SecurityContextRepository repository;
	
	@RequestMapping(value = "/getAmount/{chargeType}", method = RequestMethod.GET)
	public @ResponseBody Double getAmount(
			@PathVariable(value = "chargeType") String chargeType) {
		Double amount = typeChargeService.getChargeAmmountByType(chargeType);
		return amount;
	}

	@RequestMapping(value = "/getClientToken", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getClientToken(
			@RequestParam(value = "customerID", required = false, defaultValue = "") String customerID) {
		String clientToken = tokenService.getClientToken(customerID);
		return clientToken;
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody Response checkout(
			@RequestParam("payment_method_nonce") String payment_method_nonce,
			@RequestParam("amount") String amount,
			@RequestParam("customerId") String customerId) {
		// 0.00 is for service fee, it could be adjusted as needed
		Result<Transaction> result = paymentService.checkout(
				payment_method_nonce, amount, customerId, "0.00");
		if (result.isSuccess()) {
			return Util.getResponse(SERVICE_SUCCESS,
					"Successfull checkout", "checkout");
		} else {
			StringBuilder sb = new StringBuilder();

			for (ValidationError error : result.getErrors()
					.getAllValidationErrors()) {
				sb.append(error.getCode()).append(":")
						.append(error.getMessage()).append(";");
			}
			return Util.getResponse(SERVICE_FAILURE,
					result.getMessage() + ";" + sb.toString(), "checkout");
		}
	}

	@RequestMapping(value = "/createSubscription", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createSubscription(
			@RequestParam("payment_method_nonce") String payment_method_nonce,
			@RequestParam(value = "planId", required = true) String planId,
			@RequestParam(value = "customerId", required = true) String customerId) {

		Result<Subscription> result = subscriptionService.createSubscription(
				planId, customerId, payment_method_nonce);
		if (result.isSuccess()) {
			return Util.getResponse(SERVICE_SUCCESS,
					"Successfull subscription", "createSubscription");
		} else {
			StringBuilder sb = new StringBuilder();

			for (ValidationError error : result.getErrors()
					.getAllValidationErrors()) {
				sb.append(error.getCode()).append(":")
						.append(error.getMessage()).append(";");
			}

			return Util.getResponse(SERVICE_FAILURE,
					result.getMessage() + ";" + sb.toString(),
					"createSubscription");
		}
	}

	@RequestMapping(value = "/webhooks", method = RequestMethod.POST)
	public void webhook(
			@RequestParam(value = "bt_signature", required = true) String bt_signature,
			@RequestParam(value = "bt_payload", required = true) String bt_payload,
			@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("Parameters:"+username+" "+password+" "+bt_signature+" "+bt_payload);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		Authentication auth = authenticationManager.authenticate(token);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		repository.saveContext(SecurityContextHolder.getContext(), request, response);
		
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		
		webhookService.webhook(bt_signature, bt_payload);

	}
}
