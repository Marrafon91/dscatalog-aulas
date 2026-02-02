package io.github.marrafon91.dscatalog.projections;

public interface UserDetailsProjection {

    String getUserName();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
