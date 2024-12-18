package ru.khehelk.cityroutes.directoryservice.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FavouriteId {

    @Column(name = "userdata_id")
    private Long userID;

    @Column(name = "route_id")
    private Long routeId;

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
        FavouriteId that = (FavouriteId) o;
        return userID != null && Objects.equals(userID, that.userID)
            && routeId != null && Objects.equals(routeId, that.routeId);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(userID, routeId);
    }

}
