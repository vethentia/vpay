/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var IE;

var vpayID;
var merchantID;
var transactionID;

var chargeToken;
var chargeAmount;

var purchaseItems;

var chargeRequest;
var chargeResultField;

function doInitCharge() {
    
    // Setup the charge payment result received
    chargeResultField = document.getElementById("chargeResult");
}

function doCodeCommand(tid, code){
    
    chargeResultField = document.getElementById("chargeResult");
    if (tid !== "") {
        
      var url = "https://vethentia.azurewebsites.net/api/payments/coderxresp?msgId=19" + 
                "&tid=" + tid + "&status=0";
        
      chargeRequest = getChargeRequest();
      chargeRequest.open("POST", url, true);
      chargeRequest.onreadystatechange = chargeResult;
      chargeRequest.send(null);
    }
}

function doChargePayment(token, amount) {
    
    chargeResultField = document.getElementById("chargeResult");
    
    chargeToken = token;
    chargeAmount = amount;
    
    if(chargeToken !== "") {
        var url = "ChargePayment?action=chargestripe&token=" + chargeToken + "&amount=" + chargeAmount;
        chargeRequest = getChargeRequest();
        chargeRequest.open("POST", url, true);
        chargeRequest.onreadystatechange = chargeResult;
        chargeRequest.send(null);
    }
}

function getChargeRequest() {
    
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') !== -1) {
            IE = true;
        }
        return new XMLHttpRequest();
        
    } else if (window.ActiveXObject) {
        IE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function chargeResult() {
    
    chargeResultField.innerHTML = "Charge Completed";
    
    // alert("chargeResult() got called.");
}
