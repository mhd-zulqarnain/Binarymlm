using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Account
{
    public class UserModel
    {
        public int UserId { get; set; }        
        public string Name { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public int? Country { get; set; }
        public string CountryName { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
        public string AccountNumber { get; set; }
        public string BankName { get; set; }
        public string CNICNumber { get; set; }

        public bool IsThisFirstUser { get; set; }

        public int? SponsorId { get; set; }

        public string SponsorName { get; set; }

        public int? DownlineMemberId { get; set; }

        public int UpperId { get; set; }

        public decimal PaidAmount { get; set; }

        public DateTime CreateDate { get; set; }

        public DateTime LastLoginDate { get; set; }

        public string UserCode { get; set; }

        public bool IsUserActive { get; set; }

        public bool IsNewRequest { get; set; }

        public string UserPosition { get; set; }

        public bool IsEmailConfirmed { get; set; }

        public int UserPackage { get; set; }

        public string UserPackageName { get; set; }

        public decimal UserPackagePrice { get; set; }

        //public byte[] DocumentImage { get; set; }

        public HttpPostedFileWrapper DocumentImage { get; set; }

        public bool IsSleepingPartner { get; set; }

        public bool IsSalesExecutive { get; set; }

        public string UserDesignation { get; set; }

        public bool? IsWithdrawalOpen { get; set; }

        public decimal EWalletBalance { get; set; }

        public HttpPostedFileWrapper NICImage { get; set; }

        public HttpPostedFileWrapper ProfileImage { get; set; }

        [Required(ErrorMessage = "enter title", AllowEmptyStrings = false)]
        public string AccountTitle { get; set; }

        public bool IsVerify { get; set; }

        public bool IsBlock { get; set; }

        public bool? IsPaidMember { get; set; }

    }
}