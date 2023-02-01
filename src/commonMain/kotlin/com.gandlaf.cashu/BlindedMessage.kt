package com.gandlaf.cashu

import fr.acinq.secp256k1.Secp256k1
class BlindedMessage {
    lateinit var Y: ByteArray
    private lateinit var r: ByteArray
    private lateinit var rG: ByteArray
    lateinit var B_: ByteArray
    private lateinit var secret: ByteArray

    constructor() : this(randomBytes(65))

    constructor(secret: ByteArray){
        createBlindedMessage(secret)
    }

    private fun createBlindedMessage(secret: ByteArray): ByteArray {
        this.secret = secret
        this.Y = hashToCurve(this.secret)
        this.r = randomBytes(32)
        this.rG = Secp256k1.pubkeyCreate(r)
        this.B_ = Secp256k1.pubKeyCombine(arrayOf(this.Y, this.rG))
        this.B_ = Secp256k1.pubKeyCompress(this.B_)
        return this.B_
    }

   fun  unblindSignature(C_: ByteArray, mintPubK: ByteArray):ByteArray{
       val C : ByteArray =
           Secp256k1.pubKeyCompress(Secp256k1.pubKeyCombine(arrayOf(C_, Secp256k1.pubKeyNegate(Secp256k1.pubKeyTweakMul(mintPubK, this.r)))))
       return C
   }

}