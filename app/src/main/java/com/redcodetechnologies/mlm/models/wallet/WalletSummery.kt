package com.redcodetechnologies.mlm.models.wallet

data class WalletSummery (var bonus:String, var witdraw:String) {
    override fun toString(): String {
        return "WalletSummery(Catagory='$bonus', Balance='$witdraw')"
    }
}



