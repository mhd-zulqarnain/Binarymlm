using System.Collections.Generic;
using ApiSleepingPatener.Models;
using System.Web.Http;
using System.Data.Entity;
using System.Data;
using System.Linq;
using System;

namespace ApiSleepingPatener.Controllers.Userdetails
{
    [Authorize]
    public class UserDetailController : ApiController
    {
        [HttpGet]
        [Route("api/getuser/{username}")]
        public IHttpActionResult getNewRegistration(string username)
        {
            try
            {
                using (SleepingtestEntities dce = new SleepingtestEntities())
                {
                    //var CGP = (from a in dce.EWalletWithdrawalFunds
                    //           where a.IsPending.Value == true
                    //           select a).ToList();
                    //var query = CGP.Count();
                    NewUserRegistration v = dce.NewUserRegistrations.Where(a => a.Username == username).FirstOrDefault();
                  
                    return Ok(v);
                }

            }catch(Exception e)
            {
                return Ok(e);
            }
        }

        [HttpPost]
        [Route("api/updateuser")]
        public IHttpActionResult ProfileSetup(NewUserRegistration model)
        {
            
            SleepingtestEntities dce = new SleepingtestEntities();
            //NewUserRegistration newuser = dce.NewUserRegistrations.Where(a => a.UserId.Equals(model.UserId)).FirstOrDefault();
            NewUserRegistration newuser = dce.NewUserRegistrations.SingleOrDefault(x => x.UserId == model.UserId);
            if (newuser != null)
            {
               
                newuser.Name = model.Name;
                //newuser.Password = model.Password;
                //newuser.Country = model.Country;
                //newuser.Address = model.Address;
                //newuser.Phone = model.Phone;
                //newuser.Email = model.Email;
                //newuser.AccountNumber = model.AccountNumber;
                //newuser.BankName = model.BankName;
                dce.SaveChanges();
                return Ok(new { success = true, message = "Update Successfully" });

            }
            return Ok(new { success = false, message = "user not found" });


        }

        [HttpPost]
        [Route("api/addnew")]
        public IHttpActionResult addUserSetup(NewUserRegistration model)
        {

            SleepingtestEntities dce = new SleepingtestEntities();
            NewUserRegistration newuser = dce.NewUserRegistrations.Where(a => a.Username.Equals(model.Username)).FirstOrDefault();
            //NewUserRegistration newuser = dce.NewUserRegistrations.SingleOrDefault(x => x.Username == model.UserId);
            if (newuser == null)
            {
                dce.NewUserRegistrations.Add(model);
                dce.SaveChanges();
                return Ok(new { success = true, message = "user added" });

            }
            return Ok(new { success = false, message = "user not found" });


        }
    }
}