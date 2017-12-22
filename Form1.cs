using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Threading;
/*InputSimulater */
using WindowsInput;
using WindowsInput.Native;

namespace autotest2
{
    public partial class Form1 : Form
    {

        enum eventGubun
        {
            EventMouseLeftClick
          , EventMouseLeftDoubleClick
          , KeyboardInput
        }
        private eventGubun m_eventGubun;

        int compareImageWidth = 0;
        int compareImageHeight = 0;
        Bitmap image_size;


        public Form1()
        {
            InitializeComponent();
            MouseHook.Start();
            MouseHook.MouseAction += new EventHandler(Event);
            setDataGrid();
            // Cv2.Canny(src, dst, 50, 200);           // 외곽선 추출도 해보고.
            // Cv2.MatchTemplate
            //    using (new Window("src image", src))    // 윈도우 화면에 보임
            //  using (new Window("dst image", dst))
            {
                //     Cv2.WaitKey();                      // 키가 입력되면 그림 지움
            }
            //   webBrowser1.Navigate("https://swings.sktelecom.com:18080/");
        }


        public void setDataGrid()
        {
            this.Controls.Add(dataGridView1);

            dataGridView1.ColumnCount = 7;

            dataGridView1.Columns[0].HeaderText = "순번";
            dataGridView1.Columns[1].HeaderText = "테스트케이스";
            dataGridView1.Columns[2].HeaderText = "이미지경로";
            dataGridView1.Columns[3].HeaderText = "구분";
            dataGridView1.Columns[4].HeaderText = "텍스트";
            dataGridView1.Columns[5].HeaderText = "X좌표";
            dataGridView1.Columns[6].HeaderText = "Y좌표";

            for (int i = 0; i < 5; i++)
                dataGridView1.Columns[i].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

           // dataGridView1.Rows[0].Cells[0].Value = 1;

        }

        private void button1_Click(object sender, EventArgs e)
        {
            NaverLoginTest nt = new NaverLoginTest();
        }

        private void Swinglogin_Click(object sender, EventArgs e)
        {
            SwingLogin sw = new SwingLogin();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("explorer.exe", "https://swings.sktelecom.com:18080/");



        }

        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {

            Debug.WriteLine("webBrowser1_DocumentCompleted");

            //  if (e.Url.AbsoluteUri == webBrowser1.Url.AbsoluteUri)
            findid();
        }

        private void findid()
        {
            Debug.WriteLine("findid!!");
            //   HtmlDocument doc ;
            // string tableId = "USER";
            //HtmlElement id = doc.GetElementsByTagName("USER");
            // HtmlElement cm = doc.GetElementById(tableId);
            //   doc.GET

            // HtmlElementCollection inputcollection;
            //= doc.GetElementsByTagName("input");
            //HtmlElementCollection tds = trs[1].GetElementsByTagName("TD");
            /*foreach (HtmlElement el in inputcollection)
            {
                Debug.WriteLine(el.GetAttribute("name"));
                
                if (el.GetAttribute("name") == "loginId")
                {
                    el.SetAttribute("value","HARDKKH7");
                    //break;
                }
            }*/
        }

        private void button3_Click(object sender, EventArgs e)
        {
            detectform detectform = new detectform();
            detectform.InstanceRef = this;
            detectform.pb = this.pictureBox1;
            detectform.FormSendEvent += new detectform.FormSendDataHandler(DieaseUpdateEventMethod);
            
            this.imagepathnm.Text = detectform.imagename;
            detectform.Show();
        }

        private void DieaseUpdateEventMethod(object sender,int x, int y)
        {
            //폼2에서 델리게이트로 이벤트 발생하면 현재 함수 Call
            this.imagepathnm.Text = sender.ToString();
            this.firstcurrentx.Text = x.ToString();
            this.firstcurrenty.Text = y.ToString();
        }

      

        private void findimage_Click(object sender, EventArgs e)
        {
            string image = imagepathnm.Text;
            Bitmap image_size = new Bitmap(image);
            compareImageWidth = image_size.Width;
            compareImageHeight = image_size.Height;
            image_size.Dispose();

            //string[] results = UseImageSearch(image, this.difftxt.Text);
            string[] results =  UseImageSearch(image, this.difftxt.Text, Int32.Parse(this.firstcurrentx.Text)
                              , Int32.Parse(this.firstcurrenty.Text));
            if (results == null)
            {
                MessageBox.Show("이미지없음");
            }
            else
            {
                //compareImageWidth = getWidthSizeCompareImage(image);
                // compareImageHeight = getHeightSizeCompareImage(image);
                //MessageBox.Show(results[1] + ", " + results[2]);
                this.xposition.Text = results[1];
                this.yposion.Text = results[2];
                MoveCursor(Int32.Parse(results[1]), Int32.Parse(results[2]));
                

            }
        }




