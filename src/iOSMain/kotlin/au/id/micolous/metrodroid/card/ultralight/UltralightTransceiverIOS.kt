/*
 * UltralightTransceiverIOS.kt
 *
 * Copyright 2019 Google
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package au.id.micolous.metrodroid.card.ultralight

import au.id.micolous.metrodroid.card.CardLostException
import au.id.micolous.metrodroid.card.CardTransceiveException
import au.id.micolous.metrodroid.card.CardTransceiver
import au.id.micolous.metrodroid.multi.Log
import au.id.micolous.metrodroid.util.ImmutableByteArray
import au.id.micolous.metrodroid.util.toNSData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.runBlocking
import platform.Foundation.NSData
import platform.Foundation.NSError
import kotlin.native.concurrent.freeze

class UltralightTransceiverIOS(val tag: SwiftWrapper): CardTransceiver {
    override val uid: ImmutableByteArray? = tag.getIdentifier().toImmutable()

    init {
        freeze()
    }

    data class Capsule(
            val reply: NSData,
            val err: NSError?) {
        init {
            freeze()
        }
    }

    override fun transceive(data: ImmutableByteArray): ImmutableByteArray {
        Log.d(TAG, ">>> $data")
        val (repl, err) = runBlocking {
            val chan = Channel<Capsule>()
            chan.freeze()
            tag.transmit(data.toNSData(), chan)
            chan.receive()
        }
        if (err != null) {
            Log.d(TAG, "<!< $err")
            if (err.code == 100L)
              throw CardLostException(err.toString())
            throw CardTransceiveException(err.toString())
        } else {
            val rep = repl.toImmutable()
            Log.d(TAG, "<<< $rep")
            return rep
        }
    }

    // Kotlin apparently lack imports for NFCTagMiFare
    interface SwiftWrapper {
        fun getIdentifier(): NSData
        fun transmit(input: NSData, channel: SendChannel<Capsule>)
    }

    companion object {
        private const val TAG = "UltralightTransceiver"

        @Suppress("unused")  // Called from Swift
        fun callback(channel: SendChannel<Capsule>, reply: NSData,
            error: NSError?) {
            val capsule = Capsule(reply, error)
            runBlocking {
                channel.send(capsule)
                channel.close()
            }
        }
    }
}
