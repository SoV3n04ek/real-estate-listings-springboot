    package com.frent.apartments.repository;

    import com.frent.apartments.model.Listing;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface ListingRepository extends JpaRepository<Listing, Long> {
        List<Listing> findByTitleContainingIgnoreCase(String title);

        Page<Listing> findByCityContainingIgnoreCase(String city, Pageable pageable);
    }
