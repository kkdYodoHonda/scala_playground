trait CharSequense {
	def charAt(index: Int): Char
	def length: Int
	def subSequence(start: Int, end: Int): CharSequense
	def toString(): String
}
