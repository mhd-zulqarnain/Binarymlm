using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Account
{
    public class LoginModel
    {
        public int UserId { get; set; }

        [Required(ErrorMessage = "enter username")]
        public string UserName { get; set; }

        [Required(ErrorMessage = "enter password")]
        [DataType(System.ComponentModel.DataAnnotations.DataType.Password)]
        public string Password { get; set; }

        public string Email { get; set; }

        public string UserCode { get; set; }
    }
}