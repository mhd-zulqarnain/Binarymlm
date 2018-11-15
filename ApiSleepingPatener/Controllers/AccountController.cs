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

        //Profile Update 
        [HttpPost]
        [Route("profileupdate/{userId}")]
        public IHttpActionResult ProfileSetup(UserModel model,int userId)
        {
            //var userId = Convert.ToInt32(Session["LogedUserID"].ToString());
            sleepingtestEntities dc = new sleepingtestEntities();
            SleepingTestTreeEntities dbTree = new SleepingTestTreeEntities();
            NewUserRegistration newuser = dc.NewUserRegistrations.Where(a => a.UserId.Equals(userId)).FirstOrDefault();
            if (newuser != null)
            {
                newuser.Name = model.Name;
                //newuser.Username = model.UserName;
                newuser.Password = model.Password;
                newuser.Country = model.Country;
                newuser.Address = model.Address;
                newuser.Phone = model.Phone;
                newuser.Email = model.Email;
                newuser.AccountTitle = model.AccountTitle;
                newuser.AccountNumber = model.AccountNumber;
                newuser.BankName = model.BankName;
              //  newuser.CNIC = model.CNIC;
                //if (Session["LogedUserCode"].ToString() == BinaryMLMSystem.Common.Enum.UserType.User.ToString())
                //{
                //    newuser.IsBlock = model.IsBlock = true;
                //}
                //else
                //{
                //    newuser.IsBlock = model.IsBlock = false;
                ////}
                //var fileImage1 = model.NICImage;
                //var fileImage2 = model.ProfileImage;
                //var fileImage3 = model.DocumentImage;
                //if (fileImage1 != null && fileImage2 != null && fileImage3 != null)
                //{
                //    byte[] img1 = fileImage1;
                //    byte[] img2 = fileImage2;
                //    byte[] img3 = fileImage1;
                //    newuser.NICImage = img1;
                //    newuser.ProfileImage = img2;
                //    newuser.DocumentImage = img3;
                //}
                dc.SaveChanges();
                //dbTree.update_tree_name(userId, model.UserName);
              //  ModelState.Clear();
              

            }

            //this.AddNotification("Your profile has bees saved", NotificationType.SUCCESS);
            return Ok(new { success = true, message = "Update Successfully" });
        }

    }
}