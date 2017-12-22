using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;

namespace autotest2
{
    class TestWebClient
    {
        private string urlAddress = "http://172.31.196.21:8060/websquare/websquare.html?w2xPath=/SWING/lib/xml/ZLIBSMAN90010.xml&coClCd=T&svrLocation=SWGS";
        public TestWebClient()
        {
            WebClient client = new WebClient();
            string script = client.DownloadString(urlAddress);
            //HtmlAgilityPack.HtmlDocument doc = new HtmlAgilityPack.HtmlDocument();
            HtmlAgilityPack.HtmlDocument doc = new HtmlWeb().Load(urlAddress);
            List<string> words = doc.DocumentNode.DescendantNodes()
                    .Where(n => n.NodeType == HtmlNodeType.Text
                      && !string.IsNullOrWhiteSpace(HtmlEntity.DeEntitize(n.InnerText)))
                     // && n.ParentNode.Name != "style" && n.ParentNode.Name != "script")
                    .Select(n => HtmlEntity.DeEntitize(n.InnerText).Trim())
                    .Where(s => s.Length > 2).ToList();
            for (int i =0; i < words.Count; i ++)
            {
                File.AppendAllText("C:/test/test.txt", words[i]);
                
            }


            //File.AppendAllText("C:/test/test.txt", script);
            //string html = webClient.DownloadString(url);
            //string urlAddress = "http://google.com";

            /*
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(urlAddress);
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            if (response.StatusCode == HttpStatusCode.OK)
            {
                Stream receiveStream = response.GetResponseStream();
                StreamReader readStream = null;
                if (response.CharacterSet == null)
                    readStream = new StreamReader(receiveStream);
                else
                    readStream = new StreamReader(receiveStream, Encoding.GetEncoding(response.CharacterSet));
                string data = readStream.ReadToEnd();
                File.AppendAllText("C:/test/test.txt", data);
                response.Close();
                readStream.Close();
            }
            */



        }


    }
}
