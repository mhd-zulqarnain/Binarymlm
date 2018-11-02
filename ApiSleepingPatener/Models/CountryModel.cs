using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class CountryModel
    {
        public int CountryID { get; set; }
        public string CountryName { get; set; }
        public string TwoCharCountryCode { get; set; }
        public string ThreeCharCountryCode { get; set; }
    }
}