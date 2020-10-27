package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.repository.CompanyProfileRepository;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CompanyProfileService extends BaseServiceImpl<CompanyProfile, Long> {

    private final UserRepository userRepository;

    @Autowired
    public CompanyProfileService(CompanyProfileRepository repository,
                                 UserRepository userRepository,
                                 ModelMapper modelMapper) {
        super(repository, modelMapper, CompanyProfile.class);
        this.userRepository = userRepository;
    }

    @Transactional
    public CompanyProfile saveWithUser(CompanyProfile profile, User user) {
        profile = this.save(profile);
        user.setProfile(profile);
        userRepository.save(user);
        return profile;
    }

}