        public void Event(object sender, EventArgs e)
        {
            Console.WriteLine("Left mouse click!");
            Debug.WriteLine("커서 X = " + Cursor.Position.X);
            Debug.WriteLine("커서 Y = " + Cursor.Position.Y);
            this.curpositionx.Text = Cursor.Position.X.ToString();
            this.curpositiony.Text = Cursor.Position.Y.ToString();


        }


        [DllImport(@"C:/Users/Administrator/Desktop/javascript/ImageSearchDLL.dll")]
        public static extern IntPtr ImageSearch(int x, int y, int right, int bottom, [MarshalAs(UnmanagedType.LPStr)]string imagePath);

        public static string[] UseImageSearch(string imgPath, string tolerance)
        {
            imgPath = "*" + tolerance + " " + imgPath;


            int right = Screen.PrimaryScreen.Bounds.Width;
            int bottom = Screen.PrimaryScreen.Bounds.Height;

            Debug.WriteLine("X = " + right);
            Debug.WriteLine("Y = " + bottom);

            //IntPtr result = ImageSearch(0, 0, right, bottom, imgPath);
            IntPtr result = ImageSearch(0, 0, 500, 500, imgPath);
            string res = Marshal.PtrToStringAnsi(result);

            if (res[0] == '0') return null;

            string[] data = res.Split('|');

            int x; int y;
            int.TryParse(data[1], out x);
            int.TryParse(data[2], out y);

            return data;
        }

        public static string[] UseImageSearch(string imgPath, string tolerance,int startx,int starty)
        {
            imgPath = "*" + tolerance + " " + imgPath;

            int strdlenght = 300;


            int fullscreenright = Screen.PrimaryScreen.Bounds.Width;
            int fullscreenbottom = Screen.PrimaryScreen.Bounds.Height;

            Debug.WriteLine("X = " + fullscreenright);
            Debug.WriteLine("Y = " + fullscreenbottom);

            //IntPtr result = ImageSearch(0, 0, right, bottom, imgPath);
            int topLeftX = startx - strdlenght;
            int topLeftY = starty - strdlenght;
            int BottomRightX = startx + strdlenght;
            int BottomRightY = starty + strdlenght;

            if (topLeftX < 0) topLeftX = 0;
            if (topLeftY < 0) topLeftY = 0;
            if (BottomRightX > fullscreenright) BottomRightX = fullscreenright;
            if (BottomRightY > fullscreenbottom) BottomRightY = fullscreenbottom;

            IntPtr result = ImageSearch(topLeftX , topLeftY , BottomRightX , BottomRightY , imgPath);
            string res = Marshal.PtrToStringAnsi(result);

            if (res[0] == '0') return null;

            string[] data = res.Split('|');

            int x; int y;
            int.TryParse(data[1], out x);
            int.TryParse(data[2], out y);

            return data;
        }

        private void MoveCursor2(int move_x, int move_y, string gubun,string str)
        {

            Debug.WriteLine("이미지찾음");
            Debug.WriteLine("이미지 X = " + compareImageWidth);
            Debug.WriteLine("이미지 Y = " + compareImageHeight);
            //this.Cursor = new Cursor(Cursor.Current.Handle);
            Thread.Sleep(1000);
            if (gubun.Equals("C"))
            {
                Cursor.Position = new System.Drawing.Point(move_x + compareImageWidth / 2, move_y + compareImageHeight / 2);
            }else if (gubun.Equals("T"))
            {
                Cursor.Position = new System.Drawing.Point(move_x + compareImageWidth + 20, move_y + compareImageHeight / 2);
            }
                
            Thread.Sleep(500);
            /*
             EventMouseLeftClick
             EventMouseLeftDoubleClick
             KeyboardInput
             */
            if (gubun.Equals("C"))
            {
                MouseHook.LeftClick();

            }
            else if (gubun.Equals("EventMouseLeftDoubleClick"))
            {
                MouseHook.LeftDoubleClick();

            }
            else if (gubun.Equals("T"))
            {
                MouseHook.LeftClick();
                keyboardSend(str);
            }


        }

