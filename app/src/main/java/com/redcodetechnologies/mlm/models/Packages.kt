package com.redcodetechnologies.mlm.models

data class Packages (var UserPackageId :String?=null,

                     var PackageName :String?=null,

                     var PackagePercent :String?=null,

                     var PackagePrice :String?=null,

                     var PackageValidity :String?=null,

                     var PackagePurchaseMethod :String?=null,

                     var PackageMinWithdrawalAmount :String?=null,

                     var PackageMaxWithdrawalAmount :String?=null,

                     var PackageId :String?=null,

                     var UserId :String?=null,

                     var IsInCurrentUse :String?=null,

                     var PurchaseDate :String?=null,
                     var BankSlipImage :String?=null,

                     var LastCommisionDate :String?=null,

                     var IsBuyFromEWallet :Boolean?=null,

                     var IsBuyFromBankAcnt :Boolean?=null,

                     var IsRequestedForBuy :Boolean?=null,

                     var IsApprovedForBuy :Boolean?=null,

                     var IsRejectedForBuy :Boolean?=null

                     )



