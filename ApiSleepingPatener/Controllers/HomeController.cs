using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ApiSleepingPatener.Controllers
{
    public class HomeController : Controller
    {
        public string Index(string id,string query)
        {
            string value = "Server is up and running";
            return value;
        }

        //public String Index(String id)
        //{
        //    String value = "Home Page" + id;
        //    return value;
        //}
    }
}
