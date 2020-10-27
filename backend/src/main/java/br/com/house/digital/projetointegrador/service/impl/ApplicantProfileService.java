package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.repository.ApplicantProfileRepository;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ApplicantProfileService extends BaseServiceImpl<ApplicantProfile, Long> {

    private final UserRepository userRepository;

    @Autowired
    public ApplicantProfileService(ApplicantProfileRepository applicantProfileRepository,
                                   UserRepository userRepository,
                                   ModelMapper modelMapper) {
        super(applicantProfileRepository, modelMapper, ApplicantProfile.class);
        this.userRepository = userRepository;
    }

    @Transactional
    public ApplicantProfile saveWithUser(ApplicantProfile profile, User user) {
        profile = this.save(profile);
        user.setProfile(profile);
        userRepository.save(user);
        return profile;
    }

    public ApplicantProfileDTO findByIdWithName(Long id) {
        final ApplicantProfile profile = super.findById(id);
        final String name = userRepository.findByProfile(profile)
            .orElseThrow(() -> new ObjectNotFoundException("User not found."))
            .getName();
        final ApplicantProfileDTO dto = this.convertFromEntity(profile, ApplicantProfileDTO.class);
        dto.setName(name);
        return dto;
    }
}