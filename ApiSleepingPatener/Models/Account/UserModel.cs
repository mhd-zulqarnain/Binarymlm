using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace BinaryMLMSystem.Models.Account
{
    public class UserModel
    {
        public int UserId { get; set; }

        [Required(ErrorMessage = "enter name", AllowEmptyStrings = false)]
        [RegularExpression(@"^[a-zA-Z0-9\s]+$", ErrorMessage = "Special character should not be entered")]
        public string Name { get; set; }

        [Required(ErrorMessage = "enter UserName", AllowEmptyStrings = false)]
        public string UserName { get; set; }

        [Required(ErrorMessage = "enter Password", AllowEmptyStrings = false)]
        public string Password { get; set; }

        [Required(ErrorMessage = "enter Country", AllowEmptyStrings = false)]
        public int? Country { get; set; }

        public string CountryName { get; set; }

        [Required(ErrorMessage = "enter Address", AllowEmptyStrings = false)]
        public string Address { get; set; }

        [Required(ErrorMessage = "enter Phone", AllowEmptyStrings = false)]
        public string Phone { get; set; }

        [Required(ErrorMessage = "enter Email", AllowEmptyStrings = false)]
        public string Email { get; set; }

        [Required(ErrorMessage = "enter AccountNumber", AllowEmptyStrings = false)]
        public string AccountNumber { get; set; }

        [Required(ErrorMessage = "enter BankName", AllowEmptyStrings = false)]
        public string BankName { get; set; }

        [Required(ErrorMessage = "enter CNICNumber", AllowEmptyStrings = false)]
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

        public bool IsWithdrawalOpen { get; set; }
      
    }
}