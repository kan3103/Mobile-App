package com.example.mobile_app.api.Drug;

import java.util.ArrayList;
import java.util.List;
import java.util.Deque;
import java.util.LinkedList;



public class Drug {
    private String id;
    private String name;
    private String info;
    private Deque<Prescription> prescriptions;

    public Drug(String id, String name, String info, Deque<Prescription> prescriptions) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.prescriptions = prescriptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Deque<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public boolean removePrescriptionById(String id) {
        return prescriptions.removeIf(p -> p.getId().equals(id));
    }

    public void setPrescriptions(Deque<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public int countPrescriptions() {
        return prescriptions.size();
    }

//    void themthuoc(ArrayList<Drug> a, String id, String name, int quantity) {
//        boolean found = false;
//        for (Drug thuoc : a) {
//            if (thuoc.getId().equals(id)) {
//             for (Prescription prescription : thuoc.getPrescriptions()) {
//                thuoc.add
//                found = true;
//                return;
//             }
//            }
//        }
//        if (!found) {
//            Drug newDrug = new Drug(id, name, "", new ArrayList<>());
//            newDrug.setQuantity(quantity);
//            a.add(newDrug);
//        }
//    }

    void laythuoc(ArrayList<Drug> a, String id, int quantity) {
        for (Drug thuoc : a) {
            if (thuoc.getId().equals(id)) {
                for (Prescription prescription : thuoc.getPrescriptions()) {
                    if(quantity == 0) return;
                    int prescriptionQuantity = prescription.getQuantity();
                    if(prescriptionQuantity >= quantity){
                        prescription.setQuantity(prescriptionQuantity - quantity);
                        break;
                    }else{
                        quantity -= prescriptionQuantity;
                        thuoc.getPrescriptions().remove();
                    }
                }
                return ; // hoặc false tùy vào logic của bạn
            }
        }
        return ; // Trả về false nếu không tìm thấy thuốc
    }

    boolean removeDrug(ArrayList<Drug> a, String id, int quantity) {
        for (Drug thuoc : a) {
            if (thuoc.getId().equals(id)) {
                for (Prescription prescription : thuoc.getPrescriptions()) {
                    if(quantity == 0) return true;
                    int prescriptionQuantity = prescription.getQuantity();
                    if(prescriptionQuantity >= quantity){
                        prescription.setQuantity(prescriptionQuantity - quantity);
                        break;
                    }else{
                        quantity -= prescriptionQuantity;
                        thuoc.getPrescriptions().remove();
                    }
                }
                return true; // hoặc false tùy vào logic của bạn
            }
        }
        return false; // Trả về false nếu không tìm thấy thuốc
    }

      static class Prescription {
        private String id;
        private int quantity;
        private String entryDate;

        private String expirationDate;

        public Prescription(String id, int quantity, String entryDate, String expirationDate) {
            this.id = id;
            this.quantity = quantity;
            this.entryDate = entryDate;
            this.expirationDate = expirationDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }

        @Override
        public String toString() {
            return "ID: " + id + "\nQuantity: " + quantity + "\nEntry Date: " + entryDate + "\nExpiration Date: " + expirationDate;
        }
    }

}