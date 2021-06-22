import lt.vtmc.exam.*;

import java.util.*;

/**
 * Manages buses and passengers. The main responsibility for this class is to register available buses and register passengers for each of buses.
 */
public class TransportManagerImpl implements TransportManager {

    private Map<String, Bus> buses = new HashMap();
    private Map<String, Passenger> passengers = new HashMap();

    /**
     * @param s seats - number of seats in the bus
     * @param i id - bus identifier
     * @return registered bus instance
     */
    @Override
    public Bus createBus(String s, int i) {
        if (s.isEmpty() || i < 0) {
            throw new IllegalArgumentException();
        } else if (s == null) {
            throw new NullPointerException();
        } else {
            Bus bus = new Bus(s, i);
            if (bus.getSeats() > 0) {
                buses.put(s, bus);
                System.out.println("Created bus with id: " + s);
                return bus;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * @return list of buses
     */
    @Override
    public List<Bus> getCreatedBuses() {
        List<Bus> busList = new ArrayList<>(buses.values());
        return busList;
    }

    /**
     * @param s busId - bus identifier
     * @return registered bus
     */
    @Override
    public Bus getBusById(String s) {
        if (s != null) {
            return buses.get(s);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Passenger createPassenger(String s, String s1, int i) {
        if (s.isEmpty() || s1.isEmpty() || i < 0) {
            throw new IllegalArgumentException();
        } else {
            Passenger passenger = new Passenger(s, s1, i);
            passengers.put(s1, passenger);
            System.out.println("Created passenger with id: " + s1);
            return passenger;
        }
    }

    @Override
    public void registerPassenger(Bus bus, int i, Passenger passenger) throws SeatIsOccupiedException {
        if (bus.isSeatOccupied(i)) {
            throw new SeatIsOccupiedException();
        } else {
            passenger.setSeatNo(i);
            bus.registerPassenger(i, passenger);
        }
    }

    @Override
    public List<Passenger> getPassengers(String s) {
        Bus bus = buses.get(s);
        return bus.getPassengers();
    }

    @Override
    public Passenger getOldestPassenger(String s) {
        int oldestAge = 0;
        Passenger oldest = null;
        if (passengers.isEmpty()) {
            return null;
        } else {
            Bus bus = buses.get(s);
            List<Passenger> passengerList = bus.getPassengers();
            for (Passenger passenger : passengerList) {
                if (passenger.getAge() > oldestAge) {
                    oldestAge = passenger.getAge();
                    oldest = passenger;
                }
            }
            return oldest;
        }
    }

    @Override
    public double getAveragePassengerAge(String s) {
        int sum = 0;
        int count = 0;
        Bus bus = buses.get(s);
        List<Passenger> passengerList = bus.getPassengers();
        for (Passenger passenger : passengerList) {
            sum = sum + passenger.getAge();
            count++;
        }
        return (double) sum / count;
    }

    @Override
    public Collection<Passenger> getOrderedPassengers(String s) {
        Collection<Passenger> orderedPassengers = new ArrayList<>();
        Bus bus = buses.get(s);
        List<Passenger> passengerList = bus.getPassengers();
        Collections.sort(passengerList, new Comparator<Passenger>() {
            @Override
            public int compare(Passenger o1, Passenger o2) {
                int result = Integer.valueOf(o1.getSurname().compareTo(o2.getSurname()));
                if (result != 0) {
                    return result;
                } else {
                    return Integer.valueOf(o1.getName().compareTo(o2.getName()));
                }
            }
        });
        return passengerList;
    }

    @Override
    public List<Passenger> findPassengersBy(String s, PassengerPredicate passengerPredicate) {
        List<Passenger> approvedPassengers = new ArrayList<>();
        Bus bus = buses.get(s);
        List<Passenger> passengerList = bus.getPassengers();
        for (Passenger passenger : passengerList) {
            if (passengerPredicate.test(passenger)) {
                approvedPassengers.add(passenger);
            }
        }
        return approvedPassengers;
    }
}
