package io.zoemeow.dutapi.objects;

public class SubjectFee {
    private String id;
    private String name;
    private Float credit;
    private Boolean isQuality;
    private Double price;
    private Boolean debt;
    private Boolean isReStudy;
    private String verifiedPaymentAt;

    public SubjectFee() {

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
    public Float getCredit() {
        return credit;
    }
    public void setCredit(Float credit) {
        this.credit = credit;
    }
    public Boolean getIsQuality() {
        return isQuality;
    }
    public void setIsQuality(Boolean isQuality) {
        this.isQuality = isQuality;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getDebt() {
        return debt;
    }
    public void setDebt(Boolean debt) {
        this.debt = debt;
    }
    public Boolean getIsReStudy() {
        return isReStudy;
    }
    public void setIsReStudy(Boolean isReStudy) {
        this.isReStudy = isReStudy;
    }
    public String getVerifiedPaymentAt() {
        return verifiedPaymentAt;
    }
    public void setVerifiedPaymentAt(String verifiedPaymentAt) {
        this.verifiedPaymentAt = verifiedPaymentAt;
    }

    
}
