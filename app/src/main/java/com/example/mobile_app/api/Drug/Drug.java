
package com.example.mobile_app.api.Drug;
import java.util.List;

public class Drug {
    private int id;
    private String name;
    private String info;
    private List<Prescription> prescriptions;

    public Drug(int id, String name, String info, List<Prescription> prescriptions) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.prescriptions = prescriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }


    class Prescription {
        private int quantity;
        private String expirationDate;
        private String entryDate;
        public Prescription(int quantity, String expirationDate, String entryDate) {
            this.quantity = quantity;
            this.expirationDate = expirationDate;
            this.entryDate = entryDate;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        @Override
        public String toString() {
            return "Quantity: " + quantity + "\nExpiration Date: " + expirationDate + "\nEntry Date: " + entryDate;
        }

    }
}
