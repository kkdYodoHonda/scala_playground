var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =
	if(assertionsEnabled && !predicate())
		throw new AssertionError

var assertionsEnabled = true
def byNameAssert(predicate: => Boolean) =
	if(assertionsEnabled && !predicate)
		throw new AssertionError

