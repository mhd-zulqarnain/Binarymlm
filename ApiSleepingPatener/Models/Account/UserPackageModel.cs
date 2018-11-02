using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Account
{
    public class UserPackageModel
    {
        public int UserPackageId { get; set; }

        public string PackageName { get; set; }

        public int PackagePercent { get; set; }

        public decimal? PackagePrice { get; set; }

        public string PackageValidity { get; set; }

        public string PackagePurchaseMethod { get; set; }

        public decimal? PackageMinWithdrawalAmount { get; set; }

        public decimal? PackageMaxWithdrawalAmount { get; set; }

        public int PackageId { get; set; }

        public int? UserId { get; set; }

        public bool? IsInCurrentUse { get; set; }

        public DateTime PurchaseDate { get; set; }

        public DateTime? LastCommisionDate { get; set; }
    }
}