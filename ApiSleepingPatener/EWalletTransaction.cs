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
    
    public partial class EWalletTransaction
    {
        public int TransactionId { get; set; }
        public string TransactionSource { get; set; }
        public string TransactionName { get; set; }
        public Nullable<int> AsscociatedMember { get; set; }
        public Nullable<decimal> Amount { get; set; }
        public Nullable<System.DateTime> TransactionDate { get; set; }
        public Nullable<bool> Credit { get; set; }
        public Nullable<bool> Debit { get; set; }
        public Nullable<int> UserId { get; set; }
        public Nullable<bool> IsPackageBonus { get; set; }
        public Nullable<int> PackageId { get; set; }
        public Nullable<bool> IsMatchingBonus { get; set; }
        public Nullable<bool> IsParentBonus { get; set; }
        public Nullable<bool> IsWithdrawlRequestByUser { get; set; }
        public Nullable<bool> IsWithdrawlPaidByAdmin { get; set; }
        public Nullable<bool> AdminCredit { get; set; }
        public Nullable<bool> AdminDebit { get; set; }
        public string AdminTransactionName { get; set; }
    }
}
