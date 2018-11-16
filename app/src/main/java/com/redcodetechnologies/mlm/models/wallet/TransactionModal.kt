package com.redcodetechnologies.mlm.models.wallet

data class TransactionModal(
        var TransactionSource: String? = null,

        var TransactionName: String? = null,

        var AsscociatedMember: String? = null,

        var Amount: String? = null,

        var TransactionDate: String? = null,

        var Credit: String? = null,

        var Debit: String? = null,

        var UserId: String? = null,

        var IsPackageBonus: String? = null,

        var PackageId: String? = null,

        var IsMatchingBonus: String? = null,

        var IsParentBonus: String? = null,

        var IsWithdrawlRequestByUser: String? = null,

        var IsWithdrawlPaidByAdmin: String? = null,

        var AdminCredit: String? = null,

        var AdminDebit: String? = null,

        var AdminTransactionName: String? = null


        )
