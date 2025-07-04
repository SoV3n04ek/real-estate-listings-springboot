package com.frent.apartments.service;

import com.frent.apartments.model.Listing;
import com.frent.apartments.model.User;
import com.frent.apartments.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;

    public Listing createListing(Listing listing, User user) {
        listing.setUser(user);
        return listingRepository.save(listing);
    }

    public Page<Listing> getAllListings(String city, Pageable pageable) {
        if (city != null) {
            return listingRepository.findByCityContainingIgnoreCase(city, pageable);
        }
        return listingRepository.findAll(pageable);
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElseThrow(() -> new RuntimeException("Listing not found"));
    }
}
