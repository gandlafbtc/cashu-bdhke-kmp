package com.gandlaf.cashu

import fr.acinq.secp256k1.Secp256k1
import java.security.MessageDigest
import java.security.SecureRandom


fun hashToCurve(secret: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    var trySecret = secret
    var point = ByteArray(0)
    while (point.isEmpty()) {
        try {
            var hash: ByteArray = byteArrayOf(0x02) + digest.digest(trySecret)
            println(hash.size)
            point = Secp256k1.pubkeyParse(hash)
        } catch (exception: Exception) {
            trySecret = digest.digest(trySecret)
        }
    }
    return point
}

fun randomBytes(length: Int): ByteArray {
    val buffer = ByteArray(length)
    val random = SecureRandom()
    random.nextBytes(buffer)
    return buffer
}

fun byteArrayToHex(a: ByteArray): String {
    val sb = StringBuilder(a.size * 2)
    for (b in a) sb.append(String.format("%02x", b))
    return sb.toString()
}