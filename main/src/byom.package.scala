/**
 * we put some helper methods and implicit instances in the package object
 * for easy access and syntactic sugar
 */

 package byom {

    // warning: this is unsafe if the string is not a valid hex string
    def convertHexToBytes(hexString: String): Array[Byte] = {
        val theBytes = BigInt.apply(hexString,16).mod(BigInt(2).pow(hexString.length*4)).toByteArray
        theBytes
    } 
    def convertBytesToHex(bytes: Seq[Byte]): String = {
        val sb = new StringBuilder
        for (b <- bytes) {
            sb.append(String.format("%02x", Byte.box(b)))
        }
        sb.toString
    }

    // some syntactic sugar (probably should be in different file)
    // also, should be able to generalize to any traversable of bytes
    // skipping generalization for now
    implicit class byteArrayOps(bytes: Array[Byte]) {
        // convert to hexstring
        def hex: String = convertBytesToHex(bytes)
    }

    implicit class stringOps(s: String) {
        // convert string to bytes (UTF-8), then to hex
        def hex = s.getBytes("UTF-8").hex

        // decode a hexencoded String (may throw error if string not properly encoded)
        def unsafeHex2string: String = new String(convertHexToBytes(s))
        def unsafeHex2bytes: Array[Byte] = convertHexToBytes(s)
    }

    // for a tuple of BigInts, maps the first coordinate to x and second to y
    implicit class tuplePointOps(pair: (BigInt, BigInt)) {
        def x: BigInt = pair._1
        def y: BigInt = pair._2
    }
 }