def tupleDemo(expr: Any) =
	expr match {
		case (a, b, c) => println("matched " + a + b + c)
		case _ =>
	}

def isIntIntMap(x: Any) = x match {
case m: Map[Int, Int] => true
case _ => 1
}