using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class MakeTableData
    {
        public decimal totalLeftUsers  { get; set; }
        public decimal totalRightUsers  { get; set; }
        public decimal totalAmountLeftUsers { get; set; }
        public decimal totalAmountRightUsers { get; set; }
        public decimal rightRemaingAmount { get; set; }
        public decimal leftRemaingAmount { get; set; }
    }
}