using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models
{
    public class User
    {
      
        public User(int id, string name, string email, string pass, string uname)
        {
            Id = id;
            Name = name;
            Email = email;
            Password = pass;
            UserName = uname;
        }

        public int Id { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string UserName { get; set; }
      
    }
    public class User1
    {
        public int UId { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
    }
    public class NewUserRegistration
    {
  
        public NewUserRegistration(int id, string name1, string email, string pass)
        {
            this.UserId = id;
            this.Username = name1;
            this.Email = email;
            this.Password = pass;
           
        }

        public int UserId { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Country { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
        public string CNIC { get; set; }
        public string AccountNumber { get; set; }
        public string BankName { get; set; }
        public string IsThisFirstUser { get; set; }
        public string SponsorId { get; set; }
        public string DownlineMemberId { get; set; }
        public string UpperId { get; set; }
        public string PaidAmount { get; set; }
        public string CreateDate { get; set; }
        public string UserCode { get; set; }
        public string IsUserActive { get; set; }
        public string IsNewRequest { get; set; }
        public string UserPosition { get; set; }
        public string IsEmailConfirmed { get; set; }
        public string UserPackage { get; set; }
        public string DocumentImage { get; set; }
        public string IsSleepingPartner { get; set; }
        public string IsSalesExecutive { get; set; }
        public string UserDesignation { get; set; }
    }
}