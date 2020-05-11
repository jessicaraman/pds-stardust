package com.pds.pgmapp;

import androidx.lifecycle.Lifecycle;

import com.pds.pgmapp.activity.BeaconActivity;
import com.pds.pgmapp.model.RecentlyVisitedBeacons;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void recentlyVisitedBeaconsMapReturnTrueWhenABeaconWasAlreadyVisited() {
        String idBeacon = "ABC";
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.put(idBeacon, new Date());
        assertEquals(true, recentlyVisitedBeacons.isThisBeaconPresent(idBeacon));
    }

    @Test
    public void recentlyVisitedBeaconsMapReturnTrueWhenABeaconWasRecentlyVisited() {
        String idBeacon = "ABC";
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.put(idBeacon, new Date());
        assertEquals(true, recentlyVisitedBeacons.thisBeaconHasBeenVisitedRecently(idBeacon, new Date()));
    }

    @Test
    public void recentlyVisitedBeaconsMapReturnFalseWhenABeaconWasVisitedLongAgo() {
        String idBeacon = "ABC";
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.put(idBeacon, new Date(System.currentTimeMillis() - (60 * 1000)));
        assertEquals(false, recentlyVisitedBeacons.thisBeaconHasBeenVisitedRecently(idBeacon, new Date()));
    }
}