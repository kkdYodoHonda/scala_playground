import java.io.PrintWriter
import scala.io.Source
import java.util.Date
import java.text.SimpleDateFormat

object ServerLoglines {
	def processFile(li: List[String], server: String, ip: String) {
		val source = Source.fromFile(li(0))
		val name = ip + li(1) + "_" + li(2) + ".log"
		val serverlog = new PrintWriter(name)
		for (line <- source.getLines())
			if (line.contains(server))
				serverlog.println(line)
		serverlog.close
	}
}

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

		val li = List(logname, date, start)
		ServerLoglines.processFile(li, "www32243ue.sakura.ne.jp", "49-212-221-17_")
		ServerLoglines.processFile(li, "www21195ue.sakura.ne.jp", "49-212-186-209_")
		ServerLoglines.processFile(li, "www17007ue.sakura.ne.jp", "49-212-172-21_")
		ServerLoglines.processFile(li, "www7153ue.sakura.ne.jp", "49-212-138-167_")
		ServerLoglines.processFile(li, "www8373ue.sakura.ne.jp", "49-212-141-147_")
		ServerLoglines.processFile(li, "www34004ue.sakura.ne.jp", "49-212-230-18_")
		ServerLoglines.processFile(li, "www33136ue.sakura.ne.jp", "49-212-222-150_")
		ServerLoglines.processFile(li, "www9017ue.sakura.ne.jp", "49-212-142-31_")
		ServerLoglines.processFile(li, "www19163ue.sakura.ne.jp", "49-212-178-177_")
		ServerLoglines.processFile(li, "www31383ue.sakura.ne.jp", "49-212-217-157_")
	}
}

object MakeServerSMTPLog {
	def main(args: Array[String]) {
		for (arg <- args.drop(2))
			LogLines.processFile(arg, args(0), args(1))
	}
}
