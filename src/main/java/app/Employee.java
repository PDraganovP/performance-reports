package app;

public class Employee {
    private String name;
    private int totalSales;
    private int salesPeriod;
    private double experienceMultiplier;
    private double score;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getSalesPeriod() {
        return salesPeriod;
    }

    public void setSalesPeriod(int salesPeriod) {
        this.salesPeriod = salesPeriod;
    }

    public double getExperienceMultiplier() {
        return experienceMultiplier;
    }

    public void setExperienceMultiplier(double experienceMultiplier) {
        this.experienceMultiplier = experienceMultiplier;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double calculateScore(boolean useExperienceMultiplier, int totalSales, int salesPeriod, double experienceMultiplier) {
        double score = 0d;
        if (useExperienceMultiplier) {
            score = totalSales / salesPeriod * experienceMultiplier;
            return score;
        }
        score = totalSales / salesPeriod;
        return score;
    }

}
