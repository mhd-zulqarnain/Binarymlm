using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class ProfileSettingModel
    {
        public int UserId { get; set; }
        public string Name { get; set; }
        public string Username { get; set; }
        public string Address { get; set; }
        public Nullable<int> Country { get; set; }
        public byte[] DocumentImage { get; set; }
    }
}