//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace ApiSleepingPatener
{
    using System;
    using System.Collections.Generic;
    
    public partial class UserPackage
    {
        public int UserPackageId { get; set; }
        public string PackageName { get; set; }
        public Nullable<int> PackagePercent { get; set; }
        public Nullable<decimal> PackagePrice { get; set; }
        public string PackageValidity { get; set; }
        public Nullable<decimal> PackageMinWithdrawalAmount { get; set; }
        public Nullable<decimal> PackageMaxWithdrawalAmount { get; set; }
        public Nullable<int> PackageId { get; set; }
        public Nullable<int> UserId { get; set; }
        public Nullable<bool> IsInCurrentUse { get; set; }
        public Nullable<System.DateTime> PurchaseDate { get; set; }
        public string PackagePurchaseMethod { get; set; }
        public byte[] BankSlipImage { get; set; }
        public Nullable<System.DateTime> LastCommisionDate { get; set; }
    }
}
