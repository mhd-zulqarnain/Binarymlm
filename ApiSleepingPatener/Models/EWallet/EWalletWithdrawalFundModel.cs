using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.EWallet
{
    public class EWalletWithdrawalFundModel
    {

        public int WithdrawalFundId { get; set; }

        public int UserId { get; set; }

        public string AccountNumber { get; set; }

        public string BankName { get; set; }

        public string WithdrawalFundMethod { get; set; }

        public decimal AmountPayble { get; set; }

        public string WithdrawalFundDescription { get; set; }

        public decimal WithdrawalFundCharge { get; set; }

        public int Country { get; set; }

        public DateTime? RequestedDate { get; set; }

        public DateTime? ApprovedDate { get; set; }

        public DateTime? PaidDate { get; set; }

        public DateTime? RejectedDate { get; set; }

        public bool IsActive { get; set; }

        public bool IsPending { get; set; }

        public bool IsApproved { get; set; }

        public bool IsPaid { get; set; }

        public bool IsRejected { get; set; }

        public string UserName { get; set; }

    }
}