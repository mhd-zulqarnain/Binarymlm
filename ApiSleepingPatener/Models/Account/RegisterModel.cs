using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Account
{
    public class RegisterModel
    {
        public int UserId { get; set; }

        [Required(ErrorMessage = "Please provide name", AllowEmptyStrings = false)]
        public string Name { get; set; }

        [Required(ErrorMessage = "Please provide username", AllowEmptyStrings = false)]
        public string UserName { get; set; }

        [Required(ErrorMessage = "Please provide Password", AllowEmptyStrings = false)]
        [DataType(System.ComponentModel.DataAnnotations.DataType.Password)]
        [StringLength(50, MinimumLength = 8, ErrorMessage = "Password must be 8 char long.")]
        public string Password { get; set; }

        [Required(ErrorMessage = "Please select country")]
        public int Country { get; set; }

        [Required(ErrorMessage = "Please fill address")]
        public string Address { get; set; }

        [Required(ErrorMessage = "Please fill phone number")]
        public string Phone { get; set; }

        [RegularExpression(@"^([0-9a-zA-Z]([\+\-_\.][0-9a-zA-Z]+)*)+@(([0-9a-zA-Z][-\w]*[0-9a-zA-Z]*\.)+[a-zA-Z0-9]{2,3})$",
        ErrorMessage = "Please provide valid email id")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Please fill account number")]
        public string AccountNumber { get; set; }

        [Required(ErrorMessage = "Please fill bank name number")]
        public string BankName { get; set; }

        public string CNICNumber { get; set; }

        public bool IsThisFirstUser { get; set; }

        [Required(ErrorMessage = "Please fill sponsor")]
        public int SponsorId { get; set; }

        public int DownlineMemberId { get; set; }

        public int UpperId { get; set; }

        [Required(ErrorMessage = "Please fill paid amount")]
        public decimal PaidAmount { get; set; }

        public DateTime CreateDate { get; set; }

        public string UserCode { get; set; }

        public bool IsUserActive { get; set; }

        public bool IsNewRequest { get; set; }

        public string UserPosition { get; set; }

        public bool IsEmailConfirmed { get; set; }

        public int UserPackage { get; set; }

        public byte[] DocumentImage { get; set; }
        
    }
}