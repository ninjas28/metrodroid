/*
 * NextfareSubscription.java
 *
 * Copyright 2016 Michael Farrell <micolous+git@gmail.com>
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
package com.codebutler.farebot.transit.nextfare;

import android.os.Parcel;

import com.codebutler.farebot.transit.Subscription;
import com.codebutler.farebot.transit.nextfare.record.NextfareTravelPassRecord;

import java.util.Date;

/**
 * Represents a Nextfare travel pass.
 */

public class NextfareSubscription extends Subscription {

    private Date mValidTo;

    public NextfareSubscription(NextfareTravelPassRecord record) {
        mValidTo = record.getTimestamp().getTime();

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Date getValidFrom() {
        return null;
    }

    @Override
    public Date getValidTo() {
        return mValidTo;
    }

    @Override
    public String getAgencyName() {
        return "Nextfare";
    }

    @Override
    public String getShortAgencyName() {
        return "Nextfare";
    }

    @Override
    public int getMachineId() {
        return 0;
    }

    @Override
    public String getSubscriptionName() {
        return null;
    }

    @Override
    public String getActivation() {
        return null;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
