using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using CefSharp.WinForms;
using CefSharp;
using System.Diagnostics;
using System.IO;

namespace autotest2
{
    public partial class Form2 : Form
    {
        public ChromiumWebBrowser chromiumWebBrowser;
        private string urlAddress = "http://172.31.196.21:8060/websquare/websquare.html?w2xPath=/SWING/lib/xml/ZLIBSMAN90010.xml&coClCd=T&svrLocation=SWGS";

        public Form2()
        {
            InitializeComponent();
            InitialChrome();
        }

        public void InitialChrome()
        {
            CefSettings settings = new CefSettings();
            Cef.Initialize(settings);
            chromiumWebBrowser = new ChromiumWebBrowser(urlAddress);
            this.Controls.Add(chromiumWebBrowser);
            chromiumWebBrowser.Dock = DockStyle.Fill;
            
            //GetFrame(chromiumWebBrowser);


        }

        private void Form2_FormClosing(object sender, FormClosingEventArgs e)
        {
            Cef.Shutdown();
            
        }

        private void Form2_FormClosed(object sender, FormClosedEventArgs e)
        {
            Cef.Shutdown();
            
        }

        public async Task<IFrame> GetFrameAsync(ChromiumWebBrowser browser)
        {
            IFrame frame = null;

            var identifiers = browser.GetBrowser().GetFrameIdentifiers();

            foreach (var i in identifiers)
            {
                frame = browser.GetBrowser().GetFrame(i);
                string source = await frame.GetSourceAsync();
                Debug.WriteLine("NAME = " + frame);
                File.AppendAllText("C:/test/test4.txt", source);
            }

            return null;
        }

        public async Task GetSourceAsync()
        {
           string html = await chromiumWebBrowser.GetBrowser().MainFrame.GetSourceAsync();
            File.AppendAllText("C:/test/test3.txt", html);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            GetFrameAsync(chromiumWebBrowser);
            chromiumWebBrowser.GetBrowser().GetHost().SendMouseClickEvent(0, 0, MouseButtonType.Left, false, 1, CefEventFlags.None);
            System.Threading.Thread.Sleep(100);
            chromiumWebBrowser.GetBrowser().GetHost().SendMouseClickEvent(0, 0, MouseButtonType.Left, true, 1, CefEventFlags.None);
            Task ts = GetSourceAsync();
            Task ts1 = GetFrameAsync(chromiumWebBrowser);
        }

        private void devBt_Click(object sender, EventArgs e)
        {
            chromiumWebBrowser.GetBrowser().ShowDevTools();
        }


    }
}
