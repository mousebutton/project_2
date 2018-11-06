package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String avatar;
    private List<Role> roles;
    private String department;

    public UserDTO(){}

   public UserDTO(User user){
       this.id = user.getId();
       this.email = user.getEmail();
       this.password = user.getPassword();
       this.firstName = user.getFirstName();
       this.lastName = user.getLastName();
       this.avatar = user.getAvatar();
       this.roles = user.getRoles();
       this.department = user.getDepartment() == null ? "" : user.getDepartment().getName();
   }
}
