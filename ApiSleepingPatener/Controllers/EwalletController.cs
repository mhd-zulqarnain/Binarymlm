using ApiSleepingPatener.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ApiSleepingPatener.Controllers
{
    public class EwalletController : ApiController
    {
        //Overall transaction
        [HttpGet]
        [Route("wallet/overalllist/{userId}")] 
        public IHttpActionResult GetEWalletTransactionsOverAllList(int userId)
        {
            SleepingtestEntities db = new SleepingtestEntities();
            List<EWalletTransactionModel> List = new List<EWalletTransactionModel>();           
                List = db.EWalletTransactions.Where(a => a.UserId.Value.Equals(userId))
                    .Select(x => new EWalletTransactionModel
                    {
                        TransactionId = x.TransactionId,
                        TransactionSource = x.TransactionSource,
                        TransactionName = x.TransactionName,
                        AsscociatedMember = x.AsscociatedMember.Value,
                        Amount = x.Amount.Value,
                        TransactionDate = x.TransactionDate.Value
                    }).ToList();

         
            //return Json(new { data = List }, JsonRequestBehavior.AllowGet);
            return Ok(List);

        }

        //This Months transaction
        [HttpGet] 
        [Route("wallet/thismonth/{userId}")]
        public IHttpActionResult GetEWalletTransactionsThisMonthList(int userId)
        {            
            SleepingtestEntities db = new SleepingtestEntities();
            List<EWalletTransactionModel> List = new List<EWalletTransactionModel>();          

                List = db.EWalletTransactions.Where(a => a.UserId.Value.Equals(userId)
                    && a.TransactionDate.Value.Year.Equals(DateTime.Now.Year)
                    && a.TransactionDate.Value.Month.Equals(DateTime.Now.Month))
                    .Select(x => new EWalletTransactionModel
                    {
                        TransactionId = x.TransactionId,
                        TransactionSource = x.TransactionSource,
                        TransactionName = x.TransactionName,
                        AsscociatedMember = x.AsscociatedMember.Value,
                        Amount = x.Amount.Value,
                        TransactionDate = x.TransactionDate.Value
                    }).ToList();
            return Ok(List);
                 

        }
    }
}
