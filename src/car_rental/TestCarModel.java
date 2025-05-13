//For testing the Car Model class and its interaction with the CarController

// package car_rental;

// import controllers.CarController;
// import models.Car;
// import java.util.List;

// public class TestCarModel {
//     public static void main(String[] args) {
//         try {
//             // Create a car controller
//             CarController carController = new CarController();
            
//             // Get all cars and display their details
//             System.out.println("Fetching all cars...");
//             List<Car> cars = carController.getAllCars();
            
//             if (cars.isEmpty()) {
//                 System.out.println("No cars found in the database.");
//             } else {
//                 System.out.println("Found " + cars.size() + " cars:");
//                 for (Car car : cars) {
//                     System.out.println("\nCar Details:");
//                     System.out.println("Car ID: " + car.getCarID());
                    
//                     // Test the Car Model object
//                     if (car.getCarModel() != null) {
//                         System.out.println("Model: " + car.getFullModelName());
//                     } else {
//                         System.out.println("Model: Unknown (CarModel object is null)");
//                     }
                    
//                     // Test the Category object
//                     if (car.getCategory() != null) {
//                         System.out.println("Category: " + car.getCategoryName());
//                     } else {
//                         System.out.println("Category: Unknown (Category object is null)");
//                     }
                    
//                     System.out.println("Availability: " + car.getAvailabilityStatus());
//                     System.out.println("Rental Price: $" + car.getRentalPrice() + "/day");
//                     System.out.println("Plate Number: " + car.getPlateNo());
//                 }
//             }
//         } catch (Exception e) {
//             System.err.println("Error testing car models: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }
// }
