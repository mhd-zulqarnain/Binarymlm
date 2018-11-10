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
        //[Authorize]
        [HttpGet]
        [Route("dashboard/{userId}")]
        public IHttpActionResult DashBoard(int userId)
        {
            Dashboarddetails dbd = new Dashboarddetails();
            string totaldirectcommission = GetUserTotalDirectCommission(userId);
            string totalewalletcredit = GetEWalletCreditSum(userId);
            string totalGetPaymentsInProcessSum = GetPaymentsInProcessSum(userId);
            string totalGetUserTotalPackageCommission = GetUserTotalPackageCommission(userId);
            string totalGetEWalletDebitSum = GetEWalletDebitSum(userId); 
            string totalGetUserCurrentPackage = GetUserCurrentPackage(userId);
        //  string totalGetAllTotalLeftUserPV = GetAllTotalLeftUserPV(userId);
            //string totalGetAllTotalRightUserPV = GetAllTotalRightUserPV(userId);
            string totalGetUserDownlineMembers = GetUserDownlineMembers(userId);
            string totalGetPayoutHistorySum = GetPayoutHistorySum(userId);
            string totalGetUserTotalMatchingCommission = GetUserTotalMatchingCommission(userId);
            // string totalGetAllCurrentRewardInfo = GetAllCurrentRewardInfo(userId);
            string totalGetEWalletSummarySponsorBonus = GetEWalletSummarySponsorBonus(userId);

            string totalGetleftamount = GetTotalleftamount(userId);
            string totalGetrightamount = GetTotalrightamount(userId);
            string totalGetremaningleftamount = GetTotalremainingleftamount(userId);
            string totalGetremaningrightamount = GetTotalremainingrightamount(userId);
            //object
            dbd.totaldirectcommission = totaldirectcommission;
            dbd.GetEwalletCredit = totalewalletcredit;
            dbd.GetPaymentsInProcessSum = totalGetPaymentsInProcessSum;
            dbd.GetUserTotalPackageCommission = totalGetUserTotalPackageCommission;
            dbd.GetEWalletDebitSum = totalGetEWalletDebitSum;
            dbd.GetUserCurrentPackage = totalGetUserCurrentPackage;
            //dbd.GetAllTotalLeftUserPV = totalGetAllTotalLeftUserPV;
            //dbd.GetAllTotalRightUserPV = totalGetAllTotalRightUserPV;
            dbd.GetUserDownlineMembers = totalGetUserDownlineMembers;
            dbd.GetPayoutHistorySum = totalGetPayoutHistorySum;
            dbd.GetUserTotalMatchingCommission = totalGetUserTotalMatchingCommission;
            dbd.GetEWalletSummarySponsorBonus = totalGetEWalletSummarySponsorBonus;
            dbd.GetTotalleftamount = totalGetleftamount;
            dbd.GetTotalrightamount = totalGetrightamount;
            dbd.GetTotalremainingleftamount = totalGetremaningleftamount;
            dbd.GetTotalremainingrightamount = totalGetremaningrightamount;
            //  dbd.GetAllCurrentRewardInfo = totalGetAllCurrentRewardInfo; 


            return Ok(dbd);
        }
        public string GetUserTotalDirectCommission(int userId)
        {
            //var userId = Convert.ToInt32(Session["LogedUserID"].ToString());
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {
                var CGP = (from a in dc.EWalletTransactions
                           where a.UserId.Value == userId
                           && a.IsParentBonus.Value == true
                           select a).ToList();
                var query = CGP.Sum(x => x.Amount);
                return query.ToString();
            }



        }

        public string GetEWalletCreditSum(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {
                string UserTypeAdmin = Common.Enum.UserType.Admin.ToString();
                string UserTypeUser = Common.Enum.UserType.User.ToString();

                var CGP = (from a in dc.EWalletTransactions
                           where a.Credit.Value == true
                           && a.Debit.Value == false
                           && a.UserId.Value == userId
                           select a).ToList();
                var query = CGP.Sum(x => x.Amount);
                //return query.toretu
                return query.ToString();
            }



        }

        public string GetEWalletDebitSum(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {

                var Debit = (from a in dc.EWalletTransactions
                             where a.Credit.Value == false
                             && a.Debit.Value == true
                             && a.UserId.Value == userId
                             select a).ToList();
                var DebitValue = Debit.Sum(x => x.Amount);

                var Credit = (from a in dc.EWalletTransactions
                              where a.Credit.Value == true
                              && a.Debit.Value == false
                              && a.UserId.Value == userId
                              select a).ToList();
                var CreditValue = Credit.Sum(x => x.Amount);

                var Sum = DebitValue - CreditValue;
                return Sum.ToString();

            }
        }

        public string GetPaymentsInProcessSum(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {
                var CGP = (from a in dc.EWalletWithdrawalFunds
                           where a.IsActive.Value == true
                           && a.IsApproved.Value == true
                           && a.UserId.Value == userId
                           select a).ToList();
                var query = CGP.Sum(x => x.AmountPayble);
                return query.ToString();
            }
        }

        public string GetUserTotalPackageCommission(int userId)
        {

            using (sleepingtestEntities dc = new sleepingtestEntities())
            {
                var CGP = (from a in dc.EWalletTransactions
                           where a.UserId.Value == userId
                           && a.IsPackageBonus.Value == true
                           select a).ToList();
                var query = CGP.Sum(x => x.Amount);
                return query.ToString();
            }


        }

        public string GetUserCurrentPackage(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {
                NewUserRegistration newpckg = dc.NewUserRegistrations.Where(a => a.UserId.Equals(userId)).FirstOrDefault();

                Package pkge = dc.Packages.Where(a => a.PackageId.Equals(newpckg.UserPackage.Value)).FirstOrDefault();

                var PackageName = pkge.PackageName;
                var PackagePrice = pkge.PackagePrice.Value;
                return PackagePrice.ToString();
            }
        }

        ////public string GetAllTotalLeftUserPV(int userId)
        ////{
        ////    using (sleepingtestEntities dc = new sleepingtestEntities())
        ////    {
        ////        var TotalAmountLeftUsers = dc.GetParentChildsLeftSP(userId).ToList();
        ////        decimal TotalAmountLeftUsersShow = TotalAmountLeftUsers.Sum(x => x.PaidAmount.Value);
        ////        return TotalAmountLeftUsersShow.ToString();

        ////        //if (TotalAmountLeftUsersShow != 0)
        ////        //{
        ////        //    return TotalAmountLeftUsers.ToString();
        ////        //}
        ////        //else
        ////        //{
        ////        //    return Json(new { success = true, result = 0 }, JsonRequestBehavior.AllowGet);
        ////        //}

        ////    }


        ////}

        //public string GetAllTotalRightUserPV(int userId)
        //{
        //    using (sleepingtestEntities dc = new sleepingtestEntities())
        //    {
        //        var TotalAmountRightUsers = dc.GetParentChildsRightSP(userId).ToList();
        //        decimal TotalAmountRightUsersShow = TotalAmountRightUsers.Sum(x => x.PaidAmount.Value);
        //        //if (TotalAmountRightUsersShow != 0)
        //        //{
        //        //    return Json(new { success = true, result = TotalAmountRightUsersShow }, JsonRequestBehavior.AllowGet);
        //        //}
        //        //else
        //        //{
        //        //    return Json(new { success = true, result = 0 }, JsonRequestBehavior.AllowGet);
        //        //}
        //        return TotalAmountRightUsersShow.ToString();
        //    }
        //}

        public string GetUserDownlineMembers(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {               

                    var CGP = dc.GetParentChildsSP(userId).ToList();
                    var query = CGP.Count();
                return query.ToString();
                
            }
          
        }

        public string GetPayoutHistorySum(int userId)
        {

            using (sleepingtestEntities dc = new sleepingtestEntities())
            {               

                    var CGP = (from a in dc.EWalletWithdrawalFunds
                               where a.IsActive.Value == false && a.UserId == userId
                               && a.IsApproved.Value == true
                                && a.IsPaid.Value == true
                               select a).ToList();
                    var query = CGP.Sum(x => x.AmountPayble);
                return query.ToString();
                }            
        }
        public string GetUserTotalMatchingCommission(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {               
               
                    var CGP = (from a in dc.EWalletTransactions
                               where a.UserId.Value == userId
                               && a.IsMatchingBonus.Value == true
                               select a).ToList();
                    var query = CGP.Sum(x => x.Amount);

                return query.ToString();
                }

        }
        public string GetEWalletSummarySponsorBonus(int userId)
        {
            using (sleepingtestEntities dc = new sleepingtestEntities())
            {
                var Debit = (from eWallTr in dc.EWalletTransactions
                             where eWallTr.UserId == userId && eWallTr.Credit == false && eWallTr.Debit == true
                             select eWallTr).ToList();
                var DebitValue = Debit.Sum(x => x.Amount);

                var Credit = (from eWallTr in dc.EWalletTransactions
                              where eWallTr.UserId == userId && eWallTr.Credit == true && eWallTr.Debit == false
                              select eWallTr).ToList();
                var CreditValue = Credit.Sum(x => x.Amount);

                var Sum = DebitValue - CreditValue;

                //if (Sum != null)
                //{
                //    return Json(new { success = true, result = Sum }, JsonRequestBehavior.AllowGet);
                //}
                return Sum.ToString();
            }
            
        }

        public string GetTotalleftamount(int userId)
        {
            return  null;
            }

        public string GetTotalrightamount(int userId)
        {
            return null;
        }
        public string GetTotalremainingleftamount(int userId)
        {
            return null;
        }
        public string GetTotalremainingrightamount(int userId)
        {
            return null;
        }

    }




        //public string GetAllCurrentRewardInfo(int userId)
        //{
        //    using (sleepingtestEntities dc = new sleepingtestEntities())
        //    {

        //        UserReward usrReward = dc.UserRewards.Where(a => a.UserId.Value.Equals(userId)).OrderByDescending(o => o.RewardId.Value).FirstOrDefault();

        //        int MinValueId = 0;

        //        var TotalAmountLeftUsers = dc.GetParentChildsLeftSP(userId).ToList();
        //        var TotalAmountRightUsers = dc.GetParentChildsRightSP(userId).ToList();

        //        decimal TotalAmountLeftUsersShow = TotalAmountLeftUsers.Sum(x => x.PaidAmount.Value);
        //        decimal TotalAmountRightUsersShow = TotalAmountRightUsers.Sum(x => x.PaidAmount.Value);

        //        Reward rewardRightlimit = (from rwrd in dc.Rewards
        //                                   where rwrd.Rightlimit >= TotalAmountRightUsersShow
        //                                   select rwrd).FirstOrDefault();

        //        Reward rewardLeftlimit = (from rwrd in dc.Rewards
        //                                  where rwrd.Leftlimit >= TotalAmountLeftUsersShow
        //                                  select rwrd).FirstOrDefault();

        //        if (rewardLeftlimit == null && rewardRightlimit == null) //if all rewards complete
        //        {
        //            Reward rewardRightIfComplete = (from rwrd in dc.Rewards
        //                                            where rwrd.Rightlimit <= TotalAmountRightUsersShow
        //                                            select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //            Reward rewardLeftIfComplete = (from rwrd in dc.Rewards
        //                                           where rwrd.Leftlimit <= TotalAmountLeftUsersShow
        //                                           select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //            int MaxValueId = Math.Max((int)rewardRightIfComplete.Id, (int)rewardLeftIfComplete.Id);
        //            if (usrReward != null)
        //            {
        //                if (usrReward.RewardId == MaxValueId)
        //                {
        //                    //return Json(new
        //                    //{
        //                    //    success = true,
        //                    //    reward = "Complete",
        //                    //    remLpv = "0",
        //                    //    remRpv = "0",
        //                    //    rewardDetail = "Congrats! You have completed all rewards",
        //                    //    rewardDesignation = "Regional Manager"
        //                    //}, JsonRequestBehavior.AllowGet);
        //                }
        //                else
        //                {
        //                    MinValueId = Math.Min((int)rewardRightIfComplete.Id, (int)rewardLeftIfComplete.Id);
        //                }
        //            }



        //        }
        //        else if (rewardLeftlimit == null)
        //        {
        //            rewardRightlimit = (from rwrd in dc.Rewards
        //                                where rwrd.Rightlimit <= TotalAmountRightUsersShow
        //                                select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //            rewardLeftlimit = (from rwrd in dc.Rewards
        //                               where rwrd.Leftlimit <= TotalAmountRightUsersShow
        //                               select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //            MinValueId = Math.Min((int)rewardRightlimit.Id, (int)rewardLeftlimit.Id);

        //        }
        //        else if (rewardRightlimit == null)
        //        {
        //            rewardRightlimit = (from rwrd in dc.Rewards
        //                                where rwrd.Rightlimit <= TotalAmountRightUsersShow
        //                                select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //            rewardLeftlimit = (from rwrd in dc.Rewards
        //                               where rwrd.Leftlimit <= TotalAmountRightUsersShow
        //                               select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //            MinValueId = Math.Min((int)rewardRightlimit.Id, (int)rewardLeftlimit.Id);
        //        }
        //        else
        //        {
        //            MinValueId = Math.Min((int)rewardRightlimit.Id, (int)rewardLeftlimit.Id);
        //        }



        //        Reward reward = dc.Rewards.Where(a => a.Id.Equals(MinValueId)).FirstOrDefault();

        //        decimal LeftLimitPV = (decimal)reward.Leftlimit;
        //        decimal TotalLeftUserPV = TotalAmountLeftUsersShow;
        //        decimal MaxValueLeft = Math.Max(LeftLimitPV, TotalLeftUserPV);
        //        decimal RemainingLeftUserPV = MaxValueLeft - TotalLeftUserPV;


        //        decimal RightLimitPV = (decimal)reward.Rightlimit;
        //        decimal TotalRightUserPV = TotalAmountRightUsersShow;
        //        decimal MaxValueRight = Math.Max(RightLimitPV, TotalRightUserPV);
        //        decimal RemainingRightUserPV = MaxValueRight - TotalRightUserPV;

        //        GetCalculateCurrentReward(userId);
        //        //if (reward != null)
        //        //{
        //        //    //return reward.ToString();
        //        //    //return Json(new
        //        //    //{
        //        //    //    success = true,
        //        //    //    reward = "InComplete",
        //        //    //    remLpv = RemainingLeftUserPV,
        //        //    //    remRpv = RemainingRightUserPV,
        //        //    //    rewardDetail = reward.Description,
        //        //    //    rewardDesignation = reward.Designation
        //        //    //}, JsonRequestBehavior.AllowGet);
        //        //}
        //        //return RemainingLeftUserPV.ToString();
        //        return RemainingLeftUserPV.ToString() +""+ RemainingRightUserPV;
        //        //return (RemainingLeftUs);
        //    }


        //}

        //public void GetCalculateCurrentReward(int userId)
        //{
        //    using (sleepingtestEntities dc = new sleepingtestEntities())
        //    {
        //        UserReward usrReward = dc.UserRewards.Where(a => a.UserId.Value.Equals(userId)).OrderByDescending(o => o.RewardId.Value).FirstOrDefault();

        //        var TotalAmountLeftUsers = dc.GetParentChildsLeftSP(userId).ToList();
        //        var TotalAmountRightUsers = dc.GetParentChildsRightSP(userId).ToList();

        //        decimal TotalAmountLeftUsersShow = TotalAmountLeftUsers.Sum(x => x.PaidAmount.Value);
        //        decimal TotalAmountRightUsersShow = TotalAmountRightUsers.Sum(x => x.PaidAmount.Value);

        //        Reward rewardRightlimit = (from rwrd in dc.Rewards
        //                                   where rwrd.Rightlimit <= TotalAmountRightUsersShow
        //                                   select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();

        //        Reward rewardLeftlimit = (from rwrd in dc.Rewards
        //                                  where rwrd.Leftlimit <= TotalAmountLeftUsersShow
        //                                  select rwrd).OrderByDescending(o => o.Id).FirstOrDefault();


        //        if (rewardLeftlimit != null && rewardRightlimit != null)
        //        {
        //            int MaxValueId = Math.Max((int)rewardRightlimit.Id, (int)rewardLeftlimit.Id);

        //            Reward reward = dc.Rewards.Where(a => a.Id.Equals(MaxValueId)).FirstOrDefault();

        //            if (usrReward != null)
        //            {
        //                if (usrReward.RewardId != MaxValueId)
        //                {
        //                    NewUserRegistration user = dc.NewUserRegistrations.Where(a => a.UserId.Equals(userId)).FirstOrDefault();
        //                    UserReward usrRwrd = new UserReward();
        //                    usrRwrd.UserRewardName = reward.RewardName;
        //                    usrRwrd.UserRewardDesignation = reward.Designation;
        //                    usrRwrd.UserLeftAmount = TotalAmountLeftUsersShow;
        //                    usrRwrd.UserRightAmount = TotalAmountRightUsersShow;
        //                    usrRwrd.UserId = userId;
        //                    usrRwrd.UserName = user.Username;
        //                    usrRwrd.CreateDate = DateTime.Now;
        //                    usrRwrd.RewardId = reward.Id;
        //                    usrRwrd.IsClaimByUser = false;
        //                    usrRwrd.IsRewardedByAdmin = false;
        //                    dc.UserRewards.Add(usrRwrd);
        //                    dc.SaveChanges();

        //                    user.UserDesignation = reward.Designation;
        //                    dc.SaveChanges();
        //                }
        //            }
        //            else
        //            {
        //                NewUserRegistration user = dc.NewUserRegistrations.Where(a => a.UserId.Equals(userId)).FirstOrDefault();
        //                UserReward usrRwrd = new UserReward();
        //                usrRwrd.UserRewardName = reward.RewardName;
        //                usrRwrd.UserRewardDesignation = reward.Designation;
        //                usrRwrd.UserLeftAmount = TotalAmountLeftUsersShow;
        //                usrRwrd.UserRightAmount = TotalAmountRightUsersShow;
        //                usrRwrd.UserId = userId;
        //                usrRwrd.UserName = user.Username;
        //                usrRwrd.CreateDate = DateTime.Now;
        //                usrRwrd.RewardId = reward.Id;
        //                usrRwrd.IsClaimByUser = false;
        //                usrRwrd.IsRewardedByAdmin = false;
        //                dc.UserRewards.Add(usrRwrd);
        //                dc.SaveChanges();

        //                user.UserDesignation = reward.Designation;
        //                dc.SaveChanges();
        //            }
        //        }

        //    }
        //    // return View();

        //}

    }



