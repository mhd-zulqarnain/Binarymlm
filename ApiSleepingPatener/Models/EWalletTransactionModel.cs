using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class EWalletTransactionModel
    {
        public int TransactionId { get; set; }

        public string TransactionSource { get; set; }

        public string TransactionName { get; set; }

        public int AsscociatedMember { get; set; }

        public decimal Amount { get; set; }

        public DateTime TransactionDate { get; set; }

        public bool Credit { get; set; }

        public bool Debit { get; set; }

        public int UserId { get; set; }

        public bool IsPackageBonus { get; set; }

        public int PackageId { get; set; }

        public bool IsMatchingBonus { get; set; }

        public bool IsParentBonus { get; set; }

        public bool IsWithdrawlRequestByUser { get; set; }

        public bool IsWithdrawlPaidByAdmin { get; set; }

        public bool AdminCredit { get; set; }

        public bool AdminDebit { get; set; }

        public string AdminTransactionName { get; set; }
    }
}