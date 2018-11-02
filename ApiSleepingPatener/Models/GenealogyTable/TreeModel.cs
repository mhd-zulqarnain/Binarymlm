using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.GenealogyTable
{
    public class TreeModel
    {
        public int key { get; set; }
        public string name { get; set; }
        public int parent { get; set; }
    }
}