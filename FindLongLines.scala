import scala.io.Source

object FindLongLines {
	def prog(args: Array[String]) {
		val width = args(0).toInt
		for (arg <- args.drop(1))
			LongLines.processFile(arg, width)
	}
}