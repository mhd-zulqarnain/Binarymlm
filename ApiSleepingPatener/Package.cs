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
    
    public partial class Package
    {
        public int PackageId { get; set; }
        public string PackageName { get; set; }
        public Nullable<int> PackagePercent { get; set; }
        public Nullable<decimal> PackagePrice { get; set; }
        public string PackageValidity { get; set; }
        public Nullable<decimal> PackageMinWithdrawalAmount { get; set; }
        public Nullable<decimal> PackageMaxWithdrawalAmount { get; set; }
        public Nullable<bool> IsActive { get; set; }
        public Nullable<System.DateTime> CreateDate { get; set; }
        public Nullable<int> AddMemberLimit { get; set; }
        public Nullable<decimal> MaximumMatchingBonusAmount { get; set; }
    }
}
