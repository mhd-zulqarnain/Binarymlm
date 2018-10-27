using ApiSleepingPatener.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ApiSleepingPatener.Controllers
{
    [RoutePrefix("api/{controller}/{action}")]
    public class TestController : ApiController
    {

        static List<User> list = new List<User> {
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


        public User Get(int id)
        {
            return list[id];
        }


       
        public string ReadMyData(string param1)
        {
            return "test message";
        }

    }
}