        private void MoveCursor(int move_x, int move_y)
        {

            Debug.WriteLine("이미지찾음");
            Debug.WriteLine("이미지 X = " + compareImageWidth);
            Debug.WriteLine("이미지 Y = " + compareImageHeight);
            //this.Cursor = new Cursor(Cursor.Current.Handle);
            Thread.Sleep(1000);
            Cursor.Position = new System.Drawing.Point(move_x + compareImageWidth / 2, move_y + compareImageHeight / 2);
            if (this.m_eventGubun.ToString().Equals("EventMouseLeftClick"))
            {
                Cursor.Position = new System.Drawing.Point(move_x + compareImageWidth / 2, move_y + compareImageHeight / 2);
            }
            else if (this.m_eventGubun.ToString().Equals("KeyboardInput"))
            {
                Cursor.Position = new System.Drawing.Point(move_x + compareImageWidth + 20, move_y + compareImageHeight / 2);
            }
            Thread.Sleep(500);
            /*
             EventMouseLeftClick
             EventMouseLeftDoubleClick
             KeyboardInput
             */
            if (this.m_eventGubun.ToString().Equals("EventMouseLeftClick"))
            {
                MouseHook.LeftClick();

            }
            else if (this.m_eventGubun.ToString().Equals("EventMouseLeftDoubleClick"))
            {
                MouseHook.LeftDoubleClick();

            }
            else if (this.m_eventGubun.ToString().Equals("KeyboardInput"))
            {
                MouseHook.LeftClick();
                keyboardSend(this.sendText.Text);
            }


        }

        private void wholecapture_Click(object sender, EventArgs e)
        {
            ScreenShot.WholeCaptureImage();
        }

        private void label3_Click(object sender, EventArgs e)
        {

        }
        /*클릭*/
        private void radioButton1_Click(object sender, EventArgs e)
        {
            this.m_eventGubun = eventGubun.EventMouseLeftClick;
        }
        /*더블클릭*/
        private void radioButton2_Click(object sender, EventArgs e)
        {
            this.m_eventGubun = eventGubun.EventMouseLeftDoubleClick;
        }
        /*입력 이벤트*/
        private void radioButton3_Click(object sender, EventArgs e)
        {
            this.m_eventGubun = eventGubun.KeyboardInput;
        }

        private void procheck_Click(object sender, EventArgs e)
        {
            CheckRunProcess cp = new CheckRunProcess();
        }


        public void keyboardSend(string str)
        {
            InputSimulator sim = new InputSimulator();
            // InputSimulator.SimulateTextEntry("Say hello!");
            sim.Keyboard.TextEntry(str);
        }

        private void Addimage_Click(object sender, EventArgs e)
        {
            int rowcnt = dataGridView1.Rows.Count;
            DataGridViewRowCollection rowCollection = dataGridView1.Rows;
            rowCollection.Add(new object[] { rowcnt + 1, null, this.imagepathnm.Text, null , null ,this.firstcurrentx.Text, this.firstcurrenty.Text });
           // dataGridView1.Rows.Add();
           // dataGridView1.Rows[rowcnt].Cells[0].Value = rowcnt+1;
        }

        private void readDataGrid()
        {
            int rowcnt = dataGridView1.Rows.Count;

            for (int i = 0; i < rowcnt ; i++)
            {

                Debug.WriteLine("================="+ i + "==================");
                Debug.WriteLine("=================" + rowcnt + "==================");
                if (dataGridView1.Rows[i] != null)
                {
                    Bitmap image_size = new Bitmap(dataGridView1.Rows[i].Cells[2].Value.ToString());
                    compareImageWidth = image_size.Width;
                    compareImageHeight = image_size.Height;
                    image_size.Dispose();
                    Debug.WriteLine(dataGridView1.Rows[i].Cells[1].Value.ToString());

                    string[] results = UseImageSearch(dataGridView1.Rows[i].Cells[2].Value.ToString(), this.difftxt.Text, Int32.Parse(dataGridView1.Rows[i].Cells[5].Value.ToString())
                              , Int32.Parse(dataGridView1.Rows[i].Cells[6].Value.ToString()));

                    if (results == null)
                    {
                        MessageBox.Show("이미지없음");
                    }
                    else
                    {
                        //compareImageWidth = getWidthSizeCompareImage(image);
                        // compareImageHeight = getHeightSizeCompareImage(image);
                        //MessageBox.Show(results[1] + ", " + results[2]);
                        this.xposition.Text = results[1];
                        this.yposion.Text = results[2];
                        if (dataGridView1.Rows[i].Cells[4].Value.ToString() == null) dataGridView1.Rows[i].Cells[4].Value = " ";

                        MoveCursor2(Int32.Parse(results[1]), Int32.Parse(results[2]), dataGridView1.Rows[i].Cells[3].Value.ToString(), dataGridView1.Rows[i].Cells[4].Value.ToString());
                        Thread.Sleep(1000);

                    }
                    //if()
                    /*
                    int colcnt = dataGridView1.Columns.Count;
                    Debug.WriteLine("colcnt [" + colcnt + "]");
                    for (int j = 0; j < colcnt; j++)
                    {
                        Debug.WriteLine("cnt [" + rowcnt + "]" + "=" + "[" + j + "]" + dataGridView1.Rows[i].Cells[j].Value.ToString());
                    }
                    */

                }
            }


        }

        private void button2_Click_1(object sender, EventArgs e)
        {
            readDataGrid();
        }

        private void SwingAT_Click(object sender, EventArgs e)
        {
            SwingSAutoTest sat = new SwingSAutoTest();
        }


