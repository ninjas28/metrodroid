/*
 * ChinaCardTransitFactory.kt
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

package au.id.micolous.metrodroid.card.china

import au.id.micolous.metrodroid.multi.JvmDefault
import au.id.micolous.metrodroid.transit.CardTransitFactory
import au.id.micolous.metrodroid.util.ImmutableByteArray

interface ChinaCardTransitFactory : CardTransitFactory<ChinaCard> {

    val appNames: List<ImmutableByteArray>

    @JvmDefault
    override fun check(card: ChinaCard): Boolean {
        val appName = card.appName
        for (b in appNames) {
            if (b.contentEquals(appName)) {
                return true
            }
        }

        return false
    }
}
