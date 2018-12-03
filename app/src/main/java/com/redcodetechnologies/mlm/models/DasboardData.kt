package com.redcodetechnologies.mlm.models

data class DasboardData(
        var totaldirectcommission: String? = null,//total direct comission
        var GetEwalletCredit: String? = null,
        var GetEWalletDebitSum: String? = null, //debit
        var GetPaymentsInProcessSum: String? = null,
        var GetUserTotalPackageCommission: String? = null,//
        var GetUserCurrentPackage: String? = null,
        var GetUserDownlineMembers: String? = null,//downlinmer
        var GetPayoutHistorySum: String? = null, //withdraw
        var GetUserTotalMatchingCommission: String? = null,//table coable
        var GetAllCurrentRewardInfo: String? = null,
        var GetEWalletSummarySponsorBonus: String? = null,//balance
        var GetTotalleftamount: String? = null,//balance
        var GetTotalrightamount: String? = null,//balance
        var GetTotalremainingleftamount: String? = null,//balance
        var GetTotalremainingrightamount: String? = null,//balance

        var GetAllTotalLeftUserPV: String? = null,//balance
        var GetAllTotalRightUserPV: String? = null//balance

)
