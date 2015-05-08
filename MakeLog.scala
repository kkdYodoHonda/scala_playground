import java.io.PrintWriter
import scala.io.Source
import java.util.Date

object LogLines {
	def processFile(filename: String, start: String, end: String) {
		val date = "%tm%<td" format new Date
		val st = start.take(2) + ":" + start.drop(2) + ":00"
		val ed = end.take(2) + ":" + end.drop(2) + ":00"

		val source = Source.fromFile(filename)
		val logname = "maillog_" + date + "_" + start + ".log"
		val maillog = new PrintWriter(logname)
		for (line <- source.getLines())
			if (line matches """\s[0-9]{2}:[0-9]{2}:[0-9]\s""")
				maillog.println(line)
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
