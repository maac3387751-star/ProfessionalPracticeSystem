package uv.lis.professionalpracticesystem.logic.dto;

public class UserDTO {

    private Integer idUser;
    private String names;
    private String lastNames;
    private String email;
    private String phone;
    private boolean status;
    private AccessAccountDTO newAccount;

    public UserDTO() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public AccessAccountDTO getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(AccessAccountDTO newAccount) {
        this.newAccount = newAccount;
    }
    
}
