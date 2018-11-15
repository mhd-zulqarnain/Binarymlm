using System.Collections.Generic;
using ApiSleepingPatener.Models;
using System.Web.Http;
using System.Data.Entity;
using System.Data;
using System.Linq;
using System;
using System.Net.Mail;
using System.Net;
using System.Security.Cryptography.X509Certificates;
using System.Net.Security;

namespace ApiSleepingPatener.Controllers
{
   
    public class UserDetailController : ApiController
    {
      //  [Authorize]
        [HttpGet]
        [Route("getuser/{username}")]
        public IHttpActionResult getNewRegistration(string username)
        {
            try
            {
                using (sleepingtestEntities dce = new sleepingtestEntities())
                {
                   
                    NewUserRegistration v = dce.NewUserRegistrations.Where(a => a.Username == username).FirstOrDefault();
                  
                    return Ok(v);
                }

            }catch(Exception e)
            {
                return Ok(e);
            }
        }
        [Authorize]
        [HttpPost]
        [Route("updateuser")]
        public IHttpActionResult ProfileSetup(NewUserRegistration model)
        {

            sleepingtestEntities dce = new sleepingtestEntities();
            //NewUserRegistration newuser = dce.NewUserRegistrations.Where(a => a.UserId.Equals(model.UserId)).FirstOrDefault();
            NewUserRegistration newuser = dce.NewUserRegistrations.SingleOrDefault(x => x.UserId == model.UserId);
            if (newuser != null)
            {
               
                newuser.Name = model.Name;
                newuser.Username = model.Username;
                newuser.Password = model.Password;
                newuser.Country = model.Country;
                newuser.Address = model.Address;
                newuser.Phone = model.Phone;
                newuser.Email = model.Email;
                newuser.AccountNumber = model.AccountNumber;
                newuser.BankName = model.BankName;
                newuser.IsSalesExecutive = model.IsSalesExecutive;
                newuser.IsSleepingPartner = model.IsSleepingPartner;
                dce.SaveChanges();
                return Ok(new { success = true, message = "Update Successfully" });

            }
            return Ok(new { success = false, message = "user not found" });


        }

        [Authorize]
        [HttpPost]
        [Route("addnew")]
        public IHttpActionResult addUserSetup(NewUserRegistration model)
        {

            sleepingtestEntities dce = new sleepingtestEntities();
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


        [HttpGet]
        [Route("forgetpassword/{email}")]
        public IHttpActionResult fortgetPassword(string email)
        {
            sleepingtestEntities dc = new sleepingtestEntities();
           
                var v = dc.NewUserRegistrations.Where(a => a.Email.Equals(email)).FirstOrDefault();
                if (v != null)
                {
                    System.Net.Mail.MailMessage mail = new System.Net.Mail.MailMessage();
                    mail.From = new MailAddress("noreply@sleepingpartnermanagementportalrct.com");
                    mail.To.Add(v.Email);
                    mail.Subject = "Sleeping partner management portal";
                    mail.Body += "Your password is " + v.Password + "</br></br>" +
                                "<table style='font-family:Verdana, Helvetica, sans-serif;' cellpadding='0' cellspacing='0'><tbody><tr><td style='font-family:Verdana; border-right:2px solid #BD272D; padding-right:15px; text-align: right; vertical-align:top; ' valign='top'><table style='font-family:Verdana; margin-right:0; margin-left:auto;' cellpadding='0' cellspacing='0'><tbody><tr><td style='font-family:Verdana; height:55px; vertical-align:top; text-align:right;' valign='top' align='right'><span style='font-family:Verdana; font-size:14pt; font-weight:bold'>Sleeping partner management<span><br></span></span></td></tr><tr><td style='font-family:Verdana; height:40px; vertical-align:top; padding:0; text-align:right;' valign='top' align='right'><span style='font-family:Verdana; font-size:10pt;'>phone: 123456<span><br></span></span><span style='font-family:Verdana; font-size:10pt;'>mobile: 0123456</span></td></tr><tr><td><a href='http://sleepingpartnermanagementportalrct.com'>sleepingpartnermanagementportal</a></td></tr></tbody></table></td><td style='padding-left:15px;font-size:1pt; vertical-align:top; font-family:Verdana;' valign='top'><table style='font-family:Verdana;' cellpadding='0' cellspacing='0'><tbody><tr><td style='height:55px; font-family:Verdana; vertical-align:top;' valign='top'><a href='{Logo URL}' target='_blank'><img alt='Logo' style='height:40px; width:auto; border:0; ' height='40' border='0'  src='~/Content/images/newsleepinglogo.png'></a></td></tr><tr><td style='height:40px; font-family:Verdana; vertical-align:top; padding:0;' valign='top'><span style='font-family:Verdana; font-size:10pt;'>{Address 1}<span><br></span></span> <span style='font-family:Verdana; font-size:10pt;'>{Address 2}</span> </td></tr><tr><td style='height:20px; font-family:Verdana; vertical-align:middle;' valign='middle'><a href='http://{Web page}' target='_blank' style='color:#BD272D; font-size:10pt; font-family:Verdana;'>{Web page}</a></td></tr></tbody></table></td></tr></tbody></table>";
                    mail.IsBodyHtml = true;
                    SmtpClient smtp = new SmtpClient();
                    smtp.Host = "sleepingpartnermanagementportalrct.com";
                    smtp.EnableSsl = true;
                    smtp.UseDefaultCredentials = false;
                    smtp.Credentials = new NetworkCredential("noreply@sleepingpartnermanagementportalrct.com", "Yly21#p8");
                    smtp.DeliveryMethod = SmtpDeliveryMethod.Network;
                    smtp.Port = 25;
                    ServicePointManager.ServerCertificateValidationCallback =
                    delegate (object s, X509Certificate certificate,
                             X509Chain chain, SslPolicyErrors sslPolicyErrors)
                    { return true; };
                    smtp.Send(mail);

                return Ok(new { success = true, message = "Email and password sent to user email account" });
            }
                else
                {
                return Ok(new { success = false, message = "Email not exists" });
            }


        }

        
        [HttpGet]
        [Route("getcountries")]
        public IHttpActionResult getCountries()
        {
            try
            {
                List<Country> List = new List<Country>();
                using (sleepingtestEntities dce = new sleepingtestEntities())
                {

                    List = dce.Countries.ToList();

                    return Ok(List);
                }

            }
            catch (Exception e)
            {
                return Ok(e);
            }
        }

    }
}