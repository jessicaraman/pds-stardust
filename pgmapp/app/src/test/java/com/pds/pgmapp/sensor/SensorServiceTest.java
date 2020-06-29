package com.pds.pgmapp.sensor;

import android.os.RemoteException;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SensorServiceTest {

    @Mock
    private BeaconManager beaconManager;

    @InjectMocks
    private SensorService sensorService;

    @Test
    public void givenBeaconManagerSucceeds_whenOnBeaconServiceConnect_thenSucceed() throws RemoteException {
        sensorService.onBeaconServiceConnect();

        verify(beaconManager, times(1)).removeAllRangeNotifiers();
        verify(beaconManager, times(1)).addRangeNotifier(any(RangeNotifier.class));
        verify(beaconManager, times(1)).startRangingBeaconsInRegion(new Region("regionUniqueId", null, null, null));
    }

    @Test
    public void givenBeaconThrowsManagerRemoteException_whenOnBeaconServiceConnect_thenCatch() throws RemoteException {
        doThrow(new RemoteException()).when(beaconManager).startRangingBeaconsInRegion(any(Region.class));

        assertDoesNotThrow(() -> sensorService.onBeaconServiceConnect());
    }

    @Test
    public void givenBeaconThrowsManagerException_whenOnBeaconServiceConnect_thenFail() throws RemoteException {
        doThrow(new RuntimeException()).when(beaconManager).startRangingBeaconsInRegion(any(Region.class));

        assertThrows(RuntimeException.class, () -> sensorService.onBeaconServiceConnect());
    }

}