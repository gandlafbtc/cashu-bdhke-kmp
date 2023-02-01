package com.gandlaf.cashu

import fr.acinq.secp256k1.Secp256k1
import kotlin.test.Test
import kotlin.test.assertEquals

class SchemeTest {
    @Test
    fun testBDHKE(){
        val signingAuthority = SigningAuthority()

        val secret  = randomBytes(32)
        println("secret message: "+ byteArrayToHex( secret))
        val blindedMessage = BlindedMessage(secret)

        val B_ = blindedMessage.B_

        println("blinded Message: "+ byteArrayToHex( B_))

        val C_ = signingAuthority.createBlindSignature(B_)
        println("blinded signature: "+ byteArrayToHex( C_))

        val C = blindedMessage.unblindSignature(C_, signingAuthority.publicKey)
        println("unblinded signature: "+ byteArrayToHex( C))

        val aY = signingAuthority.calculateCVerify(secret)
        println("calculate signature: "+ byteArrayToHex( aY))

        val isVlaid = signingAuthority.verify(aY,C)

        assert(isVlaid)
    }

    @Test
    fun testPredefined(){
        val secretString = "secret"
        val secret  = secretString.toByteArray()
        val blindedMessage = BlindedMessage(secret)
        val Y = blindedMessage.Y
        assertEquals("041690316bcf298df9b9f082e155979c90a5cfd663c6aa9117ebf16bf49ee16b6e08e3119eeb5c4b102f031120a6a6e64be3068591ac2726cb9bee51a96705b2e2",byteArrayToHex(Y), )
    }

}