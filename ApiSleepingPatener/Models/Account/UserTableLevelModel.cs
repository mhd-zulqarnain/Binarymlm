using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Account
{
    public class UserTableLevelModel
    {
        public int UserTableLevelId { get; set; }
        
        public string UserName { get; set; }

        public int TableLevel { get; set; }
        
        public int NoOfUsers { get; set; }
        
        public int UserId { get; set; }

        public DateTime LastModifiedDate { get; set; }
        
        public int LeftUsers { get; set; }
        
        public int RightUsers { get; set; }

        public int TableLevelLimit { get; set; }

        public decimal TotalLeftUserAmount { get; set; }

        public decimal TotalRightUserAmount { get; set; }

        public decimal TotalRemainingUserAmount { get; set; }

    }
}