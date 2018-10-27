using System.Collections.Generic;
using ApiSleepingPatener.Models;
using System.Web.Http;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;

namespace ApiSleepingPatener.Controllers
{
    [System.Web.Mvc.RoutePrefix("api/user")]
    public class UserController : ApiController
    {
        // GET: User

         List<User> list = new List<User> {
               
                new User(   2,
                   "twt",
                    "t@g.com",
                    "test",
                    "test") {
                 
                },
                  new User(  1,
                   "twt",
                    "t@g.com",
                    "test",
                    "test") {

                }
            };
       

        public IEnumerable<User> Get()
        {
           
            return list;
        }

        [System.Web.Mvc.HttpGet]
        [System.Web.Mvc.Route("GetById/{id}")]
        public User GetById(int id)
        {
            return list[id];
        }

        [System.Web.Mvc.HttpGet]
        [System.Web.Mvc.Route("GetPassword/{id}")]
        public string GetPassword(int id)
        {
            return "Password "+list[id].Password;
        }

        [System.Web.Mvc.HttpPost]
        [System.Web.Mvc.Route("NewUser")]
        public IHttpActionResult NewUser([FromBody]User value)
        {
            list.Add(value);
            SqlConnection connect = new SqlConnection(ConfigurationManager.ConnectionStrings["local"].ConnectionString);
            if (connect.State != ConnectionState.Open)
                connect.Open();
            SqlCommand cmds;
            cmds = new SqlCommand("insert into BonusSetting values (2,'hello world',23)", connect);
            cmds.ExecuteNonQuery();
            connect.Close();
            return Ok(1);
        }

        //public string getEmail(int id)
        //{
        //    return "Email " + list[id].Email;
        //}

    }
}