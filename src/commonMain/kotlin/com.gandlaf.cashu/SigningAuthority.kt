package com.gandlaf.cashu
import fr.acinq.secp256k1.Secp256k1

class SigningAuthority {
    private var privateKey: ByteArray
    var publicKey: ByteArray
    constructor() : this(randomBytes(32))

    constructor(privateKey: ByteArray) {
        this.privateKey = privateKey
        println("private key: "+byteArrayToHex(privateKey))
        if (Secp256k1.secKeyVerify(this.privateKey)==false){
            error("not a valid private key")
        }
        this.publicKey = Secp256k1.pubkeyCreate(privateKey)
        println("public key:" + byteArrayToHex(this.publicKey))
     }

    fun createBlindSignature(B_: ByteArray) : ByteArray {
        val C_ : ByteArray = Secp256k1.pubKeyCompress(Secp256k1.pubKeyTweakMul(B_, this.privateKey))
        return C_
    }
    fun calculateCVerify(secret: ByteArray) : ByteArray {
        val Y : ByteArray = hashToCurve(secret)
        val aY : ByteArray =  Secp256k1.pubKeyCompress(Secp256k1.pubKeyTweakMul(Y, this.privateKey))
        return aY
    }

    fun verify(aY: ByteArray, C: ByteArray): Boolean {
        return byteArrayToHex(aY)== byteArrayToHex(C)
    }
}


