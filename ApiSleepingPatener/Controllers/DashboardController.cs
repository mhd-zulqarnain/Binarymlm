using System.Collections.Generic;
using ApiSleepingPatener.Models;
using System.Web.Http;
using System.Data.Entity;
using System.Data;
using System.Linq;
using System;

namespace ApiSleepingPatener.Controllers
{
    [Authorize]
    public class DashboardController : ApiController
    {
        [HttpGet]
        [Route("api/dashboard/{id}")]
        public string GetUFirstUser(int id)
        {
            try
            {
                using (remotetest dce = new remotetest())
                {
                    //var CGP = (from a in dce.EWalletWithdrawalFunds
                    //           where a.IsPending.Value == true
                    //           select a).ToList();
                    //var query = CGP.Count();
                    BonusSetting v = dce.BonusSettings.Where(a => a.BonusSettingId == 1).FirstOrDefault();
                    

                    return v.BonusSettingName.ToString();
                }

            }catch(Exception e)
            {

                return e.ToString();
            }
        }
    }
}