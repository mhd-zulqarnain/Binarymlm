using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Notification
{
    public class NotificationModel
    {
        public int NotificationId { get; set; }
        [Required(ErrorMessage = "enter notification name")]
        public string NotificationName { get; set; }
        [Required(ErrorMessage = "enter notification description")]
        public string NotificationDescription { get; set; }

        public bool IsActive { get; set; }

        [Required(ErrorMessage = "select sponsor")]
        public int UserId { get; set; }
    }
}