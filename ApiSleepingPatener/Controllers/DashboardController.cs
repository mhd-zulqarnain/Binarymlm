using System.Collections.Generic;
using ApiSleepingPatener.Models;
using System.Web.Http;
using System.Data.Entity;
using System.Data;
using System.Linq;
namespace ApiSleepingPatener.Controllers
{
    [Authorize]
    [System.Web.Mvc.RoutePrefix("api/dashboard")]
    public class DashboardController : ApiController
    {
        [HttpGet]
        [Route("api/account/d/{id}")]
        public IHttpActionResult GetUFirstUser(int id)
        {
            using (SleepingPartnermanagementEntities dce = new SleepingPartnermanagementEntities())
            {
                var CGP = (from a in dce.EWalletWithdrawalFunds
                           where a.IsPending.Value == true
                           select a).ToList();
                var query = CGP.Count();
            }
            return Ok(0);

        }
    }
}