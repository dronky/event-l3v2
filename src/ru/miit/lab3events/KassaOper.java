package ru.miit.lab3events;

import ru.miit.lab3events.annotation.Added;
import ru.miit.lab3events.annotation.Process;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import java.util.*;

/**
 * Created by Sinky on 19.03.2017.
 */
public class KassaOper {
    @Inject
    @Process
    private javax.enterprise.event.Event<List<String>> reqProcess;
    private static List<String> trips = Arrays.asList("075A|Korolev|14:00|30:12:2017|100", "023B|Kaluga|10:30|01:04:2017|300", "198B|Kaluga|10:30|01:04:2017|250",
            "942D|Kaluga|10:30|01:04:2017|70", "321I|Makhachkala|22:28|05:05:1995|120");

    public List<String> findTrip(@Observes @Added String req) {
        List<String> validTrips = new ArrayList<>();
        for (String trip : trips) {
            if (trip.split("\\|")[1].equals(req.split("\\|")[0]) &&
                    trip.split("\\|")[2].equals(req.split("\\|")[1]) &&
                    trip.split("\\|")[3].equals(req.split("\\|")[2])) {
                validTrips.add(trip);
            }
        }
        if (validTrips.size()>0){
            reqProcess.fire(validTrips);
        } else System.out.println("Trips not found");
        //System.out.println(validTrips);
        //trips.forEach ((trip) -> java.util.Arrays.toString(req.split("\\|")).toString() == java.util.Arrays.toString(trip.split("\\|")).toString() ? {validTrips.add(trip)} : return null);
        return validTrips;
    }

}
