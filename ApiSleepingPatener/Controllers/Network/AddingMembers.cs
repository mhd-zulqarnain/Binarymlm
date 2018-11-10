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
using ApiSleepingPatener.Models.Account;

namespace ApiSleepingPatener.Controllers
{
   
    public class AddingMembers : ApiController
    {
        //[HttpPost]
        //[Route("addleftmembers/{userId}")]
        //public IHttpActionResult AddNewMemeberLeft(UserModel model, int userId)
        //{
        //    try
        //    {
        //        // var userId = Convert.ToInt32(Session["LogedUserID"].ToString());
        //        //Slee  dbTree = new TreeDataTbl();
        //        using (SleepingtestEntities dc = new SleepingtestEntities())
        //        {
        //            var usercheckEmail = dc.NewUserRegistrations.Where(a => a.Email.Equals(model.Email)).FirstOrDefault();
        //            var usercheckPhone = dc.NewUserRegistrations.Where(a => a.Phone.Equals(model.Phone)).FirstOrDefault();
        //            var usercheckAccountNumber = dc.NewUserRegistrations.Where(a => a.AccountNumber.Equals(model.AccountNumber)).FirstOrDefault();
        //            var usercheckCNIC = dc.NewUserRegistrations.Where(a => a.CNIC.Equals(model.CNICNumber)).FirstOrDefault();
        //            if (usercheckEmail != null)
        //            {
        //                return Ok(new { error = true, message = "User email already exist" });
        //            }
        //            else if (usercheckPhone != null)
        //            {
        //                return Ok(new { error = true, message = "User phone number already exist" });
        //            }
        //            else if (usercheckAccountNumber != null)
        //            {
        //                return Ok(new { error = true, message = "User Account Number already exist" });
        //            }
        //            else if (usercheckCNIC != null)
        //            {
        //                return Ok(new { error = true, message = "User CNIC already exist" });
        //            }
        //            else
        //            {
        //                Package package = dc.Packages.Where(a => a.PackageId.Equals(model.UserPackage)).FirstOrDefault();
        //                UserPackage userpackage = new UserPackage();
        //                UserTableLevel userTableLevel = new UserTableLevel();
        //                NewUserRegistration newuser = new NewUserRegistration();

        //                newuser.Name = model.Name;
        //                newuser.Username = model.UserName;
        //                newuser.Password = model.Password;
        //                newuser.Country = model.Country;
        //                newuser.Address = model.Address;
        //                newuser.Phone = model.Phone;
        //                newuser.Email = model.Email;
        //                newuser.AccountNumber = model.AccountNumber;
        //                newuser.BankName = model.BankName;
        //                newuser.CNIC = model.CNICNumber;
        //                newuser.IsThisFirstUser = model.IsThisFirstUser;
        //                if (model.DownlineMemberId == 0 || model.DownlineMemberId == null)
        //                {
        //                    newuser.DownlineMemberId = userId;
        //                }
        //                else
        //                {
        //                    newuser.DownlineMemberId = model.DownlineMemberId.Value;
        //                }
        //                newuser.UserPosition = Common.Enum.UserPosition.Left;
        //                newuser.IsUserActive = false;
        //                newuser.IsNewRequest = true;
        //                newuser.SponsorId = userId;
        //                newuser.UpperId = model.UpperId;
        //                newuser.PaidAmount = package.PackagePrice;
        //                newuser.CreateDate = DateTime.Now;
        //                newuser.UserCode = Common.Enum.UserType.User.ToString();
        //                newuser.IsEmailConfirmed = false;
        //                newuser.UserPackage = model.UserPackage;
        //                //file = Request.Files["AddNewMemberLeftImageData"];
        //                var fileImage = model.DocumentImage;
        //                if (fileImage != null)
        //                {
        //                    //byte[] img = ConvertToBytes(fileImage;
        //                    //newuser.DocumentImage = Convert.ToByte(fileImage);
        //                }
        //                newuser.IsSleepingPartner = false;
        //                newuser.IsSalesExecutive = false;
        //                newuser.IsWithdrawalOpen = false;
        //                dc.NewUserRegistrations.Add(newuser);
        //                dc.SaveChanges();


        //                userpackage.PackageId = package.PackageId;
        //                userpackage.PackageName = package.PackageName;
        //                userpackage.PackagePercent = package.PackagePercent;
        //                userpackage.PackagePrice = package.PackagePrice;
        //                userpackage.PackageValidity = package.PackageValidity;
        //                userpackage.PackageMinWithdrawalAmount = package.PackageMinWithdrawalAmount;
        //                userpackage.PackageMaxWithdrawalAmount = package.PackageMaxWithdrawalAmount;
        //                userpackage.UserId = newuser.UserId;
        //                userpackage.IsInCurrentUse = true;
        //                userpackage.PurchaseDate = DateTime.Now;

        //                dc.UserPackages.Add(userpackage);


        //                userTableLevel.UserName = model.UserName;
        //                userTableLevel.TableLevel = 1;
        //                userTableLevel.NoOfUsers = 0;
        //                userTableLevel.RightUsers = 0;
        //                userTableLevel.LeftUsers = 0;
        //                userTableLevel.TableLevelLimit = 2;
        //                userTableLevel.UserId = newuser.UserId;
        //                userTableLevel.LastModifiedDate = DateTime.Now;
        //                dc.UserTableLevels.Add(userTableLevel);

        //                dc.SaveChanges();

        //                #region creating first user tree

