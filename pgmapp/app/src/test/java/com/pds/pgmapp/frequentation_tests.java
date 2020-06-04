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
public class frequentation_tests {

    @Test
    public void recentlyVisitedBeaconsMapReturnTrueWhenABeaconWasAlreadyVisited() {
        String idBeacon = "ABC";
        Date date = new Date();
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, date);
        assertEquals(true, recentlyVisitedBeacons.isThisBeaconPresent(idBeacon));
    }

    @Test
    public void recentlyVisitedBeaconsMapReturnFalseWhenABeaconWasNeverVisited() {
        String idBeacon = "ABC";
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        assertEquals(false, recentlyVisitedBeacons.isThisBeaconPresent(idBeacon));
    }

    @Test
    public void recentlyVisitedBeaconsMapReturnTrueWhenABeaconWasRecentlyVisited() {
        String idBeacon = "ABC";
        Date date = new Date();
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, date);
        assertEquals(true, recentlyVisitedBeacons.thisBeaconHasBeenVisitedRecently(idBeacon, date));
    }

    @Test
    public void recentlyVisitedBeaconsMapReturnFalseWhenABeaconWasVisitedLongAgo() {
        String idBeacon = "ABC";
        Date date = new Date();
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, new Date(date.getTime() - 60 * 1000));
        assertEquals(false, recentlyVisitedBeacons.thisBeaconHasBeenVisitedRecently(idBeacon, new Date()));
    }

    @Test
    public void recentlyVisitedBeaconsMapDoNotKeepDatesOlderThanMostRecentOne() {
        String idBeacon = "ABC";
        Date dateFirstTimeSeen = new Date();
        Date dateSecondTimeSeen = new Date(dateFirstTimeSeen.getTime() + 60 * 1000);
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, dateFirstTimeSeen);
        recentlyVisitedBeacons.addBeacon(idBeacon, dateSecondTimeSeen);
        boolean test = dateFirstTimeSeen != recentlyVisitedBeacons.getDateOfBeacon(idBeacon) && dateSecondTimeSeen == recentlyVisitedBeacons.getDateOfBeacon(idBeacon);
        assertTrue(test);
    }

    @Test
    public void recentlyVisitedBeaconsMapCannotAddBeaconWhenSameOneWasVisitedTooRecently() {
        String idBeacon = "ABC";
        Date firstDate = new Date();
        Date secondDate = new Date(firstDate.getTime() + 1000);
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, firstDate);
        assertEquals(false, recentlyVisitedBeacons.addBeacon(idBeacon, secondDate));
    }

    @Test
    public void recentlyVisitedBeaconsMapCanAddBeaconWhenSameOneWasVisitedLongAgo() {
        String idBeacon = "ABC";
        Date firstDate = new Date();
        Date secondDate = new Date(firstDate.getTime() + 60 * 1000);
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, firstDate);
        assertEquals(true, recentlyVisitedBeacons.addBeacon(idBeacon, secondDate));
    }

    @Test
    public void recentlyVisitedBeaconsMapCanAddTwoDifferentBeaconsVisitedAroundTheSameTime() {
        String idBeacon = "ABC";
        String idBeacon2 = "XYZ";
        Date date = new Date();
        RecentlyVisitedBeacons recentlyVisitedBeacons = new RecentlyVisitedBeacons();
        recentlyVisitedBeacons.addBeacon(idBeacon, date);
        assertEquals(true, recentlyVisitedBeacons.addBeacon(idBeacon2, date));
    }
}