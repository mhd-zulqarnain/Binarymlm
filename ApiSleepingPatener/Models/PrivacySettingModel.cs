using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class PrivacySettingModel
    {
        public int UserId { get; set; }
        public string Username { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
        public string BankName { get; set; }
        public string AccountNumber { get; set; }
    }
}