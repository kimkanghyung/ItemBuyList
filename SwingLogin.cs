using OpenQA.Selenium;
using OpenQA.Selenium.IE;
using OpenQA.Selenium.Chrome;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using OpenQA.Selenium.Support.UI;

namespace autotest2
{

   
    class SwingLogin
    {
        //private const string IE_DRIVER_PATH1 = @"C:/Users/Administrator/Desktop/javascript/IEDriverServer_Win32_3.8.0";
        private const string IE_DRIVER_PATH2 = @"C:/Users/Administrator/Desktop/javascript/IEDriverServer_Win32_3.8.0";

        public SwingLogin()
        {
            
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.IntroduceInstabilityByIgnoringProtectedModeSettings = true;
            options.RequireWindowFocus = true;
            //driver = new InternetExplorerDriver(options);

            IWebDriver driver = new InternetExplorerDriver(IE_DRIVER_PATH2, options);
            
            driver.Url = "https://swings.sktelecom.com:18080/";
          
            driver.Manage().Window.Maximize();
            Thread.Sleep(8000);
            // WebDriverWait wait =
            
            IWebElement detailFrame = driver.FindElement(By.XPath("//iframe[@id='loginFormFrame']"));
            driver.SwitchTo().Frame(detailFrame);
            Thread.Sleep(7000);
            //By.XPath("//span[contains(text(), 'important')]")
            IWebElement q = driver.FindElement(By.Name("USER"));
            //IWebElement q = driver.FindElement(By.XPath("//input[contains(text(), '아이디')]"));
            // q.Clear();
            q.SendKeys("HARDKKH7");
            // Thread.Sleep(5000);
            IWebElement q2 = driver.FindElement(By.Name("PASSWORD"));
            q2.SendKeys("rkdgus0427#");
            Thread.Sleep(3000);
            q2.Submit();

            Thread.Sleep(5000);
            /*SWING CLICK*/
            driver.SwitchTo().Window(driver.WindowHandles.Last());

            IWebElement q3 = driver.FindElement(By.XPath("//span[contains(text(),'SWING')]"));
            q3.Click();

            Thread.Sleep(15000);

            //ChromeDriver cmddiver;
            driver.SwitchTo().Window(driver.WindowHandles.Last());
            //cmddiver.SwitchTo().Window(driver.WindowHandles.Last());
            Thread.Sleep(15000);
            //IWebElement q4 = driver.FindElement(By.Id("div1_BTN_btn_search"));
            //q4.Click();
        }
        
    }
}
