package controllers;

import dao.CarModelDAO;
import models.CarModel;

import java.util.List;

public class CarModelController {
    private final CarModelDAO carModelDAO;

    public CarModelController() {
        this.carModelDAO = new CarModelDAO();
    }

    public boolean addCarModel(String brand, String model) {
        CarModel carModel = new CarModel();
        carModel.setBrand(brand);
        carModel.setModel(model);
        return carModelDAO.addCarModel(carModel);
    }

    public boolean updateCarModel(int modelId, String brand, String model) {
        CarModel carModel = new CarModel();
        carModel.setModelId(modelId);
        carModel.setBrand(brand);
        carModel.setModel(model);
        return carModelDAO.updateCarModel(carModel);
    }

    public boolean deleteCarModel(int modelId) {
        return carModelDAO.deleteCarModel(modelId);
    }

    public CarModel getCarModelById(int modelId) {
        return carModelDAO.getCarModelById(modelId);
    }

    public List<CarModel> getAllCarModels() {
        return carModelDAO.getAllCarModels();
    }
}