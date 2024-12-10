package ru.khehelk.cityroutes.domain.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class RouteStopsId {

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "route_id", nullable = false)
    private Long stopId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ?
            hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ?
            hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        RouteStopsId that = (RouteStopsId) o;
        return getRouteId() != null && Objects.equals(getRouteId(), that.getRouteId())
            && getStopId() != null && Objects.equals(getStopId(), that.getStopId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(routeId, stopId);
    }

}