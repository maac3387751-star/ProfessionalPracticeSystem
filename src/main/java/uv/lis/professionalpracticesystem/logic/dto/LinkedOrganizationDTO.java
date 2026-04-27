/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.dto;

/**
 *
 * @author Maria Jose
 */
public class LinkedOrganizationDTO {

    private Integer idLinkedOrganization;
    private String organizationName;
    private String organizationSector;
    private String addressOrganization;
    private String organizationCity;
    private String organizationState;
    private String phoneNumberOrganization;
    private String organizationEmail;
    private Integer primaryUsers;
    private Integer secondaryUsers;

    public LinkedOrganizationDTO() {
    }

    public Integer getIdLinkedOrganization() {
        return idLinkedOrganization;
    }

    public void setIdLinkedOrganization(Integer idLinkedOrganization) {
        this.idLinkedOrganization = idLinkedOrganization;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationSector() {
        return organizationSector;
    }

    public void setOrganizationSector(String organizationSector) {
        this.organizationSector = organizationSector;
    }

    public String getAddressOrganization() {
        return addressOrganization;
    }

    public void setAddressOrganization(String addressOrganization) {
        this.addressOrganization = addressOrganization;
    }

    public String getOrganizationCity() {
        return organizationCity;
    }

    public void setOrganizationCity(String organizationCity) {
        this.organizationCity = organizationCity;
    }

    public String getOrganizationState() {
        return organizationState;
    }

    public void setOrganizationState(String organizationState) {
        this.organizationState = organizationState;
    }

    public String getPhoneNumberOrganization() {
        return phoneNumberOrganization;
    }

    public void setPhoneNumberOrganization(String phoneNumberOrganization) {
        this.phoneNumberOrganization = phoneNumberOrganization;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public Integer getPrimaryUsers() {
        return primaryUsers;
    }

    public void setPrimaryUsers(Integer primaryUsers) {
        this.primaryUsers = primaryUsers;
    }

    public Integer getSecondaryUsers() {
        return secondaryUsers;
    }

    public void setSecondaryUsers(Integer secondaryUsers) {
        this.secondaryUsers = secondaryUsers;
    }
   
}
