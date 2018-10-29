using ApiSleepingPatener.Models;
using Newtonsoft.Json;
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
    [Authorize]
    public class NewUserDetailController : ApiController
    {
        [HttpGet]
        [Route("api/account/newuser/{username}")]
        public IHttpActionResult GetUFirstUser(string username)
        {
           
           

                using (remotetest dce = new remotetest())
                {
              
                var v = dce.NewUserRegistrations.Where(a => a.UserId == 1);


                //var CGP = (from a in dce.NewUserRegistrations
                          
                //              select a).ToList();
                //    var query = CGP.Count();

                    return Ok(v);
                
                //var CGP = (from a in en.NewUserRegistrations
                //           where a.UserId == 2
                //           select a).FirstOrDefault();

                // var json = JsonConvert.SerializeObject(CGP);
                // //Console.WriteLine(res);
                //return Ok(json);
            }


            // List < NewUserRegistration > list = new List<NewUserRegistration>();
            //SqlConnection connect = new SqlConnection(ConfigurationManager.ConnectionStrings["remote"].ConnectionString);

            //if (connect.State != ConnectionState.Open)
            //    connect.Open();
            //SqlCommand cmd = new SqlCommand("select * from NewUserRegistration where UserId = '" + id+"'", connect);
            //try
            //{
            //    SqlDataReader sdr = cmd.ExecuteReader();
            //    sdr.Read();
            //    int uid = Convert.ToInt32(sdr["UserId"].ToString());
            //    string uname = sdr["Username"].ToString();
            //    string pass = sdr["Password"].ToString();
            //    string Email = sdr["Email"].ToString();
            //   sdr.Close();
            //    connect.Close();
            //    return Ok(new NewUserRegistration(id, uname, Email, pass));
            //}
            //catch (Exception n)
            //{
            //    return Ok(0);
            //}
            
           
        }
    }
}