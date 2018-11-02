using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class ReciveUserMessageModel
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "requried field")]
        public Nullable<int> Sender { get; set; }
        public string Sender_Name { get; set; }
        [Required(ErrorMessage = "requried field")]
        public Nullable<int> UserId { get; set; }
        [Required(ErrorMessage = "requried field")]
        public Nullable<int> SponserId { get; set; }
        [Required(ErrorMessage = "requried field")]
        public string Message { get; set; }
        [Required(ErrorMessage = "requried field")]
        public Nullable<bool> IsRead { get; set; }
        [Required(ErrorMessage = "requried field")]
        public Nullable<System.DateTime> CreateDate { get; set; }

    }
}