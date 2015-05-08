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
		val receiver = new PrintWriter("receiver_" + date + "_" + start + ".log")
		for (line <- source2.getLines())
			if (line.contains("to=<"))
				receiver.println(line)
		receiver.close
	}
}

object MakeLog {
	def main(args: Array[String]) {
		for (arg <- args.drop(2))
			LogLines.processFile(arg, args(0), args(1))
	}
}
