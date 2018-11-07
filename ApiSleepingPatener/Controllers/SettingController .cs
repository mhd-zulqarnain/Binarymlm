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
    public class SettingController : ApiController
    {
        //Packages
        [HttpGet]
        [Route("setting/packages")]
        public IHttpActionResult getPackages()
        {
            try
            {
                List<Package> List = new List<Package>();
                using (SleepingtestEntities dce = new SleepingtestEntities())
                {

                    List = dce.Packages.ToList();

                    return Ok(List);
                }

            }
            catch (Exception e)
            {
                return Ok(e);
            }

        }
        //Get Ads
        [HttpGet]
        [Route("getAds")]
        public IHttpActionResult ShowAdvertisementData()
        {
            SleepingtestEntities db = new SleepingtestEntities();
            List<Advertisement> listadvertisement = db.Advertisements.Where(x => x.IsActive == true).ToList();
            return Ok(listadvertisement);

        }
        //[HttpGet]
        //[Route("setting/getdownliner")]
        //public IHttpActionResult GetUserDownlineMembersLeft(int userId)
        //{
        //    //var userId = Convert.ToInt32(Session["LogedUserID"].ToString());
        //    string UserTypeUser = Common.Enum.UserType.User.ToString();

        //    SleepingtestEntities db = new SleepingtestEntities();

        //    List<GetParentChildsLeftSP_Result> List = new List<GetParentChildsLeftSP_Result>();
        //    List = db.GetParentChildsLeftSP(userId).ToList();

        //    List<NewUserRegistration> listDownlineMember = new List<NewUserRegistration>();

        //    UserGenealogyTableLeft leftUsers = new UserGenealogyTableLeft();

        //    foreach (var item in List)
        //    {
        //        var userIdChild = Convert.ToInt32(item.UserId);
        //        if (item.UserCode == Common.Enum.UserType.User)
        //        {
        //            leftUsers = db.UserGenealogyTableLefts.Where(a => a.DownlineMemberId.Value.Equals(userIdChild)).FirstOrDefault();
        //            if (leftUsers == null)
        //            {
        //                listDownlineMember.Add(new NewUserRegistration() { UserId = item.UserId.Value, Username = item.Username, UserPosition = null });
        //            }
        //        }
        //    }
        //    if (listDownlineMember.Count != 0)
        //    {
        //            ViewBag.DownlineMemberListLeft = new SelectList(listDownlineMember, "UserId", "Username");
        //    }
        //    else
        //    {
        //        ViewBag.DownlineMemberListLeft = null;
        //    }
        //    //ViewBag.DownlineMemberList = listDownlineMembe
        //}
    }

}
