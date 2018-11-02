using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class ReceiveAdminMessageModel
    {
        public int Id { get; set; }
        public Nullable<int> Sender { get; set; }
        public Nullable<int> UserId { get; set; }
        [Required(ErrorMessage = "select sponser", AllowEmptyStrings = false)]
        public Nullable<int> SponserId { get; set; }
        public string Sender_Name { get; set; }
        [Required(ErrorMessage = "enter message", AllowEmptyStrings = false)]
        public string Message { get; set; }
        public byte[] MessageImage { get; set; }
        public Nullable<bool> IsRead { get; set; }
        public Nullable<System.DateTime> CreateDate { get; set; }
    }
}