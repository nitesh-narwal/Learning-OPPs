package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;

/** Here we are learning about Local Inner classes*/
public class Hotel {

    private String name;
    private int totalRooms;
    private int reservedRooms;

    public Hotel(String name, int totalRooms, int reservedRooms) {
        this.name = name;
        this.totalRooms = totalRooms;
        this.reservedRooms = reservedRooms;
    }

    public void reserveRoom(String guestNames, int noOfRooms){

        class ReservationValidator{

            boolean validate(){
                if(guestNames == null || guestNames.isEmpty()){
                    System.out.println("Guest name cannot be empty");
                    return false;
                }
                if(noOfRooms <= 0){
                    System.out.println("Number of rooms must be greater than zero");
                    return false;
            }     if(reservedRooms + noOfRooms > totalRooms){
                    System.out.println("Not enough rooms available");
                    return false;
                }
                return true;
            }
        }

        ReservationValidator reservationValidator = new ReservationValidator();
        if(reservationValidator.validate()){
            reservedRooms += noOfRooms;
            System.out.println("Reservation successful for " + guestNames + ". Rooms reserved: " + noOfRooms);
        }else {
            System.out.println("Reservation failed for " + guestNames);
        }
    }

     public void displayHotelInfo() {
        System.out.println("Hotel Name: " + name);
        System.out.println("Total Rooms: " + totalRooms);
        System.out.println("Reserved Rooms: " + reservedRooms);
        System.out.println("Available Rooms: " + (totalRooms - reservedRooms));
    }
}
