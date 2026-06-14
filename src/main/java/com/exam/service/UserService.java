package com.exam.service;

import com.exam.DTO.UserDetailDTO;
import com.exam.Exception.UserAlreadyExistException;
import com.exam.model.Role;
import com.exam.model.Users;
import com.exam.model.ParticipantDetail;
import com.exam.repository.ParticipantDetailRepository;
import com.exam.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService  {


    private UserRepository userRepository;
    private ParticipantDetailRepository participantDetailRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ParticipantDetailRepository participantDetailRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.participantDetailRepository = participantDetailRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Users createUser(Users user){

        Optional<Users> localUser  = userRepository.findByUsername(user.getUsername());
        Users createdUser = null;
        if(localUser.isPresent()){
            throw new UserAlreadyExistException("user name already exist");
        }else{
            createdUser = userRepository.save(user);
        }
        return createdUser;
    }




    @Transactional(rollbackFor = Exception.class)
    public boolean createUserDetail(UserDetailDTO userDetailsDTO) throws SQLException {

        Optional<Users> localUser  = userRepository.findByUsername(userDetailsDTO.getUsername());
        Optional<ParticipantDetail> localUserDetail = participantDetailRepository.findByEmail(userDetailsDTO.getEmail());
        Users user = new Users();
        ParticipantDetail participantDetails = new ParticipantDetail();
        if(userNameExist(userDetailsDTO.getUsername())){
            throw new UserAlreadyExistException("This Username is taken");

        }else if(emailExist(userDetailsDTO.getEmail())){
            throw new UserAlreadyExistException("User with this email already exist");
        }
        else{
            user.setUsername(userDetailsDTO.getUsername());

            user.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
            user.setRole(Role.USER);

                Users createdUser =  userRepository.save(user);

                 participantDetails.setEmail(userDetailsDTO.getEmail());
                 participantDetails.setFirstName(userDetailsDTO.getFirstName());
                 participantDetails.setLastName(userDetailsDTO.getLastName());
                 participantDetails.setPhone(userDetailsDTO.getPhone());
                 participantDetails.setUser(createdUser);

              participantDetailRepository.save(participantDetails);


        }
        return true;

    }


    public UserDetailDTO updateUserDetailsByContext(UserDetailDTO userDetailsDTO) {
        UserDetailDTO updatedParticipantDetailsDto =  null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println(" authentication.isAuthenticated() : " + authentication.isAuthenticated() + " principal instanceof UserDetails " + principal.toString());


        if (authentication.isAuthenticated() && principal instanceof UserDetails) {
            String userName;
            userName = ((UserDetails) principal).getUsername();

            Optional<Users> optionalUser =  userRepository.findByUsername(userName);

      if(optionalUser.isPresent()){
          Users user = optionalUser.get();

          Optional<ParticipantDetail> optionalParticipantDetailsById = participantDetailRepository.findByUserId(user.getId());


          if(optionalParticipantDetailsById.isPresent()){

              Optional<ParticipantDetail> participantDetailsByEmail = participantDetailRepository.findByEmail(userDetailsDTO.getEmail());


              ParticipantDetail participantDetailsById =  optionalParticipantDetailsById.get();

              if(participantDetailsByEmail.isPresent() && participantDetailsByEmail.get().getId() != participantDetailsById.getId()){
                      throw new UserAlreadyExistException("User with this email already exist");

                  }

              participantDetailsById.setEmail(userDetailsDTO.getEmail());
              participantDetailsById.setFirstName(userDetailsDTO.getFirstName());
              participantDetailsById.setLastName(userDetailsDTO.getLastName());
              participantDetailsById.setPhone(userDetailsDTO.getPhone());

             ParticipantDetail updatedParticipantDetails=  participantDetailRepository.save(participantDetailsById);

             updatedParticipantDetailsDto = mapToDTO(user,updatedParticipantDetails);


          }else{
              throw  new RuntimeException("participant details not found");

          }


      }else{

          throw  new RuntimeException("user not found");
      }  }else{
            throw new BadCredentialsException("invalid token");
        }

return updatedParticipantDetailsDto;

    }





    public UserDetailDTO getUserDetailsByContext() {
        UserDetailDTO userDetailDTO =  null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println(" authentication.isAuthenticated() : " + authentication.isAuthenticated() + " principal instanceof UserDetails " + principal.toString());


        if (authentication.isAuthenticated() && principal instanceof UserDetails) {
            String userName;
            userName = ((UserDetails) principal).getUsername();

            Optional<Users> optionalUser =  userRepository.findByUsername(userName);

            if(optionalUser.isPresent()){
                Users user = optionalUser.get();

                Optional<ParticipantDetail> optionalParticipantDetails = participantDetailRepository.findByUserId(user.getId());

                if(optionalParticipantDetails.isPresent()){
                    ParticipantDetail participantDetails =  optionalParticipantDetails.get();



                  userDetailDTO = mapToDTO(user,participantDetails);

                }else{
                    throw  new RuntimeException("participant details not found");

                }


            }else{

                throw  new RuntimeException("user not found");
            }  }else{
            throw new BadCredentialsException("invalid token");
        }

        return userDetailDTO;

    }


    boolean emailExist(String email){
        Optional<ParticipantDetail> optionalParticipantDetails = participantDetailRepository.findByEmail(email);
        if(optionalParticipantDetails.isPresent()){
            return true;
        }else{
            return false;
        }

    }

    boolean userNameExist(String userName){
        Optional<Users> user = userRepository.findByUsername(userName);
        if(user.isPresent()){
            return true;
        }else{
            return false;
        }

    }


    private UserDetailDTO mapToDTO(
            Users user,
            ParticipantDetail details) {

        UserDetailDTO dto = new UserDetailDTO();

        dto.setUsername(user.getUsername());
        dto.setFirstName(details.getFirstName());
        dto.setLastName(details.getLastName());
        dto.setEmail(details.getEmail());
        dto.setPhone(details.getPhone());

        return dto;
    }


}
