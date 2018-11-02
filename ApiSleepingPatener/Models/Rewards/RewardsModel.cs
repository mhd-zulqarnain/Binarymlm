using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Models.Rewards
{
    public class RewardsModel
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "please enter")]
        public string Designation { get; set; }

        [Required(ErrorMessage = "please enter")]
        public decimal Leftlimit { get; set; }

        [Required(ErrorMessage = "please enter")]
        public decimal Rightlimit { get; set; }

        [Required(ErrorMessage = "please select")]
        public byte[] Rewardimage { get; set; }

        public bool Isactive { get; set; }

        [Required(ErrorMessage = "please enter")]
        public string RewardName { get; set; }

        [Required(ErrorMessage = "please enter")]
        public string Description { get; set; }

    }
}