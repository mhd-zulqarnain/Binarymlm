using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.GeneralHelp
{
    public class HelpRequestModel
    {
        public int HelpRequestId { get; set; }
        [Required(ErrorMessage = "enter title", AllowEmptyStrings = false)]
        public string HelpRequestName { get; set; }
        [Required(ErrorMessage = "enter description", AllowEmptyStrings = false)]
        public string HelpRequestDescription { get; set; }

        public byte[] HelpRequestImage { get; set; }

        public bool IsActive { get; set; }

        public DateTime CreateDate { get; set; }

        public bool IsCheckByAdmin { get; set; }

        public int UserId { get; set; }

        public string HelpRequestPriority { get; set; }
    }
}