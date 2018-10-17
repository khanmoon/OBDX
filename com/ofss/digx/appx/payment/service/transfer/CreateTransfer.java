package com.ofss.digx.appx.payment.service.transfer;

/**
*
* Script-Name: CreateTransfer
*/

import com.mastercard.api.core.ApiConfig;
import com.mastercard.api.core.exception.*;
import com.mastercard.api.core.model.*;
import com.mastercard.api.core.model.map.*;
import com.mastercard.api.core.security.oauth.OAuthAuthentication;
import com.mastercard.api.p2m.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class CreateTransfer {

 public static void main(String[] args) throws Exception {

   String consumerKey = "your consumer key";   // You should copy this from "My Keys" on your project page e.g. UTfbhDCSeNYvJpLL5l028sWL9it739PYh6LU5lZja15xcRpY!fd209e6c579dc9d7be52da93d35ae6b6c167c174690b72fa
   String keyAlias = "keyalias";   // For production: change this to the key alias you chose when you created your production key
   String keyPassword = "keystorepassword";   // For production: change this to the key alias you chose when you created your production key
   InputStream is = new FileInputStream("path to your .p12 private key file"); // e.g. /Users/yourname/project/sandbox.p12 | C:\Users\yourname\project\sandbox.p12
   ApiConfig.setAuthentication(new OAuthAuthentication(consumerKey, is, keyAlias, keyPassword));   // You only need to set this once
   ApiConfig.setDebug(true);   // Enable http wire logging
   ApiConfig.setSandbox(true); // For production: use ApiConfig.setSandbox(false);

   try {
     RequestMap map = new RequestMap();
     map.set("partnerId", "ptnr_BEeCrYJHh2BXTXPy_PEtp-8DBOo");
     map.set("merchant_transfer.transfer_reference", "4007826709474811576743651734315950554193");
     map.set("merchant_transfer.payment_type", "P2M");
     map.set("merchant_transfer.transfer_amount.value", "18");
     map.set("merchant_transfer.transfer_amount.currency", "USD");
     map.set("merchant_transfer.payment_origination_country", "USA");
     map.set("merchant_transfer.sender_account_uri", "pan:5184680430000006;exp=2077-08;cvc=123");
     map.set("merchant_transfer.digital_account_reference_number", "pan:5234568000001234");
     map.set("merchant_transfer.sender.first_name", "John");
     map.set("merchant_transfer.sender.middle_name", "Tyler");
     map.set("merchant_transfer.sender.last_name", "Jones");
     map.set("merchant_transfer.sender.address.line1", "21 Broadway");
     map.set("merchant_transfer.sender.address.line2", "Apartment A-6");
     map.set("merchant_transfer.sender.address.city", "OFallon");
     map.set("merchant_transfer.sender.address.country_subdivision", "MO");
     map.set("merchant_transfer.sender.address.postal_code", "63368");
     map.set("merchant_transfer.sender.address.country", "USA");
     map.set("merchant_transfer.sender.phone", "11234565555");
     map.set("merchant_transfer.sender.email", "John.Jones123@abcmail.com");
     map.set("merchant_transfer.recipient_account_uri", "pan:5184680430000014;exp=2077-08;cvc=123");
     map.set("merchant_transfer.recipient.first_name", "Jane");
     map.set("merchant_transfer.recipient.middle_name", "Tyler");
     map.set("merchant_transfer.recipient.last_name", "Smith");
     map.set("merchant_transfer.recipient.address.line1", "1 Main St");
     map.set("merchant_transfer.recipient.address.line2", "Apartment 9");
     map.set("merchant_transfer.recipient.address.city", "OFallon");
     map.set("merchant_transfer.recipient.address.country_subdivision", "MO");
     map.set("merchant_transfer.recipient.address.postal_code", "63368");
     map.set("merchant_transfer.recipient.address.country", "USA");
     map.set("merchant_transfer.recipient.phone", "11234567890");
     map.set("merchant_transfer.recipient.email", "Jane.Smith123@abcmail.com");
     map.set("merchant_transfer.recipient.merchant_category_code", "3000");
     map.set("merchant_transfer.reconciliation_data.custom_field[0].name", "GHI");
     map.set("merchant_transfer.reconciliation_data.custom_field[0].value", "123");
     map.set("merchant_transfer.reconciliation_data.custom_field[1].name", "ABC");
     map.set("merchant_transfer.reconciliation_data.custom_field[1].value", "456");
     map.set("merchant_transfer.reconciliation_data.custom_field[2].name", "DEF");
     map.set("merchant_transfer.reconciliation_data.custom_field[2].value", "789");
     map.set("merchant_transfer.transaction_local_date_time", "2016-09-22T13:22:11-05:30");
     map.set("merchant_transfer.participant.card_acceptor_id", "1234567890ABCDE");
     map.set("merchant_transfer.participant.card_acceptor_name", "WELLS FARGO BANK NA");
     map.set("merchant_transfer.participation_id", "TERMINAL34728");
     map.set("merchant_transfer.additional_message", "mymessage");
     map.set("merchant_transfer.mastercard_assigned_id", "123456");
     MerchantTransferFundingAndPayment response = MerchantTransferFundingAndPayment.create(map);

     out(response, "merchant_transfer.id"); //-->mtrn_8HmnHRkW5CsHsKtIGEJ8azsrwnJ
     out(response, "merchant_transfer.resource_type"); //-->merchant_transfer
     out(response, "merchant_transfer.transfer_reference"); //-->4007826709474811576743651734315950554193
     out(response, "merchant_transfer.payment_type"); //-->P2M
     out(response, "merchant_transfer.sender_account_uri"); //-->pan:************0006
     out(response, "merchant_transfer.digital_account_reference_number"); //-->pan:************1234
     out(response, "merchant_transfer.payment_origination_country"); //-->USA
     out(response, "merchant_transfer.sender.first_name"); //-->John
     out(response, "merchant_transfer.sender.middle_name"); //-->Tyler
     out(response, "merchant_transfer.sender.last_name"); //-->Jones
     out(response, "merchant_transfer.sender.address.line1"); //-->21 Broadway
     out(response, "merchant_transfer.sender.address.line2"); //-->Apartment A-6
     out(response, "merchant_transfer.sender.address.city"); //-->OFallon
     out(response, "merchant_transfer.sender.address.country_subdivision"); //-->MO
     out(response, "merchant_transfer.sender.address.postal_code"); //-->63368
     out(response, "merchant_transfer.sender.address.country"); //-->USA
     out(response, "merchant_transfer.sender.phone"); //-->11234565555
     out(response, "merchant_transfer.sender.email"); //-->John.Jones123@abcmail.com
     out(response, "merchant_transfer.recipient_account_uri"); //-->pan:************0014
     out(response, "merchant_transfer.recipient.first_name"); //-->Jane
     out(response, "merchant_transfer.recipient.middle_name"); //-->Tyler
     out(response, "merchant_transfer.recipient.last_name"); //-->Smith
     out(response, "merchant_transfer.recipient.address.line1"); //-->1 Main St
     out(response, "merchant_transfer.recipient.address.line2"); //-->Apartment 9
     out(response, "merchant_transfer.recipient.address.city"); //-->OFallon
     out(response, "merchant_transfer.recipient.address.country_subdivision"); //-->MO
     out(response, "merchant_transfer.recipient.address.postal_code"); //-->63368
     out(response, "merchant_transfer.recipient.address.country"); //-->USA
     out(response, "merchant_transfer.recipient.phone"); //-->11234567890
     out(response, "merchant_transfer.recipient.email"); //-->John.Jones123@abcmail.com
     out(response, "merchant_transfer.recipient.merchant_category_code"); //-->3000
     out(response, "merchant_transfer.transfer_amount.value"); //-->18
     out(response, "merchant_transfer.transfer_amount.currency"); //-->USD
     out(response, "merchant_transfer.created"); //-->2016-09-22T13:22:11-05:30
     out(response, "merchant_transfer.transaction_history.resource_type"); //-->list
     out(response, "merchant_transfer.transaction_history.item_count"); //-->2
     out(response, "merchant_transfer.transaction_history.data.transaction[0].id"); //-->txn_KHesUdrpshiYXJVH_H702cCvtEK5
     out(response, "merchant_transfer.transaction_history.data.transaction[0].resource_type"); //-->transaction
     out(response, "merchant_transfer.transaction_history.data.transaction[0].account_uri"); //-->pan:************0006
     out(response, "merchant_transfer.transaction_history.data.transaction[0].transaction_amount.value"); //-->18
     out(response, "merchant_transfer.transaction_history.data.transaction[0].transaction_amount.currency"); //-->USD
     out(response, "merchant_transfer.transaction_history.data.transaction[0].network"); //-->MoneySend
     out(response, "merchant_transfer.transaction_history.data.transaction[0].network_status_code"); //-->00
     out(response, "merchant_transfer.transaction_history.data.transaction[0].network_status_description"); //-->Approved
     out(response, "merchant_transfer.transaction_history.data.transaction[0].type"); //-->FUNDING
     out(response, "merchant_transfer.transaction_history.data.transaction[0].create_timestamp"); //-->2016-08-29T01:07:37-05:30
     out(response, "merchant_transfer.transaction_history.data.transaction[0].status"); //-->APPROVED
     out(response, "merchant_transfer.transaction_history.data.transaction[0].status_reason"); //-->APPROVED
     out(response, "merchant_transfer.transaction_history.data.transaction[0].status_timestamp"); //-->2016-08-29T01:07:37-05:30
     out(response, "merchant_transfer.transaction_history.data.transaction[0].retrieval_reference"); //-->723411975497
     out(response, "merchant_transfer.transaction_history.data.transaction[0].system_trace_audit_number"); //-->975497
     out(response, "merchant_transfer.transaction_history.data.transaction[0].unique_reference_number"); //-->9261621456
     out(response, "merchant_transfer.transaction_history.data.transaction[1].id"); //-->txn_KHesUdrpshiYXJVH_H702cCvtEK5
     out(response, "merchant_transfer.transaction_history.data.transaction[1].resource_type"); //-->transaction
     out(response, "merchant_transfer.transaction_history.data.transaction[1].account_uri"); //-->pan:************0014
     out(response, "merchant_transfer.transaction_history.data.transaction[1].transaction_amount.value"); //-->18
     out(response, "merchant_transfer.transaction_history.data.transaction[1].transaction_amount.currency"); //-->USD
     out(response, "merchant_transfer.transaction_history.data.transaction[1].network"); //-->MoneySend
     out(response, "merchant_transfer.transaction_history.data.transaction[1].network_status_code"); //-->00
     out(response, "merchant_transfer.transaction_history.data.transaction[1].network_status_description"); //-->Approved
     out(response, "merchant_transfer.transaction_history.data.transaction[1].type"); //-->PAYMENT
     out(response, "merchant_transfer.transaction_history.data.transaction[1].create_timestamp"); //-->2016-08-29T01:07:37-05:30
     out(response, "merchant_transfer.transaction_history.data.transaction[1].status"); //-->APPROVED
     out(response, "merchant_transfer.transaction_history.data.transaction[1].status_reason"); //-->APPROVED
     out(response, "merchant_transfer.transaction_history.data.transaction[1].status_timestamp"); //-->2016-08-29T01:07:37-05:30
     out(response, "merchant_transfer.transaction_history.data.transaction[1].retrieval_reference"); //-->723411975497
     out(response, "merchant_transfer.transaction_history.data.transaction[1].system_trace_audit_number"); //-->975497
     out(response, "merchant_transfer.transaction_history.data.transaction[1].unique_reference_number"); //-->9261621456
     out(response, "merchant_transfer.reconciliation_data.custom_field[0].name"); //-->GHI
     out(response, "merchant_transfer.reconciliation_data.custom_field[0].value"); //-->123
     out(response, "merchant_transfer.reconciliation_data.custom_field[1].name"); //-->ABC
     out(response, "merchant_transfer.reconciliation_data.custom_field[1].value"); //-->456
     out(response, "merchant_transfer.reconciliation_data.custom_field[2].name"); //-->DEF
     out(response, "merchant_transfer.reconciliation_data.custom_field[2].value"); //-->789
     out(response, "merchant_transfer.original_status"); //-->APPROVED
     out(response, "merchant_transfer.status"); //-->APPROVED
     out(response, "merchant_transfer.status_timestamp"); //-->2016-08-29T01:07:37-05:00
     out(response, "merchant_transfer.transaction_local_date_time"); //-->2016-09-22T13:22:11-05:30
     out(response, "merchant_transfer.mastercard_assigned_id"); //-->123456
     out(response, "merchant_transfer.participant.card_acceptor_name"); //-->WELLS FARGO BANK NA
     // This sample shows looping through merchant_transfer.transaction_history.data.transaction
     System.out.println("This sample shows looping through merchant_transfer.transaction_history.data.transaction");
     for(Map<String,Object> item : (List<Map<String, Object>>) response.get("merchant_transfer.transaction_history.data.transaction")) {
         out(item, "id");
         out(item, "resource_type");
         out(item, "account_uri");
         out(item, "transaction_amount");
         out(item, "network");
         out(item, "network_status_code");
         out(item, "network_status_description");
         out(item, "type");
         out(item, "create_timestamp");
         out(item, "status");
         out(item, "status_reason");
         out(item, "status_timestamp");
         out(item, "retrieval_reference");
         out(item, "system_trace_audit_number");
         out(item, "unique_reference_number");
     }

   } catch (ApiException e) {
     err("HttpStatus: "+e.getHttpStatus());
     err("Message: "+e.getMessage());
     err("ReasonCode: "+e.getReasonCode());
     err("Source: "+e.getSource());
   }
 }

 public static void out(SmartMap response, String key) {
   System.out.println(key+"-->"+response.get(key));
 }

 public static void out(Map<String,Object> map, String key) {
   System.out.println(key+"--->"+map.get(key));
 }

 public static void err(String message) {
   System.err.println(message);
 }
}
