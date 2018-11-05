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
    public class DasboardDetailController : ApiController
    {
        [Authorize]
        [HttpGet]
        [Route("dashboard/{userId}")]
        public IHttpActionResult DashBoard(int userId)
        {
            Dashboarddetails dbd = new Dashboarddetails();
            string totaldirectcommission = GetUserTotalDirectCommission(userId);

            dbd.totaldirectcommission = totaldirectcommission;
            return Ok(dbd);
        }
        public string GetUserTotalDirectCommission(int userId)
        {
            //var userId = Convert.ToInt32(Session["LogedUserID"].ToString());
            using (SleepingtestEntities dc = new SleepingtestEntities())
            {


                var CGP = (from a in dc.EWalletTransactions
                           where a.UserId.Value == userId
                           && a.IsParentBonus.Value == true
                           select a).ToList();
                var query = CGP.Sum(x => x.Amount);
                return query.ToString();
            }



        }
    }
}
