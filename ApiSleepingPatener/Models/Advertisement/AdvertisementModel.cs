using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Advertisement
{
    public class AdvertisementModel
    {
        public int AdvertisementId { get; set; }
        [Required(ErrorMessage = "enter advertisement name")]
        public string AdvertisementName { get; set; }
        [Required(ErrorMessage = "enter advertisement description")]
        public string AdvertisementDescription { get; set; }

        public byte[] AdvertisementImage { get; set; }

        public bool IsActive { get; set; }
    }
}