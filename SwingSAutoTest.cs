using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Automation;

namespace autotest2
{
    class SwingSAutoTest
    {
        private const string IE_DRIVER_PATH = @"C:/Users/Administrator/Desktop/javascript/chromedriver_win32";

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

        public SwingSAutoTest()
        {
             ChromeDriver driver = new ChromeDriver(IE_DRIVER_PATH);
             driver.Url = "http://172.31.196.21:8060/websquare/websquare.html?w2xPath=/SWING/lib/xml/ZLIBSMAN90010.xml&coClCd=T&svrLocation=SWGS";
             driver.Manage().Window.Maximize();
            //Thread.Sleep(5000);
            // WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            Thread.Sleep(5000);
            IWebElement q11 = driver.FindElement(By.Id("ipt_loginId"));
            q11.SendKeys("HARDKKH7");
            IWebElement q12 = driver.FindElement(By.Id("btn_search"));
            q12.Click();
            Thread.Sleep(5000);
            waitTillElementisDisplayed(driver, By.Id("btn_findPop"), 10);
             Thread.Sleep(5000);
             IWebElement q = driver.FindElement(By.Id("btn_findPop"));
             q.Click();
             waitTillElementisDisplayed(driver, By.Id("wfm_findLayer_edt_keyword"), 10);
             IWebElement q2 = driver.FindElement(By.Id("wfm_findLayer_edt_keyword"));
             q2.SendKeys("자동납부 변경이력");

             waitTillElementisDisplayed(driver, By.Id("wfm_findLayer_btn_findBtn"), 10);
             IWebElement q3 = driver.FindElement(By.Id("wfm_findLayer_btn_findBtn"));
             q3.Click();

            //driver.Manage().Window.GetType().
             
           // getChromeUrl();

        }



        //For Chrome
        private const int WM_GETTEXTLENGTH = 0Xe;
        private const int WM_GETTEXT = 0Xd;

        [DllImport("user32.dll")]
        private extern static int SendMessage(IntPtr hWnd, uint Msg, int wParam, int lParam);
        [DllImport("user32.dll")]
        private extern static int SendMessage(IntPtr hWnd, uint Msg, int wParam, StringBuilder lParam);
        [DllImport("user32.dll", SetLastError = true)]
        private extern static IntPtr FindWindowEx(IntPtr parentHandle, IntPtr childAfter, string className, string windowTitle);

        public void getChromeUrl(IntPtr winHandle)
        {
            string browserUrl = null;
            IntPtr urlHandle = FindWindowEx(winHandle, IntPtr.Zero, "Chrome_AutocompleteEditView", null);
            const int nChars = 256;
            StringBuilder Buff = new StringBuilder(nChars);
            int length = SendMessage(urlHandle, WM_GETTEXTLENGTH, 0, 0);
            if (length > 0)
            {
                SendMessage(urlHandle, WM_GETTEXT, nChars, Buff);
                browserUrl = Buff.ToString();

                Debug.WriteLine(browserUrl);
            }
            else
            {
                Debug.WriteLine(browserUrl);
            }

        }
        public static IntPtr GetChromeHandle()
        {
            IntPtr ChromeHandle = default(IntPtr);
            Process[] Allpro = Process.GetProcesses();
            foreach (Process pro in Allpro)
            {
                if (pro.ProcessName == "chrome")
                {
                    ChromeHandle = pro.MainWindowHandle;
                    break;
                }
            }
            return ChromeHandle;
        }



    public void getChromeURL2()
        {
            Debug.WriteLine("============getChromeURL============");
            Process[] procsChrome = Process.GetProcessesByName("chrome");
            foreach (Process chrome in procsChrome)
            {
                Debug.WriteLine("============getChromeURL1111============");
                // the chrome process must have a window
                if (chrome.MainWindowHandle == IntPtr.Zero)
                {
                    continue;
                }

                // find the automation element
                AutomationElement elm = AutomationElement.FromHandle(chrome.MainWindowHandle);
                AutomationElement elmUrlBar = elm.FindFirst(TreeScope.Descendants,
                  new PropertyCondition(AutomationElement.NameProperty, "Address and search bar"));
                AutomationPattern[] patterns2 = elmUrlBar.GetSupportedPatterns();
                ValuePattern val2 = (ValuePattern)elmUrlBar.GetCurrentPattern(patterns2[0]);
                Debug.WriteLine("Chrome URL found: " + val2.Current.Value);

                // if it can be found, get the value from the URL bar
                if (elmUrlBar != null)
                {
                    AutomationPattern[] patterns = elmUrlBar.GetSupportedPatterns();
                    if (patterns.Length > 0)
                    {
                        ValuePattern val = (ValuePattern)elmUrlBar.GetCurrentPattern(patterns[0]);
                        Debug.WriteLine("Chrome URL found: " + val.Current.Value);
                    }
                }
            }
        }
    }
}
