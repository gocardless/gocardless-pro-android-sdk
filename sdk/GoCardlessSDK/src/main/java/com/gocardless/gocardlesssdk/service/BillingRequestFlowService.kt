package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.network.GoCardlessApi

/**
 * Billing Request Flows can be created to enable a payer to authorise a payment
 * created for a scheme with strong payer authorisation (such as open banking single payments).
 */
class BillingRequestFlowService(private val goCardlessAPI: GoCardlessApi) {
}