        //                TreeDataTbl userTree = dbTree.TreeDataTbls.Where(a => a.UserId.Value.Equals(newuser.DownlineMemberId)).FirstOrDefault();

        //                if (userTree == null)
        //                {
        //                    if (newuser.IsThisFirstUser == true)
        //                    {
        //                        dbTree.insert_tree_node(newuser.Username, 0, newuser.UserId, newuser.DownlineMemberId, newuser.UserPosition);
        //                    }
        //                    else
        //                    {
        //                        dbTree.insert_tree_node(newuser.Username, userTree.Tree_ID, newuser.UserId, newuser.DownlineMemberId, newuser.UserPosition);
        //                    }
        //                }



        //                #endregion

        //                #region send sms


        //                TwilioClient.Init(SendSMSAccountSid, SendSMSAuthToken);

        //                var message = MessageResource.Create(
        //                    body: "Welcome to Sleeping partner portal. "
        //                    + " Please make sure to pay your amount with in 5 bussiness days"
        //                    + " to avoid your account deactivation. "
        //                    + " Your username is : " + model.UserName
        //                    + " and password is : " + model.Password + "."
        //                    + " Click on http://sleepingpartnermanagementportalrct.com ",
        //                    from: new Twilio.Types.PhoneNumber(SendSMSFromNumber),
        //                    to: new Twilio.Types.PhoneNumber(model.Phone)
        //                );


        //                #endregion

        //                #region user email

        //                System.Net.Mail.MailMessage mail1 = new System.Net.Mail.MailMessage();
        //                mail1.From = new MailAddress("noreply@sleepingpartnermanagementportalrct.com");
        //                mail1.To.Add(model.Email);
        //                mail1.Subject = "Sleeping partner management portal";
        //                mail1.Body = "User accept by admin. " +
        //                    " Your username is " + model.UserName + " and password : " + model.Password + "</br></br>" +
        //                    "<table style='font-family:Verdana, Helvetica, sans-serif;' cellpadding='0' cellspacing='0'><tbody><tr><td style='font-family:Verdana; border-right:2px solid #BD272D; padding-right:15px; text-align: right; vertical-align:top; ' valign='top'><table style='font-family:Verdana; margin-right:0; margin-left:auto;' cellpadding='0' cellspacing='0'><tbody><tr><td style='font-family:Verdana; height:55px; vertical-align:top; text-align:right;' valign='top' align='right'><span style='font-family:Verdana; font-size:14pt; font-weight:bold'>Sleeping partner management<span><br></span></span></td></tr><tr><td style='font-family:Verdana; height:40px; vertical-align:top; padding:0; text-align:right;' valign='top' align='right'><span style='font-family:Verdana; font-size:10pt;'>phone: 123456<span><br></span></span><span style='font-family:Verdana; font-size:10pt;'>mobile: 0123456</span></td></tr><tr><td><a href='http://sleepingpartnermanagementportalrct.com'>sleepingpartnermanagementportal</a></td></tr></tbody></table></td><td style='padding-left:15px;font-size:1pt; vertical-align:top; font-family:Verdana;' valign='top'><table style='font-family:Verdana;' cellpadding='0' cellspacing='0'><tbody><tr><td style='height:55px; font-family:Verdana; vertical-align:top;' valign='top'><a href='{Logo URL}' target='_blank'><img alt='Logo' style='height:40px; width:auto; border:0; ' height='40' border='0'  src='~/Content/images/newsleepinglogo.png'></a></td></tr><tr><td style='height:40px; font-family:Verdana; vertical-align:top; padding:0;' valign='top'><span style='font-family:Verdana; font-size:10pt;'>{Address 1}<span><br></span></span> <span style='font-family:Verdana; font-size:10pt;'>{Address 2}</span> </td></tr><tr><td style='height:20px; font-family:Verdana; vertical-align:middle;' valign='middle'><a href='http://{Web page}' target='_blank' style='color:#BD272D; font-size:10pt; font-family:Verdana;'>{Web page}</a></td></tr></tbody></table></td></tr></tbody></table>";
        //                mail1.IsBodyHtml = true;
        //                SmtpClient smtp1 = new SmtpClient();
        //                smtp1.Host = "sleepingpartnermanagementportalrct.com";
        //                smtp1.EnableSsl = true;
        //                smtp1.UseDefaultCredentials = false;
        //                smtp1.Credentials = new NetworkCredential("noreply@sleepingpartnermanagementportalrct.com", "Yly21#p8");
        //                smtp1.DeliveryMethod = SmtpDeliveryMethod.Network;
        //                smtp1.Port = 25;
        //                ServicePointManager.ServerCertificateValidationCallback =
        //                delegate (object s, X509Certificate certificate,
        //                         X509Chain chain, SslPolicyErrors sslPolicyErrors)
        //                { return true; };
        //                smtp1.Send(mail1);
        //                //await SendEmailToSponsor(newuserdata.Email, "sleeping patners", Body);

        //                #endregion

        //                ModelState.Clear();
        //                model = null;
        //                ViewBag.MessageAddNewMemeberLeft = "Successfully Registration Done";
        //            }

        //        }
        //        return Json(new { success = true, message = "User has been saved" }, JsonRequestBehavior.AllowGet);
        //    }
        //    catch (Exception ex)
        //    {
        //        return Json(new { error = true, message = ex.Message }, JsonRequestBehavior.AllowGet);
        //    }

        //}

    }

}


