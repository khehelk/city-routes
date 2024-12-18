package ru.khehelk.cityroutes.reviewservice.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class ReviewId {

    @Column(name = "userdata_id")
    private Long userID;

    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
            ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
            ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        ReviewId reviewId = (ReviewId) o;
        return userID != null && Objects.equals(userID, reviewId.userID)
            && routeId != null && Objects.equals(routeId, reviewId.routeId)
            && date != null && Objects.equals(date, reviewId.date);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(userID, routeId, date);
    }

}
