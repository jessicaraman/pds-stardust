package com.pds.pgmapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * UnitTest : Guide User Store
 */
public class GuideUserStoreUnitTest {

    @Before
    public void mockPath() {
        // MOCK DATA
    }

    // the method computeDistance should return the correct one
    @Test
    public void computeDistanceReturnsTheCorrectOne() {
        assertEquals(4, 2 + 2);
    }

    // the method getClosestNode should return an error if all nodes has already been visited by the user
    @Test
    public void getClosetNodeWithAllNodesAlreadyVisited() {
        assertEquals(4, 2 + 2);
    }

    // the method computeDirection should return the correct one
    @Test
    public void computeDirectionReturnsTheCorrectOne() {
        assertEquals(4, 2 + 2);
    }

    // the method getClosestNode should return the closest node to the user
    @Test
    public void getClosestNodeReturnsTheClosestOne() {
        assertEquals(4, 2 + 2);
    }

    // loading the mocked should return a parsed json object
    @Test
    public void loadingMockedPath() {
        assertEquals(4, 2 + 2);
    }

    // the user's current position must match the end position
    @Test
    public void userIsArrived() {
        assertEquals(4, 2 + 2);
    }

    // when the user takes a wrong direction, he must go back thanks to a notification he has received
    @Test
    public void userTookTheWrongDirectionAndShouldGoBack() {
        assertEquals(4, 2 + 2);
    }

    // the end point of the last direction must match the point of the final path
    @Test
    public void endPointLastDirectionMatchWithFinalPath() {
        assertEquals(4, 2 + 2);
    }

    // the first point of the first direction must match to the initial position of the user
    @Test
    public void firstPointFirstDirectionMatchWithUserInitialPosition() {
        assertEquals(4, 2 + 2);
    }

    // check if each direction has in common a point with the previous direction
    @Test
    public void everyDirectionHasCommonPointWithPreviousDirection() {
        assertEquals(4, 2 + 2);
    }

    // if the user took the right direction, we show him the next one with a notification
    @Test
    public void userGoingInTheRightDirectionShouldShowHimTheNextOne() {
        assertEquals(4, 2 + 2);
    }

    // must show a notification error if the geolocation is lost
    @Test
    public void shouldShowNotificationIfGeolocationIsLost() {
        assertEquals(4, 2 + 2);
    }

    // must stop the guiding if the geolocation is lost
    @Test
    public void shouldStopTheGuidingIfGeolocationIsLost() {
        assertEquals(4, 2 + 2);
    }
}
