package models;

public class CarModel {
    private int modelId;
    private String brand;
    private String model;

    public CarModel() {
    }

    public CarModel(int modelId, String brand, String model) {
        this.modelId = modelId;
        this.brand = brand;
        this.model = model;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "modelId=" + modelId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}