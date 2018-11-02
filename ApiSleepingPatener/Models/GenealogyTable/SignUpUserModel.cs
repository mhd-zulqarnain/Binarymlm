using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.GenealogyTable
{
    public class SignUpUserModel
    {
        public int SignUpId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public Nullable<System.DateTime> DateOfBirth { get; set; }
        public string NICnumber { get; set; }
        public string Package { get; set; }
        public Nullable<decimal> AmountRange { get; set; }
        public Nullable<int> PhoneNumber { get; set; }
        public string Message { get; set; }
        public Nullable<System.DateTime> CreateDate { get; set; }
    }
}