using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.NewsTicker
{
    public class NewsTickerModel
    {
        public int NewsTickerId { get; set; }
        [Required(ErrorMessage = "enter news ticker name")]
        public string NewsTickerName { get; set; }
        [Required(ErrorMessage = "enter news ticker description")]
        public string NewsTickerDescription { get; set; }

        public bool IsActive { get; set; }
    }
}