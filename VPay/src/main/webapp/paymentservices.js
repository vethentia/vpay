/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var req;
var isIE;

var paymentParameters;
var paymentResponseField;

function init() {
    paymentResponseField = document.getElementById("paymentResponse");
    
}


function doPaymentAuthorizeRequest() { 
        
    alert("doPaymentAuthorizeRequest() got called.");
    
    var url = "paymentRequest?id=" + escape(paymentResponseField.value);
    req = initRequest();
    req.open("POST", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') !== -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    
    alert("callback() for Stripe's Charge got called.");
}
