using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace autotest2
{
    class CheckRunProcess
    {


        public CheckRunProcess()
        {
            Process[] processlist = Process.GetProcesses();

            foreach (Process theprocess in processlist)
            {
                //Console.WriteLine("Process: { 0} ID: { 1}", theprocess.ProcessName, theprocess.Id);
                Debug.WriteLine(theprocess.ProcessName);
            }

        }
    }
}
