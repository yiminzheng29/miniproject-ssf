package vttp.ssf.SpotifyApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.SpotifyApp.repositories.ArtistRepository;

@Service
public class ArtistService {
    
    @Autowired
    private ArtistRepository artistRepo;

    
}
