using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiSleepingPatener.Common
{
    public class Enum
    {

        public static class UserType
        {
            public static string Admin = "Adm";
            public static string User = "Usr";
        }

        public static class UserPosition
        {
            public static string Left = "Left";
            public static string Right = "Right";
            public static string Center = "Center";
        }

        public static class Actions
        {
            public static string Save = "Save";
            public static string Cancel = "Cancel";
            public static string Back = "Back";
            public static string Yes = "Yes";
        }

        public static class PaymentSource
        {
            public static string BankAccount = "Bank Account";
            public static string OnlinePayment = "Online Payment";
        }

        public static class AmountSource
        {
            public static string Withdrawal = "Withdrawal";
            public static string SponsorBonus = "Sponsor Bonus";
            public static string PackageBonus = "Package Bonus";
        }

        public static class AmountDebitOrCredit
        {
            public static string Debit = "Debit";
            public static string Credit = "Credit";
        }

        public static class EWalletPayoutTimePeriod
        {
            public static string Daily = "Daily";
            public static string Weekly = "Weekly";
            public static string Monthly = "Monthly";
            public static string Yearly = "Yearly";
        }

        public static class UpgradeInvestmentPaymentSource
        {
            public static string BankAccount = "Bank Account";
            public static string EWalletBalance = "E-Wallet Balance";
        }

        public static class UserStatus
        {
            public static string Pending = "Pending";
            public static string Approved = "Approved";
            public static string Rejected = "Rejected";
        }

        public static class UserAsSPorSM
        {
            public static string SleepingPartner = "SleepingPartner";
            public static string SalesExecutive = "SalesExecutive";
        }
    }
}