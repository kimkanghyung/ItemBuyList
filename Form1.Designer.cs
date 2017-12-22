namespace autotest2
{
    partial class Form1
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.button1 = new System.Windows.Forms.Button();
            this.Swinglogin = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.findimage = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.yposion = new System.Windows.Forms.TextBox();
            this.xposition = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.wholecapture = new System.Windows.Forms.Button();
            this.difftxt = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.curpositiony = new System.Windows.Forms.TextBox();
            this.curpositionx = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.sendText = new System.Windows.Forms.TextBox();
            this.radioButton3 = new System.Windows.Forms.RadioButton();
            this.radioButton2 = new System.Windows.Forms.RadioButton();
            this.radioButton1 = new System.Windows.Forms.RadioButton();
            this.procheck = new System.Windows.Forms.Button();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.label6 = new System.Windows.Forms.Label();
            this.imagepathnm = new System.Windows.Forms.TextBox();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.DelBt = new System.Windows.Forms.Button();
            this.Addimage = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.firstcurrenty = new System.Windows.Forms.TextBox();
            this.firstcurrentx = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.SwingAT = new System.Windows.Forms.Button();
            this.WebClient = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.groupBox4.SuspendLayout();
            this.groupBox5.SuspendLayout();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(33, 38);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(122, 23);
            this.button1.TabIndex = 0;
            this.button1.Text = "NAVER로그인";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // Swinglogin
            // 
            this.Swinglogin.Location = new System.Drawing.Point(174, 38);
            this.Swinglogin.Name = "Swinglogin";
            this.Swinglogin.Size = new System.Drawing.Size(115, 23);
            this.Swinglogin.TabIndex = 1;
            this.Swinglogin.Text = "Swing-S 로그인";
            this.Swinglogin.UseVisualStyleBackColor = true;
            this.Swinglogin.Click += new System.EventHandler(this.Swinglogin_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(351, 38);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 3;
            this.button3.Text = "캡쳐";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.pictureBox1.Location = new System.Drawing.Point(33, 67);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(393, 355);
            this.pictureBox1.TabIndex = 4;
            this.pictureBox1.TabStop = false;
            // 
            // findimage
            // 
            this.findimage.Location = new System.Drawing.Point(457, 74);
            this.findimage.Name = "findimage";
            this.findimage.Size = new System.Drawing.Size(75, 23);
            this.findimage.TabIndex = 5;
            this.findimage.Text = "이미지찾기";
            this.findimage.UseVisualStyleBackColor = true;
            this.findimage.Click += new System.EventHandler(this.findimage_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.yposion);
            this.groupBox1.Controls.Add(this.xposition);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(457, 104);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(200, 100);
            this.groupBox1.TabIndex = 6;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "위치";
            // 
            // yposion
            // 
            this.yposion.Location = new System.Drawing.Point(81, 50);
            this.yposion.Name = "yposion";
            this.yposion.ReadOnly = true;
            this.yposion.Size = new System.Drawing.Size(100, 21);
            this.yposion.TabIndex = 3;
            // 
            // xposition
            // 
            this.xposition.Location = new System.Drawing.Point(81, 21);
            this.xposition.Name = "xposition";
            this.xposition.ReadOnly = true;
            this.xposition.Size = new System.Drawing.Size(100, 21);
            this.xposition.TabIndex = 2;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(22, 59);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(37, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "Y좌표";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(20, 30);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(37, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "X좌표";
            // 
            // wholecapture
            // 
            this.wholecapture.Location = new System.Drawing.Point(457, 36);
            this.wholecapture.Name = "wholecapture";
            this.wholecapture.Size = new System.Drawing.Size(75, 23);
            this.wholecapture.TabIndex = 7;
            this.wholecapture.Text = "전체캡쳐";
            this.wholecapture.UseVisualStyleBackColor = true;
            this.wholecapture.Click += new System.EventHandler(this.wholecapture_Click);
            // 
            // difftxt
            // 
            this.difftxt.Location = new System.Drawing.Point(609, 74);
            this.difftxt.Name = "difftxt";
            this.difftxt.Size = new System.Drawing.Size(100, 21);
            this.difftxt.TabIndex = 8;
            this.difftxt.Text = "140";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(550, 78);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 12);
            this.label3.TabIndex = 9;
            this.label3.Text = "오차범위";
            this.label3.Click += new System.EventHandler(this.label3_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.curpositiony);
            this.groupBox2.Controls.Add(this.curpositionx);
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Location = new System.Drawing.Point(457, 247);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(200, 100);
            this.groupBox2.TabIndex = 10;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "현재좌표";
            // 
            // curpositiony
            // 
            this.curpositiony.Location = new System.Drawing.Point(81, 54);
            this.curpositiony.Name = "curpositiony";
            this.curpositiony.ReadOnly = true;
            this.curpositiony.Size = new System.Drawing.Size(100, 21);
            this.curpositiony.TabIndex = 7;
            // 
            // curpositionx
            // 
            this.curpositionx.Location = new System.Drawing.Point(81, 25);
            this.curpositionx.Name = "curpositionx";
            this.curpositionx.ReadOnly = true;
            this.curpositionx.Size = new System.Drawing.Size(100, 21);
            this.curpositionx.TabIndex = 6;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(22, 63);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(37, 12);
            this.label4.TabIndex = 5;
            this.label4.Text = "Y좌표";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(20, 34);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(37, 12);
            this.label5.TabIndex = 4;
            this.label5.Text = "X좌표";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.sendText);
            this.groupBox3.Controls.Add(this.radioButton3);
            this.groupBox3.Controls.Add(this.radioButton2);
            this.groupBox3.Controls.Add(this.radioButton1);
            this.groupBox3.Location = new System.Drawing.Point(679, 104);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(200, 121);
            this.groupBox3.TabIndex = 11;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "event gubun";
            // 
            // sendText
            // 
            this.sendText.Location = new System.Drawing.Point(16, 88);
            this.sendText.Name = "sendText";
            this.sendText.Size = new System.Drawing.Size(178, 21);
            this.sendText.TabIndex = 3;
            // 
            // radioButton3
            // 
            this.radioButton3.AutoSize = true;
            this.radioButton3.Location = new System.Drawing.Point(16, 66);
            this.radioButton3.Name = "radioButton3";
            this.radioButton3.Size = new System.Drawing.Size(47, 16);
            this.radioButton3.TabIndex = 2;
            this.radioButton3.TabStop = true;
            this.radioButton3.Text = "입력";
            this.radioButton3.UseVisualStyleBackColor = true;
            this.radioButton3.Click += new System.EventHandler(this.radioButton3_Click);
            // 
            // radioButton2
            // 
            this.radioButton2.AutoSize = true;
            this.radioButton2.Location = new System.Drawing.Point(16, 43);
            this.radioButton2.Name = "radioButton2";
            this.radioButton2.Size = new System.Drawing.Size(131, 16);
            this.radioButton2.TabIndex = 1;
            this.radioButton2.TabStop = true;
            this.radioButton2.Text = "왼쪽마우스더블클릭";
            this.radioButton2.UseVisualStyleBackColor = true;
            this.radioButton2.Click += new System.EventHandler(this.radioButton2_Click);
            // 
            // radioButton1
            // 
            this.radioButton1.AutoSize = true;
            this.radioButton1.Location = new System.Drawing.Point(16, 20);
            this.radioButton1.Name = "radioButton1";
            this.radioButton1.Size = new System.Drawing.Size(107, 16);
            this.radioButton1.TabIndex = 0;
            this.radioButton1.TabStop = true;
            this.radioButton1.Text = "왼쪽마우스클릭";
            this.radioButton1.UseVisualStyleBackColor = true;
            this.radioButton1.Click += new System.EventHandler(this.radioButton1_Click);
            // 
            // procheck
            // 
            this.procheck.Location = new System.Drawing.Point(775, 38);
            this.procheck.Name = "procheck";
            this.procheck.Size = new System.Drawing.Size(128, 23);
            this.procheck.TabIndex = 12;
            this.procheck.Text = "Process check";
            this.procheck.UseVisualStyleBackColor = true;
            this.procheck.Click += new System.EventHandler(this.procheck_Click);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(61, 4);
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(901, 134);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.Size = new System.Drawing.Size(791, 257);
            this.dataGridView1.TabIndex = 13;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(6, 24);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(69, 12);
            this.label6.TabIndex = 14;
            this.label6.Text = "이미지 이름";
            // 
            // imagepathnm
            // 
            this.imagepathnm.Location = new System.Drawing.Point(94, 20);
            this.imagepathnm.Name = "imagepathnm";
            this.imagepathnm.Size = new System.Drawing.Size(135, 21);
            this.imagepathnm.TabIndex = 15;
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.DelBt);
            this.groupBox4.Controls.Add(this.Addimage);
            this.groupBox4.Controls.Add(this.label6);
            this.groupBox4.Controls.Add(this.imagepathnm);
            this.groupBox4.Location = new System.Drawing.Point(931, 67);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(427, 54);
            this.groupBox4.TabIndex = 16;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "경로";
            // 
            // DelBt
            // 
            this.DelBt.Location = new System.Drawing.Point(327, 20);
            this.DelBt.Name = "DelBt";
            this.DelBt.Size = new System.Drawing.Size(75, 23);
            this.DelBt.TabIndex = 17;
            this.DelBt.Text = "Del";
            this.DelBt.UseVisualStyleBackColor = true;
            this.DelBt.Click += new System.EventHandler(this.DelBt_Click);
            // 
            // Addimage
            // 
            this.Addimage.Location = new System.Drawing.Point(246, 20);
            this.Addimage.Name = "Addimage";
            this.Addimage.Size = new System.Drawing.Size(75, 23);
            this.Addimage.TabIndex = 16;
            this.Addimage.Text = "Add";
            this.Addimage.UseVisualStyleBackColor = true;
            this.Addimage.Click += new System.EventHandler(this.Addimage_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(1002, 38);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 17;
            this.button2.Text = "Test";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click_1);
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(1045, 38);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(8, 8);
            this.button4.TabIndex = 18;
            this.button4.Text = "Test";
            this.button4.UseVisualStyleBackColor = true;
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.firstcurrenty);
            this.groupBox5.Controls.Add(this.firstcurrentx);
            this.groupBox5.Controls.Add(this.label7);
            this.groupBox5.Controls.Add(this.label8);
            this.groupBox5.Location = new System.Drawing.Point(1364, 28);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(200, 100);
            this.groupBox5.TabIndex = 19;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "클릭했던 이미지 위치";
            // 
            // firstcurrenty
            // 
            this.firstcurrenty.Location = new System.Drawing.Point(81, 50);
            this.firstcurrenty.Name = "firstcurrenty";
            this.firstcurrenty.ReadOnly = true;
            this.firstcurrenty.Size = new System.Drawing.Size(100, 21);
            this.firstcurrenty.TabIndex = 3;
            // 
            // firstcurrentx
            // 
            this.firstcurrentx.Location = new System.Drawing.Point(81, 21);
            this.firstcurrentx.Name = "firstcurrentx";
            this.firstcurrentx.ReadOnly = true;
            this.firstcurrentx.Size = new System.Drawing.Size(100, 21);
            this.firstcurrentx.TabIndex = 2;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(22, 59);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(37, 12);
            this.label7.TabIndex = 1;
            this.label7.Text = "Y좌표";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(20, 30);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(37, 12);
            this.label8.TabIndex = 0;
            this.label8.Text = "X좌표";
            // 
            // SwingAT
            // 
            this.SwingAT.Location = new System.Drawing.Point(33, 13);
            this.SwingAT.Name = "SwingAT";
            this.SwingAT.Size = new System.Drawing.Size(122, 23);
            this.SwingAT.TabIndex = 20;
            this.SwingAT.Text = "SwingTest";
            this.SwingAT.UseVisualStyleBackColor = true;
            this.SwingAT.Click += new System.EventHandler(this.SwingAT_Click);
            // 
            // WebClient
            // 
            this.WebClient.Location = new System.Drawing.Point(174, 13);
            this.WebClient.Name = "WebClient";
            this.WebClient.Size = new System.Drawing.Size(115, 23);
            this.WebClient.TabIndex = 21;
            this.WebClient.Text = "WebClient";
            this.WebClient.UseVisualStyleBackColor = true;
            this.WebClient.Click += new System.EventHandler(this.WebClient_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1698, 456);
            this.Controls.Add(this.WebClient);
            this.Controls.Add(this.SwingAT);
            this.Controls.Add(this.groupBox5);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.procheck);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.difftxt);
            this.Controls.Add(this.wholecapture);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.findimage);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.Swinglogin);
            this.Controls.Add(this.button1);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.groupBox4.ResumeLayout(false);
            this.groupBox4.PerformLayout();
            this.groupBox5.ResumeLayout(false);
            this.groupBox5.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button Swinglogin;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button findimage;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.TextBox yposion;
        private System.Windows.Forms.TextBox xposition;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button wholecapture;
        private System.Windows.Forms.TextBox difftxt;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TextBox curpositiony;
        private System.Windows.Forms.TextBox curpositionx;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.RadioButton radioButton3;
        private System.Windows.Forms.RadioButton radioButton2;
        private System.Windows.Forms.RadioButton radioButton1;
        private System.Windows.Forms.Button procheck;
        private System.Windows.Forms.TextBox sendText;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox imagepathnm;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.Button Addimage;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.TextBox firstcurrenty;
        private System.Windows.Forms.TextBox firstcurrentx;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button DelBt;
        private System.Windows.Forms.Button SwingAT;
        private System.Windows.Forms.Button WebClient;
    }
}

