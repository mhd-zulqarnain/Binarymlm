using ApiSleepingPatener.Models;
using ApiSleepingPatener.Models.Account;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Http;


namespace ApiSleepingPatener.Controllers
{
   
    public class AccountController : ApiController
    {

        [Authorize]
        [HttpGet]
        [Route("api/account/getuser/{id}")]
        public IHttpActionResult GetUFirstUser(int id)
        {
            // Get user from dummy list

            //var userList = new List<User>(){
            //     new User(   2,
            //       "twt",
            //        "t@g.com",
            //        "test",
            //        "test") {

            //    },
            //      new User(  1,
            //       "twt",
            //        "t@g.com",
            //        "test",
            //        "test") {

            //    }
                
            //};

            List<NewUserRegistration> list = new List<NewUserRegistration>();
            SqlConnection connect = new SqlConnection(ConfigurationManager.ConnectionStrings["remote"].ConnectionString);

            if (connect.State != ConnectionState.Open)
                connect.Open();
            SqlCommand cmd = new SqlCommand("select * from NewUserRegistration where UserId = '" + id+"'", connect);
            try
            {
                SqlDataReader sdr = cmd.ExecuteReader();
                sdr.Read();
                int uid = Convert.ToInt32(sdr["UserId"].ToString());
                string uname = sdr["Username"].ToString();
                string pass = sdr["Password"].ToString();
                string Email = sdr["Email"].ToString();
               sdr.Close();
                connect.Close();
                return Ok(0);
            }
            catch (Exception n)
            {
                return Ok(0);
            }
            
           
        }
        //[HttpPost]
        //public IHttpActionResult ProfileSetup(ProfileSetting model, HttpPostedFileBase file,int userId)
        //{
        //    //var userId = Convert.ToInt32(Session["LogedUserID"].ToString());
        //    sleepingtestEntities dc = new sleepingtestEntities();
        //    SleepingTestTreeEntities dbTree = new SleepingTestTreeEntities();
        //    NewUserRegistration newuser = dc.NewUserRegistrations.Where(a => a.UserId.Equals(userId)).FirstOrDefault();

        //    if (newuser != null)
        //    {
        //        newuser.Name = model.Name;
        //        newuser.Username = model.Username;            
        //        newuser.Country = model.CountryName;
        //        newuser.Address = model.Address;
        //        var fileImage = model.DocumentImage;
        //        if (fileImage != null)
        //        {
        //            byte[] img = ConvertToBytes(fileImage);
        //            newuser.DocumentImage = img;
        //        }
        //        dc.SaveChanges();

        //        //dbTree.update_tree_name(userId, model.UserName);
        //        ModelState.Clear();
        //        return Json(new { success = true, message = "Update Successfully" }, JsonRequestBehavior.AllowGet);

        //    }

        //    this.AddNotification("Your profile has bees saved", NotificationType.SUCCESS);
        //    return RedirectToAction("ProfileSetup");
        //}


    }
}