//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace ApiSleepingPatener
{
    using System;
    using System.Collections.Generic;
    
    public partial class ReceiveUserMessage
    {
        public int Id { get; set; }
        public Nullable<int> Sender { get; set; }
        public string Sender_Name { get; set; }
        public Nullable<int> UserId { get; set; }
        public Nullable<int> SponserId { get; set; }
        public string Message { get; set; }
        public Nullable<bool> IsRead { get; set; }
        public Nullable<System.DateTime> CreateDate { get; set; }
    }
}
