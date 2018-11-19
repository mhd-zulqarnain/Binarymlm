package com.redcodetechnologies.mlm.models.wallet

data class WalletSummery (var Catagory:String, var Balance:String) {
    override fun toString(): String {
        return "WalletSummery(Catagory='$Catagory', Balance='$Balance')"
    }
}