        public static class MouseHook
        {
            public static event EventHandler MouseAction = delegate { };

            public static void Start()
            {
                _hookID = SetHook(_proc);


            }
            public static void stop()
            {
                UnhookWindowsHookEx(_hookID);
            }

            private static LowLevelMouseProc _proc = HookCallback;
            private static IntPtr _hookID = IntPtr.Zero;

            private static IntPtr SetHook(LowLevelMouseProc proc)
            {
                using (Process curProcess = Process.GetCurrentProcess())
                using (ProcessModule curModule = curProcess.MainModule)
                {
                    return SetWindowsHookEx(WH_MOUSE_LL, proc,
                      GetModuleHandle(curModule.ModuleName), 0);
                }
            }

            private delegate IntPtr LowLevelMouseProc(int nCode, IntPtr wParam, IntPtr lParam);

            private static IntPtr HookCallback(
              int nCode, IntPtr wParam, IntPtr lParam)
            {
                if (nCode >= 0 && MouseMessages.WM_LBUTTONDOWN == (MouseMessages)wParam)
                {
                    MSLLHOOKSTRUCT hookStruct = (MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT));
                    MouseAction(null, new EventArgs());
                }
                return CallNextHookEx(_hookID, nCode, wParam, lParam);
            }

            private const int WH_MOUSE_LL = 14;

            private enum MouseMessages
            {
                WM_LBUTTONDOWN = 0x0201,
                WM_LBUTTONUP = 0x0202,
                WM_MOUSEMOVE = 0x0200,
                WM_MOUSEWHEEL = 0x020A,
                WM_RBUTTONDOWN = 0x0204,
                WM_RBUTTONUP = 0x0205
            }

            [StructLayout(LayoutKind.Sequential)]
            private struct POINT
            {
                public int x;
                public int y;
            }

            [StructLayout(LayoutKind.Sequential)]
            private struct MSLLHOOKSTRUCT
            {
                public POINT pt;
                public uint mouseData;
                public uint flags;
                public uint time;
                public IntPtr dwExtraInfo;
            }

            [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
            private static extern IntPtr SetWindowsHookEx(int idHook,
              LowLevelMouseProc lpfn, IntPtr hMod, uint dwThreadId);

            [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            private static extern bool UnhookWindowsHookEx(IntPtr hhk);

            [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
            private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode,
              IntPtr wParam, IntPtr lParam);

            [DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
            private static extern IntPtr GetModuleHandle(string lpModuleName);
            /*mouse click event */
            [DllImport("user32.dll")]
            static extern void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);
            private const int MOUSEEVENTF_MOVE = 0x0001;
            private const int MOUSEEVENTF_LEFTDOWN = 0x0002;
            private const int MOUSEEVENTF_LEFTUP = 0x0004;
            private const int MOUSEEVENTF_RIGHTDOWN = 0x0008;
            private const int MOUSEEVENTF_RIGHTUP = 0x0010;
            private const int MOUSEEVENTF_MIDDLEDOWN = 0x0020;
            private const int MOUSEEVENTF_MIDDLEUP = 0x0040;
            private const int MOUSEEVENTF_ABSOLUTE = 0x8000;

            public static void LeftClick()
            {
                mouse_event(MOUSEEVENTF_LEFTDOWN, System.Windows.Forms.Control.MousePosition.X, System.Windows.Forms.Control.MousePosition.Y, 0, 0);
                mouse_event(MOUSEEVENTF_LEFTUP, System.Windows.Forms.Control.MousePosition.X, System.Windows.Forms.Control.MousePosition.Y, 0, 0);
            }

            public static void LeftDoubleClick()
            {
                mouse_event(MOUSEEVENTF_LEFTDOWN, System.Windows.Forms.Control.MousePosition.X, System.Windows.Forms.Control.MousePosition.Y, 0, 0);
                mouse_event(MOUSEEVENTF_LEFTUP, System.Windows.Forms.Control.MousePosition.X, System.Windows.Forms.Control.MousePosition.Y, 0, 0);
                mouse_event(MOUSEEVENTF_LEFTDOWN, System.Windows.Forms.Control.MousePosition.X, System.Windows.Forms.Control.MousePosition.Y, 0, 0);
                mouse_event(MOUSEEVENTF_LEFTUP, System.Windows.Forms.Control.MousePosition.X, System.Windows.Forms.Control.MousePosition.Y, 0, 0);
            }

        }

        private void DelBt_Click(object sender, EventArgs e)
        {
            foreach (DataGridViewRow item in this.dataGridView1.SelectedRows)
            {
                dataGridView1.Rows.RemoveAt(item.Index);
            }
        }

        private void WebClient_Click(object sender, EventArgs e)
        {
            Form2 f2 = new Form2();
            f2.Show();
        }
    }
}
