package com.sms.entity;

/**
 * 管理员实体类
 * 对应教务管理员和系统管理员
 */
public class Admin {
    private String adminId;
    private String name;
    private String adminType;  // academic_admin / system_admin
    private String deptCode;
    private String phone;
    private String email;

    public Admin() {}

    public Admin(String adminId, String name, String adminType) {
        this.adminId = adminId;
        this.name = name;
        this.adminType = adminType;
    }

    public boolean isAcademicAdmin() {
        return "academic_admin".equals(adminType);
    }

    public boolean isSystemAdmin() {
        return "system_admin".equals(adminType);
    }

    // Getters and Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAdminType() { return adminType; }
    public void setAdminType(String adminType) { this.adminType = adminType; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Admin{" + "adminId='" + adminId + "', name='" + name + '\'' + '}';
    }
}