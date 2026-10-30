package lostand_found_management_system;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "handover_note")
public class HandoverNote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String verifiedWith;
    private String remarks;
    private int itemsHanded;
    private LocalDate handoverDate;

    @ManyToOne
    @JoinColumn(name = "lost_report_id")
    private LostReport lostReport;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVerifiedWith() {
        return verifiedWith;
    }

    public void setVerifiedWith(String verifiedWith) {
        this.verifiedWith = verifiedWith;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getItemsHanded() {
        return itemsHanded;
    }

    public void setItemsHanded(int itemsHanded) {
        this.itemsHanded = itemsHanded;
    }

    public LocalDate getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(LocalDate handoverDate) {
        this.handoverDate = handoverDate;
    }

    public LostReport getLostReport() {
        return lostReport;
    }

    public void setLostReport(LostReport lostReport) {
        this.lostReport = lostReport;
    }
}
