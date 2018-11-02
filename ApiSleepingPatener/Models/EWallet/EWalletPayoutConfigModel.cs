using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.EWallet
{
    public class EWalletPayoutConfigModel
    {
        public int ConfigId { get; set; }

        public string TimePeriod { get; set; }

        public decimal MinimumPayout { get; set; }

        public int PayoutChargesPercent { get; set; }
    }
}