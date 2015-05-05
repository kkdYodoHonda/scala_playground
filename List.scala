val fruit = List("apples", "oranges", "pears")
val nums = List(1, 2, 3, 4)
val diags3 = List(
	List(1, 0, 0),
	List(0, 1, 0),
	List(0, 0, 1)
)

val empty = List()

// Insertion Sort
def isort(xs: List[Int]): List[Int] =
	if(xs.isEmpty) Nil
	else insert(xs.head, isort(xs.tail))
def insert(x: Int, xs: List[Int]): List[Int] =
	if (xs.isEmpty || x <= xs.head) x :: xs
	else xs.head :: insert(x, xs.tail)

