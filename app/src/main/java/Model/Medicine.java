package Model;

public class Medicine {
        private String name;
        private String medTime, medDate;

    public Medicine(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedTime() {
        return medTime;
    }

    public void setMedTime(String medTime) {
        this.medTime = medTime;
    }

    public String getMedDate() {
        return medDate;
    }

    public void setMedDate(String medDate) {
        this.medDate = medDate;
    }

}
