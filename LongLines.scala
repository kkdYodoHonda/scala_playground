import scala.io.Source

object FindLongLines {
	def prog(args: Array[String]) {
		val width = args(0).toInt
		for (arg <- args.drop(1))
			LongLines.processFile(arg, width)
	}
}
object LongLines {
	def processFile(filename: String, width: Int){
		def processLine(line: String) {
			if (line.length > width)
				println(filename + ": " + line)
		}
		val source = Source.fromFile(filename)
		for (line <- source.getLines()) {
			processLine(line)
		}
	}
}
