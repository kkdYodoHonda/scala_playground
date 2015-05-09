import java.io.PrintWriter
import scala.io.Source
import java.util.Date
import java.text.SimpleDateFormat

object LogLines {
	def processFile(filename: String, start: String, end: String) {
		val date = "%tm%<td" format new Date
		val st = new SimpleDateFormat("HHmmss").parse(start + "00").getTime()
		val ed = new SimpleDateFormat("HHmmss").parse(end + "00").getTime()

		val source = Source.fromFile(filename)
		val logname = "maillog_" + date + "_" + start + ".log"
		val maillog = new PrintWriter(logname)
		val regex = """.*\s(\d\d:\d\d:\d\d)\s.*""".r
		def getLog(reg: String, line: String) {
			var time = new SimpleDateFormat("HH:mm:ss").parse(reg).getTime()
			if(st <= time && time < ed)
				maillog.println(line)
		}
		for (line <- source.getLines()) {
			line match {
				case regex(reg) => getLog(reg, line)
				case _ =>
			}
		}
		maillog.close

		val source2 = Source.fromFile(logname)
		val reclogname = "receiver_" + date + "_" + start + ".log"
		val receiver = new PrintWriter(reclogname)
		for (line <- source2.getLines())
			if (line.contains("to=<"))
				receiver.println(line)
		receiver.close

		// gmail blocked list
		val source3 = Source.fromFile(logname)
		val gmail = "gmail_blocked_" + date + "_" + start + ".log"
		val gmaillist = new PrintWriter(gmail)
		for (line <- source3.getLines())
			if (line.contains("unsolicited mail originating from your IP address"))
				gmaillist.println(line)
		gmaillist.close

		// yahoo blocked list
		val source4 = Source.fromFile(logname)
		val yahoo = "yahoo_blocked_" + date + "_" + start + ".log"
		val yahoolist = new PrintWriter(yahoo)
		for (line <- source4.getLines())
			if (line.contains("Please refer to http://www.yahoo-help.jp"))
				yahoolist.println(line)
		yahoolist.close

		// outlook blocked list
		val source5 = Source.fromFile(logname)
		val outlook = "outlook_blocked_" + date + "_" + start + ".log"
		val outlooklist = new PrintWriter(outlook)
		for (line <- source5.getLines())
			if (line.contains(" Please contact your Internet service provider since part of their network is on our block list."))
				outlooklist.println(line)
		outlooklist.close
	}
}

object MakeLog {
	def main(args: Array[String]) {
		for (arg <- args.drop(2))
			LogLines.processFile(arg, args(0), args(1))
	}
}
