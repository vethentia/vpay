/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vethentia.vpay;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;


/**
 *
 * @author andy
 */
public class ChargeStripe {

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * Constructor
     */
    public ChargeStripe() {
        
        this.stripeToken = null;
        this.amount = 0;
    }
    
    public ChargeStripe(String token, long amount) {
        
        this.stripeToken = token;
        this.amount = amount;
    }
    
    /**
     * Create Charge
     * @throws com.stripe.exception.APIException
     */
    public void createCharge() throws APIException {
        
        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_mPKkiU0DeFqUJKO37eb6E6Vq";

        // Get the credit card details submitted by the form
        //String token = request.getParameter("stripeToken");
        String token = getStripeToken();
        long total = getAmount();
        
        // Create a charge: this will charge the user's card
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            
            chargeParams.put("amount", total); // Amount in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", token);
            chargeParams.put("description", "Secure Mobile Payment powered by Vethentia...");

            Charge charge = Charge.create(chargeParams);
            
            Logger.getLogger(ChargeStripe.class.getName()).log(Level.SEVERE, charge.getStatus());
            
        } catch (CardException | AuthenticationException | InvalidRequestException | APIConnectionException e) {
            
            // The card has been declined
            Logger.getLogger(ChargeStripe.class.getName()).log(Level.SEVERE, null, e);
        }  
    }
    
    /**
     * @return the stripeToken
     */
    public String getStripeToken() {
        return stripeToken;
    }

    /**
     * @param stripeToken the stripeToken to set
     */
    public void setStripeToken(String stripeToken) {
        
        this.stripeToken = stripeToken;
        
        try {
            
            createCharge();
            
        } catch (APIException ex) {
            
            Logger.getLogger(ChargeStripe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the apiSecretKey
     */
    public String getApiSecretKey() {
        return apiSecretKey;
    }

    /**
     * @param apiSecretKey the apiSecretKey to set
     */
    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }
    
    // Strip specific information
    private String apiSecretKey = "sk_test_mPKkiU0DeFqUJKO37eb6E6Vq";
    private String stripeToken; // received from mobile app.js
    private long amount; // in cents
}
