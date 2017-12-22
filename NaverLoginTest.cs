using OpenQA.Selenium;
using OpenQA.Selenium.IE;
using OpenQA.Selenium.Chrome;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Support.UI;

namespace autotest2
{
    class NaverLoginTest
    {


        private const string IE_DRIVER_PATH = @"C:/Users/Administrator/Desktop/javascript/chromedriver_win32";

        /*
         *  waitTillElementisDisplayed -> element가 보이는지 확인하는 함수   
         */

        public bool waitTillElementisDisplayed(IWebDriver driver, By by, int timeoutInSeconds)
        {
            bool elementDisplayed = false;

            for (int i = 0; i < timeoutInSeconds; i++)
            {
                try
                {
                    if (timeoutInSeconds > 0)
                    {
                        var wait = new WebDriverWait(driver, TimeSpan.FromSeconds(timeoutInSeconds));
                        wait.Until(drv => drv.FindElement(by));
                    }
                    elementDisplayed = driver.FindElement(by).Displayed;
                }
                catch
                { }
            }
            return elementDisplayed;

        }

        public NaverLoginTest(string a)
        {

            ChromeDriver driver = new ChromeDriver(IE_DRIVER_PATH);
            driver.Url = "http://172.31.196.21:8060/websquare/websquare.html?w2xPath=/SWING/lib/xml/ZLIBSMAN90010.xml&coClCd=T&svrLocation=SWGS";
            driver.Manage().Window.Maximize();
            //Thread.Sleep(5000);
           // WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            waitTillElementisDisplayed(driver, By.Name("btn_findPop"),5);

            IWebElement q = driver.FindElement(By.Name("btn_findPop"));
            q.SendKeys("자동납부 변경이력");
            waitTillElementisDisplayed(driver, By.Name("pw"), 5);
            IWebElement q2 = driver.FindElement(By.Name("pw"));
            q2.SendKeys("khj0923");
            q2.Submit();
            

        }

        public NaverLoginTest()
        {
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.IntroduceInstabilityByIgnoringProtectedModeSettings = true;
            options.RequireWindowFocus = true;
            //driver = new InternetExplorerDriver(options);
            IWebDriver driver = new InternetExplorerDriver(IE_DRIVER_PATH, options);
            driver.Url = "http://www.naver.com";
            driver.Manage().Window.Maximize();
            Thread.Sleep(5000);
            /*   Thread.Sleep(5000);
               IWebElement q = driver.FindElement(By.Id("query"));
               q.SendKeys("최신영화순위");
               driver.FindElement(By.Id("search_btn")).Click();
               Thread.Sleep(5000);


               // 1위 제목 출력
               var rank = driver.FindElement(By.ClassName("list_rank"));
               var rankOne = rank.FindElement(By.XPath(".//li[0]/h4/a"));
               string title = rankOne.Text;
               Console.WriteLine(title);


               driver.Close();*/


            //  driver.Url = "http://www.naver.com";
            //  Thread.Sleep(7000);
            //driver.Manage().Window.Maximize();
            //driver. //("loginframe");
            //driver.SwitchTo().
            //Frame(driver.FindElement(By.Id("loginframe")));
            // driver.SwitchTo().Frame("loginframe");
            IWebElement q = driver.FindElement(By.Name("id"));
            // q.Clear();
            q.SendKeys("rgntr");
            // Thread.Sleep(5000);
            IWebElement q2 = driver.FindElement(By.Name("pw"));
            q2.SendKeys("khj0923");
            Thread.Sleep(3000);
            q2.Submit();
        }

    }
}
