using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web.Http;
using Microsoft.Owin.Security.OAuth;
using Newtonsoft.Json.Serialization;
using System.Net.Http.Headers;

namespace ApiSleepingPatener
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API configuration and services
            // Configure Web API to use only bearer token authentication.
            config.SuppressDefaultHostAuthentication();
            config.Filters.Add(new HostAuthenticationFilter(OAuthDefaults.AuthenticationType));
            config.Formatters.JsonFormatter.SupportedMediaTypes.Add(new MediaTypeHeaderValue("text/html"));
            config.MapHttpAttributeRoutes();


            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{action}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );

       

          //  config.Routes.MapHttpRoute(
          //     name: "DefaultApiWithAction",
          //     routeTemplate: "api/{controller}/GetPassword/{id}",
          //     defaults: null
          // );

          //  config.Routes.MapHttpRoute(
          //    name: "DefaultApiWithActions",
          //    routeTemplate: "api/{controller}/GetById/{id}",
          //    defaults: null
          //);
        }
    }
}
