package com.redcodetechnologies.mlm.models

data class Report(
        var WithdrawalFundId: String? = null,
        var UserId: String? = null,
        var AccountNumber: String? = null,
        var BankName: String? = null,
        var WithdrawalFundMethod: String? = null,
        var AmountPayble: String? = null,
        var WithdrawalFundDescription: String? = null,
        var WithdrawalFundCharge: String? = null,
        var Country: String? = null,
        var RequestedDate: String? = null,
        var ApprovedDate: String? = null,
        var PaidDate: String? = null,
        var RejectedDate: String? = null,
        var IsActive: String? = null,
        var IsPending: String? = null,
        var IsApproved: String? = null,
        var IsPaid: String? = null,
        var IsRejected: String? = null,
        var Username: String? = null

        )

